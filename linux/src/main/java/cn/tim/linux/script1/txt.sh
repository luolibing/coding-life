
统计我们的日志文件中出现次数最高的10个md5
cat rms_resource.log|grep "MD5"|awk -F "MD5:" '{print $2}'|sort|uniq -c|sort -rn|head -n 10

awk分隔
sort排序
uniq -c去重统计
sort -rn倒排
head -n取前多少位

找出某些文件进行删除
find ./ -name "1*" -exec rm -rf {} \;
find . -name "*.txt" -exec cat ${1} {} \;

分割文件
指定分割后文件行数
split -l 10000 rms_resource.log rms_child_
拆分之后会变成   rms_child_aa rms_child_ab

按文件大小拆, 但是有可能出现断行的情况
split -b 100m rms_resource.log rms_child_


合并文件
cat *base.log >> b.log


xargs指令
对于一些不支持管道来传递参数的, 而日常工作有必要, 所有就有了xargs这个指令
find /sbin -perm +700|xargs ls -l