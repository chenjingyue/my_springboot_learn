package com.cy.springbootlearndemo.config.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MybatisInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        Configuration configuration = mappedStatement.getConfiguration();
        Object target = invocation.getTarget();
        StatementHandler handler = configuration.newStatementHandler((Executor) target, mappedStatement,
                parameter, RowBounds.DEFAULT, null, null);
        //记录SQL
        BoundSql boundSql = handler.getBoundSql();
        //记录耗时
        long start = System.currentTimeMillis();
        //执行真正的方法
        Object result = invocation.proceed();
        long end = System.currentTimeMillis();
        //记录影响行数
        int affectedRows = Integer.parseInt(result.toString());
        //记录时间
        System.out.println("executed times: " + new Date() + ", sql: " + boundSql.getSql() +
                ", affected rows: " + affectedRows + ", time cost: " + (end - start) + " ms");
        return result;
    }

    @Override
    public Object plugin(Object target) {
//        System.out.println("plugin:"+target.toString());
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("setProperties:"+properties.toString());
    }
}
