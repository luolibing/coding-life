package cn.tim.db.sample1.interceptor;

import org.apache.tomcat.jdbc.pool.interceptor.SlowQueryReport;

/**
 * Created by LuoLiBing on 17/2/24.
 */
public class MySlowQueryReport extends SlowQueryReport {

    @Override
    protected String reportSlowQuery(String query, Object[] args, String name, long start, long delta) {
        String sql = super.reportSlowQuery(query, args, name, start, delta);
        System.out.println("慢查询" + sql);
        return sql;
    }
}
