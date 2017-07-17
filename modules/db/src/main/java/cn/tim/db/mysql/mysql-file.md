mysql的文件目录
查看mysql中各种目录设置通过指令: show variables like '%dir%';

数据文件目录在datadir配置当中
例如: /var/lib/mysql, 这个目录下回有各个数据库schema目录，对应schema目录下是所有表的文件。
在/var/lib/mysql中会有2个log文件ib_logfile0, ib_logfile1作为innoDB的redo作为事务的日志文件。