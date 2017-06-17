package cn.tim.twopc.client;

/**
 * User: luolibing
 * Date: 2017/6/8 12:48
 */
public interface ClientTransaction<T> {

    T beginTransaction();

    void rollback();

    void commit();
}
