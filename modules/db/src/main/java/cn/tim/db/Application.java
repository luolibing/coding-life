package cn.tim.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by LuoLiBing on 17/2/21.
 * mysql中查看数据文件存放的位置
 * mysql -uroot -proot -e 'SHOW VARIABLES WHERE Variable_Name LIKE "%dir"'
 * mysql -uroot -proot -e 'SHOW VARIABLES WHERE Variable_Name = "datadir"'
 *
 * mysql文件 InnoDB
 * frm      表结构文件
 * ibd      表空间文件(用于存放索引和数据)
 *
 * 表分区
 * 1 不能在索引字段上进行表分区
 *
 * 数据表删除之后数据库文件空间不会释放, 执行optimize table work_cell_aud;数据库文件大小会进行重排回收.
 *
 * 由文件结构理解数据库
 * 1 数据库查询, 如果不适用索引查询, 需要遍历整个文件. 用索引查询的话先遍历索引树或者hash表, 找到对应的位置, 然后读取指定位置的数据
 * 2 给数据库加行锁,间隙锁, 其实是给文件指定区间加对应的文件锁
 * 3 为什么删除数据之后文件大小不变, 这是因为数据的位置都固定了, 不固定没法查询记录. 所以删除之后还是会继续占用, 使用optimize table tname; 将会使得文件进行重排压缩
 * 4 为什么表分区查询删除快, 这是因为表分区在一个数据库文件中, io竞争少
 *
 * hexdump -v -C table1.frm 可以查看对应的数据库文件hex二进制
 *
 * https://www.percona.com/blog/2017/04/10/innodb-page-merging-and-page-splitting/
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
