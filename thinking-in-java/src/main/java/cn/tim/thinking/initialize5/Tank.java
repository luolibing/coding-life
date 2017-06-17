package cn.tim.thinking.initialize5;

/**
 * Created by LuoLiBing on 16/8/15.
 */
public class Tank {

    boolean full = false;

    public Tank(boolean full) {
        this.full = full;
    }

    @Override
    protected void finalize() throws Throwable {
        if(full) {
            System.out.println("error status full!!!");
        }
        super.finalize();
    }
}
