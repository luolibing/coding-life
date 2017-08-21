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
2 使用索引覆盖扫描来返回记录，