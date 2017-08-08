mysql类型
1 更小的类型通常更好
2 简单更好，整数比字符串更好
3 尽量避免为NULL,NULL字段会带来更多的开销，尤其是在索引字段上为NULL
4 TIMESTAMP比DATETIME存储空间更小，效率更高，但是又范围限制1970-01-01到2038-01-01，所以如果时间限制不在这个范围之内，最好使用DATETIME

整形：
INT(10)，指定宽度对于大多数应用没有意义，不会限制合法取值范围，只是规定了MYSQL的一些交互工具的显示字符的个数。INT(1)和INT(11)存储空间是一样的。

实数：
可以使用BIGINT来进行Decimal数据的运算，例如10.12，可以使用1012来代替.

VARCHAR和CHAR
VARCHAR用于可变长度字符串，它比定长字符串更节省空间。如果使用ROW_FORMAT=FIXED创建的话。每一行占用的定长空间，这样会浪费很多空间。
VARCHAR因为可变长，所以需要1个或者多个字节来保存字符串的长度，所以char(1)和varchar(1)占用的空间并不一样，varchar(1)会多占用一字节。
VARCHAR在更新的时候，例如加长的时候，需要做一些额外的工作，如果使用InnoDB如果页内没有空间，则需要通过分裂页来存储。
适用场景，字符串比平均字符串长度大很多，使用varchar可以节省很多空间，列的更新少。

CHAR
CHAR类型是定长的，CHAR会采用空格进行填充以方便比较，使用场景长度固定或者比较平均，例如md5可以使用char。
CHAR与VARCHAR，在插入末尾带空格的时候，CHAR会忽略掉末尾的空格，而VARCHAR不会.

CHAR和BINARY字符串和二进制，BINARY存储的是二进制字符串，BINARY填充采用的是\0而不是空格，检索时不会去掉空值。
VARCHAR(5)和VARCHAR(200)的区别，在存储空间上一样，更长的列会消耗更多的内存，因为会分配固定大小的内存块来保存内部值。

BLOB和TEXT
BLOB采用二进制保存，TEXT采用字符串存储，两者被当成一个独立的对象处理。当BLOB和TEXT超过一定长度之后，会采用外部存储区域来进行存储。每个值在行内需要1-4个字节存储对应的指针.
对于BLOB和TEXT进行排序，会只读max_sort_length长度字符串进行排序，如果只需要排前面几个字符串可以需要order by substring(str, len).
MEMORY引擎不支持BLOB和TEXT列，所以如果需要使用隐式临时表，将不得不使用MyISAM磁盘临时表。尽量避免使用这两种类型，实在无法避免，可以使用SUBSTRING(column, length)，这样就可以使用内存临时表了。

枚举ENUM
保存枚举是保存的枚举的数字，在frm中保存了数字和字符串的映射表，当使用排序的时候，也是按照这个数字来进行排序的。 使用枚举的好处，空间更小，因为使用整数，会比字符串少很多。
如果使用枚举与varchar关联，因为中间需要转换，所以性能会差一些。还没有varchar和varchar关联好，所以尽量避免这样做。修改枚举是执行的alter table操作，操作代价有点高.

BIT
mysql将bit当做字符串类型保存，而不是数字类型，但是在检索时，则又是使用字符串转换成数字类型。应该谨慎使用Bit类型，如果需要使用bit表示一个true/false，可以使用一个char(0)保存一个空字符串或者NULL来表示。

SET
与java中的bitSet一样，可以保存一批true或者false，可以节省大量的空间。例如保存权限的访问控制列表ACL,select * from c_type where find_in_set('CAN_DELETE',acl)使用find_in_set查询一个set中是否保存一个值。
其实它底层保存的也是整数，例如select acl+0 from c_type，检索出的结果就是个数字.

选择标识符的原则
1 应该选择跟关联表中对应列一样的类型，类型之间需要精确匹配，包括像UNSIGNED这样的属性.
2 整数类型是最好的选择，避免使用字符串类型，因为占用空间大。MyISAM默认会对字符串进行压缩，这会导致查询速度变慢。因为字符串的随机性，会导致这些记录随机的分配到各个空间，导致查询或者插入大量的随机访问操作，从而变得很慢。
    插入值索引会被随机的写到不同的位置，所以insert语句会慢很多，同时会导致大量的页分裂，磁盘随机访问，还会导致大量的碎片。
    select语句会变慢，因为逻辑上相邻的记录可能分布在不同的地方，导致大量随机访问
    随机值导致缓存失效，例如查询一大块，却只匹配其中的一两行，导致大量的浪费。
    如果存储UUID可以将-符号去掉；或者更好的做法是，用UNHEX将UUID转换成16字节的数字
    

mysql schema设计中的陷阱
1 太多的列，mysql存储引擎API工作时需要在服务器层和存储引擎层之间通过行缓冲格式拷贝数据，然后在服务器层将缓冲内容解码成各个列。MYISAM为定长列，所以转换不需要转换，而INNODB变长列，解析列需要的成本依赖于列的总数。
2 太多的关联
3 全能的枚举，防止过度使用枚举
4 变相的枚举

范式与反范式

缓存表和汇总表，统计一天24小时访问量。可以将24小时拆分为24片，或者拆分成48片，甚至更多，然后每次访问随机的放入到一个slot当中。这样避免更新集中在某一个区域导致压力。
CREATE TABLE `my_summary` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `hour` tinyint(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

// 临时表
drop table if exists my_summary_new, my_summary_old;

// 插入表，当记录不存在时插入，存在时更新
INSERT INTO my_summary (hour,day,count) VALUES (FLOOR(RAND()*24),20170807,0)
         ON DUPLICATE KEY UPDATE count=count+1;
         
// 在重建汇总表和缓存表示，通常需要保证在操作时依然可用，可以通过影子表来实现。类似于用一个中间变量转换一下。
create table my_summary_new like my_summary;
rename table my_summary to my_summary_old, my_summary_new to my_summary;
创建了一个新表，然后将当前表重命名为老表，新表重命名为当前表，这样就无缝连接了。
定时合并任务，将所有的总数合并到一个slot当中，然后其他的字段值，都设置为0

update my_summary_old as c
	inner join(
		select day,sum(count) as cnt,min(hour) as h from my_summary_old group by day
	) as x using(day)
	set c.count=if(c.hour=x.h, x.cnt, 0),
	c.hour=if(c.hour=x.h, 0, c.hour);
	
并且删除掉其他行，这样就只剩下这一天的一个总数
delete from my_summary_old where hour<>0 and count=0


alter table对于大表会是一个大问题，有2种方式能够加快alter table
1 先在一台不提供服务的机器上执行alter table操作，然后和提供服务的主库进行切换
2 影子拷贝

只修改.frm文件时很快的，一般的alter table可能都需要重建表，重建表的代价会比较高。
移除一个列的auto_increment属性， 增加移除或者更改enum或者set常量并不会重建表
