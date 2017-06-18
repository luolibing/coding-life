package cn.tim.springboot.transactionmq.mq;

/**
 * Created by luolibing on 2017/6/18.
 */
public interface Consumer<T> {

    Class<T> supportMessageClazz();

    void handleMessage(T message);
}
