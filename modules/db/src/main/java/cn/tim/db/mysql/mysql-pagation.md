# mysql分页查询
### 一、有一种方式可以一次返回总条数与分页数据
> select SQL_CALC_FOUND_ROWS * from dept_emp order by emp_no desc limit 300000,10;
select found_rows();
第一条sql返回分页数据， 第二条sql返回不带limit的总条数。 但是通过测试发现， 使用两个查询比一个查询速度还快。
sql_no_cache

## 二、使用range优化
> select sql_no_cache count(*) from dept_emp use index(primary);
可以使用use index(primary) 查询， 但是查询速度还是感觉没有直接查来得快， 使用explain select * from dept_emp where emp_no<300000 limit 200000,10;
查询的时候会比之前的快一些， 因为使用的查询方式是range方式， 而不是all全表扫描的方式。

## 三、最终优化
> 巨大的偏移量查询会拖慢整个系统， 因此我们要重写这个Sql, 使用索引来进行查询。
向后翻页的sql
select * from dept_emp where emp_no>$last_id order by emp_no desc limit $perpage; last_id是上一次分页查询中最大的id, 然后limit $prepage使用的是每页的数据大小。
向前翻页的sql
select * from dept_emp where emp_no<$last_id order by emp_no desc limit $prepage; last_id也是上一页的第一个id。
这种方式只能处理简单的向前翻页，向后翻页，而无法做出真正的导航页码。而要做真正的页码， 得添加页码对应的页码以及对应的id。
select emp_no from(
	select emp_no,((@cnt:=@cnt+1)+$prepage-1)%$prepage as cnt
	from dept_emp
	join (select @cnt:=0) T
	where emp_no>$last_id
	order by emp_no asc
	limit $prepage * $buttons
) c where cnt=0;

## 链接地址:http://www.xarg.org/2011/10/optimized-pagination-using-mysql/  有空再来看最终的优化，将sql优化得尽量短

## 四、大表问题： 缓冲区， 索引和连接
> Buffers 缓冲区， 适合内存的方式才好，可以通过表分区技术， 将冷热数据进行分离， 很久以前的数据被再次访问的概率相当低。
> Indexs索引，优化表索引
> Join连接，mysql中并不存在高级的连接方法， 例如哈希连接或排序合并连接， 它只能做嵌套循环方法， 需要很多索引的查找。
> 如果两个特大表进行连接查询的次数会特别大， 所以连接的次数太多， 会导致大量的循环嵌套查询， 而且查询连接顺序的不同会产生不一样的成本。

## 五、大表优化的建议
* 尝试将数据集合放入到内存中， 这可以加快连接查询的速度
* 使用索引得看索引字段重复率能达到多少， 如果很低的话，用索引就没多大意义了。 首选全表扫描到索引访问？
* 避免大表的嵌套循环连接， 这样代价是非常昂贵的。 连接到较小表示确定的， 小表可以预先加载到内存中。