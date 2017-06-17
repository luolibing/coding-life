# explain执行计划

#### 导语

> 今天去面试， 多次问道了mysql执行计划
explain select * from table where a=? and b=? group by c order by d having ?（以前知道这个explain，但是不知道原来这个就叫执行计划， 也是懵逼了）
 查阅官方文档
https://dev.mysql.com/doc/refman/5.7/en/explain-output.html

## 一、认识explain

> expalin对于一个查询涉及到的多个表都会有一条语句记录。 它的顺序是执行SQL时用到这个表的顺序。
MySQL使用嵌套连接方法来解析所有连接, 意思是从第一个表中读取一行之后，再在第二个表中查找是否有匹配的行, 然后根据连接的字段再到第三个表中查找， 直到最后一个表。
（PS: 之前没有彻底理解为什么连接顺序的不同会导致查询性能的不同， 原来是这样的）
mysql通过表列表输出所选的列和回溯，直到找到具有更多匹配行的表。从该表中读取下一行，并且该过程继续下一个表（找出性能相对较好的查询连接顺序方案s？）
Explain输出包含了表分区的信息，此外对于select语句， explain生成的扩展信息， 可以在explain之后使用show warnings显示。
show warnings显示了sql优化器限定表和列的名称，在应用了重写和优化规则之后select看起来像什么， 以及可能关于优化器中的其他注释
show warnings只适用于select， 但是不适用于其他的，其他的会返回空

explain select * from dept_emp where dept_no in(select dept_no from departments);

*************************** 1. row ***************************
           id: 1
  select_type: PRIMARY
        table: dept_emp
         type: index
possible_keys: NULL
          key: emp_no
      key_len: 4
          ref: NULL
         rows: 332289
        Extra: Using index
*************************** 2. row ***************************
           id: 2
  select_type: DEPENDENT SUBQUERY
        table: departments
         type: unique_subquery
possible_keys: PRIMARY
          key: PRIMARY
      key_len: 4
          ref: func
         rows: 1
        Extra: Using index
2 rows in set (0.00 sec)


> explain输出字段

* id                查询的唯一标识                 这个查询的序列号
* select_type       查询方式
   可以是以下方式 SIMPLE(简单查询， 不适用连接或者子查询)、PRIMARY(最外部的查询)、UNION(第二个或者后面一个连接)、DEPENDENT UNION(第二个或者后面一个连接依赖于外部查询)、
   UNION_RESULT(连接的结果)
* partitions        命中的分区
* type              连接方式
* possible_keys     可能被选择的index索引
* key               实际选择的索引
* key_len           key的长度
* ref               引用的长度
* rows              估计要检索的行总数
* filtered          过滤行的百分比
* Extra             额外的信息

> 相关SUBQUERY与UNCACHEABLE_SUBQUERY查询评估不一样，对于DEPENDENT_SUBQUERY，对于来自外部上下文的变量的每个集合， SUBQUERY仅重新求值一次。而UNCACHEABLE SUBQUERY对于每一行都要重新计算子查询。
子查询缓存不同于查询缓存， 子查询缓存是发生在执行期间， 而查询缓存在查询执行完成后才用于存储结果。

## 二、SHOW WARNINGS
> SHOW WARNINGS包含了很多特殊标记记录重写sql和优化动作的一些信息，该sql语句不一定是有效的sql， 并且不打算真正执行这个sql, 同时还包含一些注释行
* <auto_key>
  临时表生成的自增key

*

### TODO 明天继续

https://dev.mysql.com/doc/refman/5.7/en/explain-output.html
