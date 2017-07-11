# 慢查询 SLOW_QUERY
https://dev.mysql.com/doc/refman/5.7/en/mysqldumpslow.html
> 慢查询日志查询: mysqldumpslow
=======
> 慢查询日志查询: mysqldumpslow
show variables like '%slow%' 查看慢查询配置
log-slow-queries = /usr/local/mysql/var/slowquery.log
long_query_time = 1  #单位是秒
log-queries-not-using-indexes

开启慢sql查询日志: 
set global slow_query_log='ON';
set GLOBAL long_query_time=20;
set GLOBAL slow_query_log_file='/var/lib/mysql/slow.log'

不重启mysql，开启慢sql
SET GLOBAL slow_query_log = 'ON'; FLUSH LOGS;

> 查询slow_query_log的相关配置
show variables like '%slow_query_log%'
有两个参数： slow_query_log（是否开启），slow_query_log_file(日志文件位置)
慢查询日志包括执行超过long_query_time秒的SQL语句， 并且至少需要检查min_expected_row_limit行， 最小值和默认值分别为0到10秒。 对于写入文件， 写入包括微秒部分的时间。 对于写入表，只写入证书倍，微秒部分被忽略。
默认情况下管理语句和不适用索引的查询都不会记录， 可以使用log_slow_admin_statements和log_queries_not_using_indexes开启这两个。
默认是关闭状态， 可以通过slow_query_log=0/1来关闭或者开启， slow_query_log_file=/share/slowquery.log指定慢查询日志的位置。
