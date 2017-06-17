package cn.tim.thinking.initialize5;

/**
 * Created by LuoLiBing on 16/8/15.
 */
public class Book {

    boolean checkedOut = false;

    Book(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    void checkIn() {
        checkedOut = false;
    }

    /**
     * 第二个用法,对于终结条件的验证
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        if(checkedOut) {
            System.out.println("Error: checked out");
        }
        // 正常情况下,都应该调用父类的super.finalize()方法
        super.finalize();
    }
}
