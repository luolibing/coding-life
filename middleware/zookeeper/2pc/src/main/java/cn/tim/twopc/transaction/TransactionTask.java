package cn.tim.twopc.transaction;

import lombok.Data;

/**
 * User: luolibing
 * Date: 2017/6/8 11:21
 */
@Data
public class TransactionTask {

    private String txPath;

    private Status status;

    public enum Status {
        create, commit, abort, ack
    }
}
