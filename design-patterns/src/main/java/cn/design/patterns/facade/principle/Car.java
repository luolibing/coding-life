package cn.design.patterns.facade.principle;

/**
 * Created by LuoLiBing on 16/6/10.
 */
public class Car {

    private Engine engine;

    public Car() {
        engine = new Engine();
    }

    public void start(Key key) {
        // 方法内部创建的对象
        Doors doors = new Doors();
        // 调用方法的参数
        boolean authorized = key.turns();
        if(authorized) {
            engine.start();
            // 调用对象本身的方法
            updateDashboardDisplay();
            // 调用方法内部创建的对象
            doors.lock();
        }
    }

    public void updateDashboardDisplay() {
        System.out.println("update dashBoardDisplay");
    }
}
