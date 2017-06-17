package cn.tim.twopc.transaction;

import java.util.List;

/**
 * User: luolibing
 * Date: 2017/6/8 11:21
 */
public interface TwoPcTransaction {

    TransactionTask createTransaction();

    void decideTransaction(List<String> children);

    void submit(TransactionTask transaction);
}
