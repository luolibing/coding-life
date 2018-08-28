package cn.tim.mybatis.base.interceptor;

import com.mysql.jdbc.Connection;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * Created by luolibing on 2018/8/28.
 */
@Slf4j
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare",
                args = { Connection.class, Integer.class}) })
public class SqlMybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        ParameterHandler parameterHandler = statementHandler.getParameterHandler();
        log.info("execute sql = " + boundSql);
        Object parameterObject = parameterHandler.getParameterObject();
        System.out.println(parameterObject);
        return null;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        log.info(properties.getProperty("dialect"));
    }
}
