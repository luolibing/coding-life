package cn.tim.state.machine.enums;

/**
 * User: luolibing
 * Date: 2017/7/22 15:20
 */
public enum  States {

    CREATE(1), WAIT_SERVE_AUDIT(2), WAIT_SERVE_MANAGE_AUDIT(3), FINISHED(6), CLOSED(7);

    private int value;

    States(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
