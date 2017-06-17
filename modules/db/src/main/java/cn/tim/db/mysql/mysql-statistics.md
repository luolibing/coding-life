# mysql统计信息
> 查询优化器使用统计信息为sql选择执行计划, mysql没有直方图信息, 也无法手工删除统计信息.
统计信息如何收集: Analyze table收集表和索引统计信息, 适用于MyISAM和InnoDB.
何时统计信息可以使用下面选项
1 表第一次打开的时候
2 表修改的行超过1/16或者20亿条, ./row/row0mysql.c:row_update_statistics_if_needed
3 执行show index/table或者查询information_schema.tables/statistics表时

# 查看统计信息
> show index from work_cell 查看表的索引或者查看information_schema_statistics表
> show table status或information_schema.tables表