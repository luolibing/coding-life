## Mysql高性能索引
索引应该是对查询优化最有效的手段了。

BTree
MyISAM使用前缀压缩的方式使得索引更小，而InnoDB则保存的原数据。MyISAM索引通过物理位置引用被索引的行，而InnoDB则根据主键引用被索引的行。
BTree通常以为这所有值都是按照顺序存储的，同时每个叶子节点到根节点的距离相同。叶子节点，叶子页。
BTree对索引是顺序组织存储的，所以非常适合查找范围数据。

BTree索引适用于全键值、键值范围或者前缀查找。
全值匹配                        select * from work_cell where sheet_id=1000 and row=0 and col=6
匹配最左前缀                     select * from work_cell where sheet_id=1000
匹配列前缀                       select * from work_cell where sheet_id like '2000%'
匹配范围值                       select * from work_cell where sheet_id between 10000 and 20000 and row=10
精确匹配某一列并范围匹配另外一列    select * from work_cell where sheet_id=1000 and row between 0 and 10
只访问索引的查询                  select * from work_cell where sheet_id=1000 and row=10 and col=1

哈希索引
mysql中只有memory引擎显式支持哈希索引。
哈希索引的限制：
1 哈希索引只包含哈希值和行指针，而不存储字段值，所以不能使用索引中的值来避免读取行。
2 哈希值并不是按照索引顺序存储的，所以无法排序
3 哈希索引页不支持部分索引列匹配查找。
4 哈希索引只支持等值比较查询，包括=, IN(), <=>
5 哈希索引速度会很快，前提是哈希冲突较少

InnoDB有一个自适应哈希索引。当注意到某些索引值被使用得非常频繁时，会在内存中基于B-Tree索引之上再创建一个哈希索引，这样能够加快查询速度。

伪哈希索引.
对于在大文本上进行索引查询，本身会比较消耗性能。因为大文本索引会占用大量的空间，导致索引速度变慢。可以将大文本进行编码压缩成一个数字，然后在这个数字上做索引，这样会快很多.
select * from my_content where crc32=711768043 and content='to make a decision'
我们在crc32字段上对content字段的内容进行了编码成一个数字，然后针对这个数字做索引，这样查询的话会快很多，即使有hash冲突，还可以根据content进行过滤。
只是维护这个crc32字段会相对麻烦一些，可以在应用中维护，也可以使用触发器，在添加和更新的时候，同步更新对应的crc32值

delimiter //
create trigger manage_crc32_hash before insert on my_content for each row begin
set new.crc32=crc32(new.content);
end;
//

create trigger manage_crc32_hash_update before update on my_content for each row begin
set new.crc32=crc32(new.content);
end;
//
delimiter ;

delimiter是为了临时修改语句分隔符，这样可以在触发器中使用;分号


空间数据索引 R-Tree
MyISAM支持R-Tree，空间索引，可以作为地理数据存储。GIS

全文索引


## 索引的优点：可以让服务器快速地定位到表的数据的指定位置
1 索引大大减少了服务器需要扫描的数据量
2 索引可以帮助服务器避免排序和临时表
3 索引可以将随机I/O变成顺序IO

索引的评级，"三星系统"；
一星：索引将相关的记录放到一起则获得一星                 explain select * from work_cell where sheet_id=10001 and row=0 and col=1
二星：索引中的数据顺序和查找的排列顺序一致，则获得二星     explain select * from work_cell where sheet_id=10001 order by row
三星：索引中的列包含了查询中需要的全部列，则获得三星       explain select id,sheet_id,row,col from work_cell where sheet_id=10001 order by row

索引的使用场景，表数据数量适中，对于数量很少的表，没有太大必要创建索引
同时有较好的区分度，最好为1，一般在0-1之间，区分度=不重复数/总数。唯一索引的区分度为1。在性别上创建索引就完全没有必要，因为区分度为2/总数
数据再大一点，可以使用分区技术（不过这个有技术说不使用这个技术？这个有待研究）
如果再往上，就可以考虑使用分表分库技术，这个时候可以建立一个元数据表，例如哪个用户存储的哪个库的哪个表里面。

高性能索引策略
1 独立的列，索引列不能是表达式的一部分。即不能使用表达式作为查询条件，explain select * from my_content where crc32+2=2528782775
2 前缀索引和索引选择性。前缀索引的长度对于性能的影响较大，当长度过大时会导致性能大幅降低。索引选择性=不重复数量/总数。比值在1/#T到1之间。唯一索引为1
  有时候还得需要考虑到索引列重复读分配情况，如果分配不均匀。需要按照实际情况来选择，索引的前缀长度。选择性等于0.031左右，就基本上可以使用了。
  MYSQL无法使用前缀索引做ORDER BY和GROUP BY,也无法使用覆盖索引。有时候后缀索引页有用途，但是MYSQL并不支持反向索引，可以将原本正向的反转之后存入字段。
    select count(distinct crc32)/count(*) from my_content
    select crc32,count(*) from my_content group by crc32
    explain select count(*) as cnt,left(name, 12) as pref from work_excel group by pref order by cnt desc limit 100
    
    select 
    count(distinct left(name,7))/count(*),
    count(distinct left(name,8))/count(*),
    count(distinct left(name,9))/count(*),
    count(distinct left(name,10))/count(*),
    count(distinct left(name,11))/count(*),
    count(distinct left(name,12))/count(*),
    count(distinct left(name,13))/count(*),
    count(distinct left(name,14))/count(*),
    count(distinct left(name,15))/count(*)
    from work_excel
3 多列索引
    多个单列索引，在mysql中5.0以上引入了一种叫"索引合并"的优化策略。以下查询，虽然在2个列上创建了索引，但是并不能很好的使用索引。Using union(IDX_SHEET_ID,IDX_CRC32); Using where
    explain select * from my_content where sheet_id=10 or crc32=1409898321
    当出现服务器对多个索引做相交操作时（多个条件AND），通常意味着需要一个包含所有相关列的多列索引。
    当出现多个索引做联合操作时，（多个条件OR）,需要大量的CPU和内存资源在算法的缓存、排序和合并操作上。
    
4 选择合适的索引列顺序
    正确的顺序依赖于使用该索引的查询，并且同时需要考虑如何更好地满足排序和分组的需要。将选择性最高的列放在索引的最前面，前提是不需要考虑排序和分组时。
    select 
    count(distinct word)/count(*),
    count(distinct image_name)/count(*) 
    from maintable
    
    
## 聚族索引
聚族索引并不是一种单独的索引类型，而是一种数据存储方式。具体的细节依赖于实现方式，InnoDB的聚族索引实际上在同一个结构中保存了B-Tree索引和数据行。
    
    