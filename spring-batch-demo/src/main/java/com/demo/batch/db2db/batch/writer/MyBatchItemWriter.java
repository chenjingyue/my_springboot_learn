package com.demo.batch.db2db.batch.writer;

import com.demo.batch.db2db.config.DataSourceHolder;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

public class MyBatchItemWriter<T> implements ItemWriter<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatchItemWriter.class);


    private final Object lock = new Object();

    private SqlSessionTemplate sqlSessionTemplate;

    private String statementId;

    private boolean assertUpdates = true;

    @Autowired
    private TaskExecutor myTaskExecutor;


    /**
     * Public setter for the flag that determines whether an assertion is made that all items cause at least one row to be
     * updated.
     *
     * @param assertUpdates the flag to set. Defaults to true;
     */
    public void setAssertUpdates(boolean assertUpdates) {
        this.assertUpdates = assertUpdates;
    }

    /**
     * Public setter for {@link SqlSessionFactory} for injection purposes.
     *
     * @param sqlSessionFactory a factory object for the {@link SqlSession}.
     */
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        if (sqlSessionTemplate == null) {
            this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        }
    }

    /**
     * Public setter for the {@link SqlSessionTemplate}.
     *
     * @param sqlSessionTemplate a template object for use the {@link SqlSession} on the Spring managed transaction
     */
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    /**
     * Public setter for the statement id identifying the statement in the SqlMap configuration file.
     *
     * @param statementId the id for the statement
     */
    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final List<? extends T> items) {
        myTaskExecutor.execute(() -> doWrite(items));
//        doWrite(items);
    }

    private void doWrite(List<? extends T> items) {
        if (!items.isEmpty()) {
//            LOGGER.warn(Thread.currentThread().getName() + "-- Executing batch with " + items.size() + " items.");
            DataSourceHolder.set("master");

//            synchronized (lock) {
                if (!TransactionSynchronizationManager.isSynchronizationActive()) {
                    TransactionSynchronizationManager.initSynchronization();
                }

//            for (T item : items) {
                sqlSessionTemplate.update(statementId, items);
//            }
                List<BatchResult> results = sqlSessionTemplate.flushStatements();

                if (assertUpdates) {
                    if (results.size() != 1) {
                        throw new InvalidDataAccessResourceUsageException("Batch execution returned invalid results. "
                                + "Expected 1 but number of BatchResult objects returned was " + results.size());
                    }

                    int[] updateCounts = results.get(0).getUpdateCounts();

                    for (int i = 0; i < updateCounts.length; i++) {
                        int value = updateCounts[i];
                        if (value == 0) {
                            throw new EmptyResultDataAccessException(
                                    "Item " + i + " of " + updateCounts.length + " did not update any rows: [" + items.get(i) + "]", 1);
                        }
                    }
//                }
            }
        }
    }
}
