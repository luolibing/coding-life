# 查询性能优化
查询性能为什么会低，如果把查询看做一个任务，那么它就由一系列的子任务组成，每个子任务都会消耗一定的时间。所以优化的方向要么是降低子任务消耗的时间，要么就是消除某些子任务。

## 查询的大致生命周期
从客户端，到服务器，然后在服务器上进行解析，生成执行计划，执行，并且返回结果给客户端。

## 慢查询的基础: 最基本的原因是访问的数据太多
1 确认应用程序是否在检索大量超过需要的数据，这通常意味着访问了太多的行，但有时候也可能是访问了太多的列。
2 Mysql服务器层是否在分析大量超过需要的数据行。

一 是否向数据库请求了不需要的数据
1 查询不需要的记录
2 多表关联时返回所有表的全部列
3 总是取出全部列
4 重复查询相同的数据

二 Mysql是否在扫描额外的记录
衡量查询开销的三个指标是：
1 响应时间
2 扫描的行数
3 返回的行数

扫描的行数和返回的行数
扫描的行数对返回的行数通常比率比较小，在1：1到10：1之间，甚至更高

扫描的行数和访问类型
访问类型有很多种，从全表扫描 -> 索引扫描 -> 范围扫描 -> 唯一索引查询 -> 常数引用 速度由慢到快，扫描的行数由大到小
索引让MySQL以最高效，扫描行数最少的方式找到需要的记录。
explain select * from my_content where sheet_id=10。使用的查询方式为常数引用ref，扫描最少的行20行，但是因为是二级索引，所以这个地方还是得回表查询一次extra Using where
如果这个sheet_id上没有索引这个地方就得使用全表扫描了

WHERE条件的好坏，从好到坏依次是
1 在索引中使用WHERE条件来过滤不匹配的记录，这是在存储引擎层实现的。select * from my_content where sheet_id=10
2 使用索引覆盖扫描来返回记录（extra中Using index），直接从索引中过滤不需要的记录并返回命中的结果。这是在MySQL的服务层完成的，但是却不需要再回表查询
3 从数据表中返回数据，然后过滤不满足条件的记录（extra中Using where）。

如果发现查询需要扫描大量的数据行而只返回少数的数据行，可以按照下面的方式进行优化
1 使用索引覆盖扫描，把所有需要用到的列放入到索引当中，这样存储引擎无须回表查询
2 改变库表结构，例如汇总表
3 重写复杂的查询

优化查询
一个复杂的查询还是多个简单的查询
切分查询，将一个查询切分成多个查询
分解关联查询

分解关联查询的优势：
1 让缓存更有效率
2 将查询分解后，执行单个查询可以减少锁的竞争
3 在应用层做关联，可以更容易对数据库进行拆分，更容易做到高性能高扩展。
4 查询本身效率也会提高
5 可以减少冗余记录的查询
6 更进一步，这相当于hash关联


查询执行的基础：
客户端发送一条查询给服务端 -> 服务器先检查查询缓存，如果命中了缓存，则立即返回，否则下一步 -> 服务器端进行SQL解析、预处理，再由优化器生成对应的执行计划 -> 根据执行计划，调用存储引擎层的API来执行查询 -> 将结果返回给客户端
MySQL客户端和服务器之间的通信协议是"半双工"通信，在任何时刻，要么由服务器向客户端发送数据，要么由客户端向服务器发送数据，两个动作不能同时发生。所以当开始响应客户端请求时，客户端必须完成地接受整个返回结果，而不能简单地只取前面几条结果，然后让服务器停止发送数据。

MySql查询状态
Sleep
Query
Locked
Analyzing and statistics
Copying to tmp table [on disk]
Sorting result
Sending data

查询缓存，查询之前先查看查询缓存，根据sql语句进行缓存，所以对大小写敏感
MySQL会通过关键字将SQL语句进行解析，生成一棵对应的"解析树"，MySQL根据对应的语法规则验证和解析查询。除了解析规则，还会验证权限。
查询优化器，一个查询可以有多种执行方式，最后都返回相同的结果。优化器的作用就是找到这其中最好的执行计划。
Mysql查询优化器是基于成本模型的优化器。

select sql_no_cache count(*) from work_cell; // 避免缓存影响成本计算
show status like 'last_query_cost'; // 查看上一次查询的成本消耗,查看大概需要做多少个数据页的随机查找才能完成查询。

下面的情况有可能导致错误的执行计划
1 统计信息不准确
2 执行计划中的成本估算不等同于实际执行的成本
3 mysql的最优和你想象的最优可能不一样
4 mysql从不考虑并发
5 并不是一直基于成本
6 mysql不会考虑不受控制的操作成本

静态优化和动态优化，静态优化只进行一次，而动态优化，可能每次查询都会进行
mysql的动态优化
1 重新定义关联表的顺序
2 将外连接转换成内连接
3 使用等价变换规则
4 优化count(),min()和max()
5 预估并转换为常数表达式
6 覆盖索引扫描
7 子查询优化
8 提前终止查询  explain select d.id from department d left outer join user u using(id) where u.`department` is null
9 等值传播
10列表In()的比较。 mysql会对in中的集合进行排序。


MYSQL如何执行关联查询
MYSQL认为任何一个查询都是一次"关联"。对于UNION查询，Mysql先将一系列单个查询结果放到一个临时表中，然后再重新读出临时表数据来完成UNION查询。
MYSQL对任何关联都执行嵌套循环关联操作，即MYSQL先在一个表中循环取出单条数据，然后再嵌套循环到下一个表中寻找匹配的行，依次下去，直到找到所有表中匹配的行为止。


执行计划
关联查询的最重要的一部分，就是使用一个左侧深度优先的树来进行查询，同时表关联的顺序不同，成本也会不同。可以使用一个贪心算法，来得出相对较低的成本。
explain
select c.col,c.row,c.content,s.name from work_cell c inner join work_sheet s on c.sheet_id=s.id
where c.sheet_id=100
关联查询，这个时候会先使用work_sheet
强制使用自己指定的关联顺序，进行关联straight_join。当超过optimizer_search_depth表数量时，会使用贪心算法进行成本核算，选择最优的结果。

数据排序
无论如何排序都是一个成本很高的操作，应该尽量避免对大量数据进行排序。数量量较少时，在内存中排序，数据量大时使用文件io排序，不过都统称为filesort。
当数据量少于"排序缓冲区"时，MYSQL在内存中使用"快速排序"操作。如果内存不够排序，则先将数据分块，分别使用快速排序，然后再讲排好序的快合并起来。
max_length_for_sort_data可以控制，当未超过这个大小时，使用单次传输排序。

通常使用in()加子查询，性能经常会非常糟糕，所以通常建议使用exists()等效地改写来获取更好的效率
explain 
(select * from my_content
order by sheet_id desc limit 20)
union all
(select c.*,"asfds" from work_cell c
order by c.sheet_id desc limit 20)
limit 20


select count(*) from tb1;该查询并不是我们想象的那样，扩展到所有列，它会忽略所有列的值而直接统计所有的行数。如果使用count(col1)查询时，只会统计col1不为空的列。
对count()的优化，可以使用count(*) - 更小的扫描行数
explain select ((select count(*) from work_cell)-count(*)) from work_cell where id<=10;这样查询的性能会更高吗？
如果对于统计结果并不是要求的那么精确，可以使用一个近似值，可以使用explain来获取，explain并不会真正执行查询，所以成本很低

优化关联查询
1 确保on和using子句的列上有索引
2 确保任何group by和order by中的表达式只涉及到一个表中的列

优化子查询：尽量使用关联查询进行优化

优化group by和distinct
在无法使用索引的时候，group by使用临时表或者文件排序来进行分组。采用标识符进行分组效率会更高一些。



limit分页优化
explain select * from work_cell limit 4000000,20；这个查询会查出400万20条数据，然后丢弃400万。
优化这种查询的思路是尽可能的使用索引覆盖扫描，而不是查询所有列，然后根据需要做一次关联查询返回所需的列。
使用下面的索引缩小扫描的范围，然后再进行排序，limit效率会高很多
explain select * from work_cell where id<3000000 order by id desc limit 20;


优化UNION查询
除非需要去重，不然可以使用UNION ALL代替UNION查询，这一点很重要。


用户自定义变量
set @last_week 	:= CURRENT_DATE-INTERVAL 1 WEEK;
set @min_id 	:= (select min(id) from work_cell);

select @last_week,@min_id from dual;
用户自定义变量的一个好处是，可以在查询值的同时，更改值。

更新值的同时，返回值的结果，避免重复查询.
update my_content set update_time=now() where id=100 and @now := NOW();
select @now;

当使用insert on duplicate key update语法时，有时候需要知道更新了多少条数据，可以使用自定义变量
set @x	:= 0

insert into work_cell(row,col,content,sheet_id) values(3,3,"3333",1),(3,3,"3333",2),(3,3,"3333",2000)
on duplicate key
update
content=content+"_10000" + (0*(@x:=@x+1))

select @x


偷懒的UNION写法
select * from tb1 where id=1
union all
select * from tb2 where id=1
这个查询，当id=1的时候union all还是会继续去扫描tb2这个查询，如果要避免继续扫描tb2这个表，使用下面的sql语句可以使用.

// greatest返回这个数组中的最大值。
explain
select greatest(@found:=-1,id) as id, content
from my_content where id=1
union all
select id, content from work_cell where id=1 and @found is null
union all
select 1,'reset' from dual where (@found := NULL) is not null;




