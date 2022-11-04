package com.demo.batch.db2db.batch.reader;

import com.demo.batch.db2db.entity.User;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyBatchPageItemReader<T> extends AbstractPagingItemReader<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatchPageItemReader.class);

    private SqlSessionFactory sqlSessionFactory;
    private SqlSessionTemplate sqlSessionTemplate;

    private String queryId;

    private Integer lastId;

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public Integer getLastId() {
        return lastId;
    }

    public void setLastId(Integer lastId) {
        this.lastId = lastId;
    }

    @Override
    protected void doReadPage() {
//        LOGGER.info(Thread.currentThread().getName() + "--doReadPage");
//        LOGGER.info(Thread.currentThread().getName() + "--currentItemCount" + currentItemCount);

        if (sqlSessionTemplate == null) {
            sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        }
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("_lastid", lastId);
        parameters.put("_page", getPage());
        parameters.put("_pagesize", getPageSize());
        parameters.put("_skiprows", getPage() * getPageSize());
        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        } else {
            results.clear();
        }
        List<T> objects = sqlSessionTemplate.selectList(queryId, parameters);
        determineLastId(objects);
        results.addAll(objects);

    }

    private void determineLastId(List<T> objects) {
        if (CollectionUtils.isEmpty(objects)) {
            this.lastId = null;
            return;
        }
        User user = (User) objects.get(objects.size() - 1);
        this.lastId = user.getId();
    }

    @Override
    protected void doJumpToPage(int itemIndex) {

    }

}
