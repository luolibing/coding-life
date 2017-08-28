### Mysql高级特性
#### 分区表
mysql分区表是一个独立的逻辑表，它底层由多个物理表组成，进行查找时，优化器会根据分区定义过滤那些没有我们数据的分区，这样就无需扫描所有的分区了，只需要扫描包含我们需要的数据分区即可。

分区表的应用场景
1 表非常大以至于无法全部都存放在内存中，或者只在表的最后部门有热点数据，其他均为历史数据。
2 分区表数据更容易维护，如果想删除大两岁护具可以使用清理整个分区的方式
3 分区表数据可以分布在不同的物理设备上，更加高效
4 可以使用分区表来避免某些特殊的瓶颈，例如innoDB单个索引的互斥访问，ext3文件系统inode的锁竞争
5 如果需要，还可以针对表分区进行备份和恢复

同时表分区也有一些限制
1 最多1024个分区
2 分区表达式必须是整数，或者是返回整数的表达式
3 如果分区字段中有主见或者唯一索引的列，那么所有的主见和唯一索引都必须包含进来。
4 分区表中无法使用外键索引

CREATE TABLE `my_content_bak` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `row` tinyint(3) unsigned NOT NULL COMMENT '行',
  `col` tinyint(3) unsigned NOT NULL COMMENT '列',
  `content` varchar(20000) NOT NULL DEFAULT '' COMMENT '内容',
  `version` tinyint(3) unsigned DEFAULT NULL COMMENT '版本',
  `type` tinyint(3) unsigned DEFAULT '0',
  `style` varchar(100) DEFAULT NULL COMMENT '样式',
  `sheet_id` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL COMMENT '最后修改时间',
  `update_userid` bigint(20) DEFAULT NULL COMMENT '最后修改人',
  `update_username` varchar(30) DEFAULT NULL,
  `crc32` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`,`sheet_id`),  ##因为有主键和唯一索引，所以必须将其都纳入到分区字段中
  KEY `IDX_CRC32` (`crc32`),
  KEY `IDX_SHEET_ID` (`sheet_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1021 DEFAULT CHARSET=utf8
partition by range(sheet_id)
(
partition p0 values less than (500),
partition p1 values less than (1000),
partition p2 values less than (1500),
partition p3 values less than (2000)
);

alter table my_content_bak partition by hash(sheet_id)
partitions 100;

分区完成之后，表现在数据库文件上会产生对应个分区的表文件
my_content_bak#P#p10.MYI
my_content_bak#P#p10.MYD

分区表类型，支持键值，哈希，范围列表分区
可以使用explain partitions select * from work_cell where sheet_id=1000来查看需要到哪些分区中进行扫描数据。


视图
create view work_cell_2 AS
select * from work_cell where sheet_id between 1000 and 2000 with check option;
创建视图，视图本身是一个虚拟表，不存放任何数据。MYSQL可以使用2种算法来处理视图，分别为合并算法（Merge）和临时表算法（TEMPTABLE）
当视图查询中有，group by、distinct，任何聚合函数,union，子查询等，只要无法再元彪记录和视图记录建立一一对应映射场景，MYSQL将使用临时表来实现视图。
select_type为DELIVERY,说明该视图是采用临时表算法实现的。
