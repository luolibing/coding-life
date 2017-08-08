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