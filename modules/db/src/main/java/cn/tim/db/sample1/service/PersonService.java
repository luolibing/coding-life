package cn.tim.db.sample1.service;

import cn.tim.db.sample1.entity.Person;
import cn.tim.db.sample1.exception.OtherException;
import cn.tim.db.sample1.exception.TransactionException;
import cn.tim.db.sample1.jpa.PersonJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * Created by LuoLiBing on 17/2/21.
 * 悲观锁的原理:
 * 1 如果一个事务需要一条数据
 * 2 它就将数据锁住
 * 3 如果另外一个事务也需要这条数据
 * 4 它就必须要等第一个事务释放这条数据
 *   这种锁叫排它锁
 *
 * 如果仅仅是读取数据的事务使用排它锁代价昂贵, 因此有了另外一种锁, 共享锁
 * 共享锁的原理:
 * 1 如果一个事物只需要读取数据A
 * 2 它会给数据A加上共享锁并读取
 * 3 如果第二个事物也需要仅仅读取数据A
 * 4 同样它也会给数据A加上共享锁并读取
 * 5 如果第三个事物需要修改数据A
 * 6 它会给数据A加上排它锁, 但是必须等待另外两个事务释放他们的共享锁
 *
 * 共享锁:
 *
 *
 */
@Service
public class PersonService {

    @Autowired
    private PersonJpaRepository personJpaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * InnoDB在可重复读的情况下, 如果一个字段加了索引会使用行锁 + 间隙锁的组合来解决幻读的发生
     * 在扫描索引记录的时候, 会首先对选中的索引记录加上行锁, 再对索引记录两边的间隙(第一个比参数小的, 第一个比参数大的值, 构成一个区间)加上间隙锁(Gap Lock).
     * 如果一个间隙被事务T1加了锁, 其他事务是不能在这个间隙插入记录的.
     *
     * 在并发的情况下有可能会发生死锁, 间隙锁在InnoBD唯一作用就是防止其它事务的插入操作, 防止幻读的发生, 所以不分共享锁与排它锁.
     * 如果扫描的是一个主键或者是一个唯一索引的话, InnoDB只会采用行锁方式加锁.
     *
     * 禁止间隙锁的话, 可以把隔离级别降为读已提交, 或者开启参数innodb_locks_unsafe_for_binlog
     * Deadlock found when trying to get lock; try restarting transaction
     * @param person
     * @param age
     * @param num
     */
    @Transactional
    public void gapLock(Person person, Integer age, int num) {
        System.out.println(num);
        personJpaRepository.deleteByAge(age);
        personJpaRepository.save(person);
    }


    /**
     * 读提交, 可以解决间隙锁, 但是同样解决不了死锁的问题
     * @param person
     * @param age
     * @param num
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void save2(Person person, Integer age, int num) {
        System.out.println(num);
        personJpaRepository.deleteByAge(age);
        personJpaRepository.save(person);
    }


    /**
     * 悲观写
     * 对应SQL: select * from Person where age=20 for update
     * 悲观写锁也不允许出现脏读和不可重复读, 参数rollbackFor设置导致回滚的异常, noRollbackFor设置不会回滚的异常
     * @param age
     * @param num
     * @throws InterruptedException
     */
    @Transactional(readOnly = false, rollbackFor = TransactionException.class, noRollbackFor = OtherException.class)
    public void pessimistic(Integer age, int num) throws InterruptedException {
        System.out.println(num);
        List<Person> list = personJpaRepository.findByAgePessimistic(age);
//        Thread.sleep(5000);
        for(Person p : list) {
            p.setName(p.getName() + "_add");
        }
        personJpaRepository.save(list);
        throw new TransactionException();
    }


    /**
     * 悲观读, 同样悲观读会限制写操作
     * 是为了解决在有可能脏读或不可重复读的情况下实现可重复读的情况
     * select * from person where age=20 lock in share mode;
     *
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void pessimisticRead(Integer age) throws InterruptedException {
        List<Person> list = personJpaRepository.findByAgePessimisticRead(age);
        System.out.println(list.size());
        //
        Thread.sleep(10000);
        list = personJpaRepository.findByAgePessimisticRead(age);
        System.out.println(list.size());
    }

    /**
     * 悲观锁强制将查询出来的对象版本号加1, 即使查询出来的对象并没有修改
     * select * from person where id=? for update
     * update person set version=? where id=? and version=?
     * @param id
     * @throws InterruptedException
     */
    @Transactional
    public void pessimisticIncrement(Long id) throws InterruptedException {
        personJpaRepository.findById(id);
    }

    /**
     * 死锁
     * 死锁发生时, 锁管理器要选择取消一个事务, 以便消除死锁.
     * mysql会通过简单的途径来判定死锁: 通过超时设定, 如果一个锁在超时时间内没有加上, 那么事务就进入死锁状态.
     *
     * 两段锁:
     * 实现纯粹的隔离最简单的方法是: 事务开始获取锁, 事务结束释放锁. 但是为了等待所有的锁, 大量的时间被浪费了.
     * 更快的方法是两阶段锁协议(DB2, SQLServer)
     * 成长阶段: 事务可以获得锁, 但不能释放锁
     * 收缩阶段: 事务可以释放锁, 但不能获得锁
     *
     */


    /**
     * 乐观锁
     * 使用版本号作为辅助, 使用乐观锁, 必须确保不会出现脏读和不可重复读
     *
     * 版本控制:
     * 1 每个事务可以在相同时刻修改相同的数据
     * 2 每个事务有自己的数据拷贝(或者版本)
     * 3 如果2个事务修改相同的数据, 只接收一个修改, 另一个将被拒绝, 相关的事务回滚(或重新运行)
     *
     * 这将提高性能, 因为
     * 读事务不会阻塞写事务
     * 写事务不会阻塞读
     * 没有"臃肿缓慢"的锁管理器带来的额外开销
     *
     * mysql oracle PotgreSQL使用锁和版本控制混合机制.
     *
     */
    public void op() {

    }


    /**
     * 慢sqlQuery查询报告, 使用tomcat连接池可以针对这个进行报告, 默认是warn的方式将日志打印出来.
     * long_query_time默认是10s, 关闭的, 在需要调优的情况下, 才开启这个参数, 因为开启慢查询日志或多或少带来一些性能损耗. 慢查询日志要写入数据库, 也支持写入数据库表.
     * http://www.cnblogs.com/kerrycode/p/5593204.html
     *
     */
    public void slowQueryReport() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from dept_emp");
        System.out.println(list.size());
    }


}
