package cn.tim.thinking.innerclass;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 16/12/21.
 * 控制框架
 */
public class ControlFrameworkDemo1 {


    // 定义event和Controller, 将变化的事物与不变的事物相互分离.
    abstract class Event {
        private long eventTime;

        protected final long delayTime;

        public Event(long delayTime) {
            this.delayTime = delayTime;
        }

        public void start() {
            eventTime = System.nanoTime() + delayTime;
        }

        public boolean ready() {
            return System.nanoTime() >= eventTime;
        }

        public abstract void action();
    }

    class Controller {
        private List<Event> events = new ArrayList<>();

        public void addEvent(Event e) {
            events.add(e);
        }

        public void run() {
            while (events.size() > 0) {
                // 通过new ArrayList直接复制, 因为下面要remove, 如果直接remove events的话, 会抛出ConcurrentModify异常
                for(Event e : new ArrayList<>(events)) {
                    if(e.ready()) {
                        System.out.println(e);
                        e.action();
                        events.remove(e);
                    }
                }
            }
        }
    }

    /**
     * 变化向量就是各种不同的event对象所具有的不同行为,而你通过创建不同的Event子类来表现不同的行为
     * 这正是内部类要做的事情, 内部类允许:
     * 1 控制框架的完整实现是由单个类创建的, 从而使得实现的细节被封装了起来. 内部类用来表示解决问题所必需的各种不同的action()
     * 2 内部类能够很容易地访问外围类的任何成员,所以可以避免这种实现变得笨拙.如果没有这种能力, 代码将变得令人讨厌.
     *
     * 以下的内部类即拥有Event所有的方法, 同时又拥有Controller中的所有方法. 类似于多重继承
     */
    public class GreenhouseControls extends Controller {
        private boolean light = false;

        public class LightOn extends Event {

            public LightOn(long delayTime) {
                super(delayTime);
            }

            @Override
            public void action() {
                // 内部类可以直接访问外部类的所有元素
                light = true;
            }

            @Override
            public String toString() {
                return "light is on";
            }
        }

        public class LightOff extends Event {

            public LightOff(long delayTime) {
                super(delayTime);
            }

            @Override
            public void action() {
                light = false;
            }

            @Override
            public String toString() {
                return "light is off";
            }
        }

        private boolean water = false;

        public class WaterOn extends Event {

            public WaterOn(long delayTime) {
                super(delayTime);
            }

            @Override
            public void action() {
                water = true;
            }

            @Override
            public String toString() {
                return "Greenhouse water is on";
            }
        }

        public class WaterOff extends Event {

            public WaterOff(long delayTime) {
                super(delayTime);
            }

            @Override
            public void action() {
                water = false;
            }

            @Override
            public String toString() {
                return "Greenhouse water is off";
            }
        }

        private String thermostat = "Day";

        public class ThermostatNight extends Event {

            public ThermostatNight(long delayTime) {
                super(delayTime);
            }

            @Override
            public void action() {
                thermostat = "Night";
            }

            @Override
            public String toString() {
                return "Thermostat on night setting";
            }
        }

        public class ThermostatDay extends Event {

            public ThermostatDay(long delayTime) {
                super(delayTime);
            }

            @Override
            public void action() {
                thermostat = "Day";
            }

            @Override
            public String toString() {
                return "Thermostat on day setting";
            }
        }

        public class Bell extends Event {

            public Bell(long delayTime) {
                super(delayTime);
            }

            @Override
            public void action() {
                addEvent(new Bell(delayTime));
            }

            @Override
            public String toString() {
                return "Bing!";
            }
        }

        public class Restart extends Event {
            private Event[] eventList;

            public Restart(long delayTime, Event[] eventList) {
                super(delayTime);
                this.eventList = eventList;
                // 将所有的event都加入到Controller当中
                for(Event e : eventList) {
                    addEvent(e);
                }
            }

            @Override
            public void action() {
                for(Event e : eventList) {
                    e.start();
                    addEvent(e);
                }
                start();
                addEvent(this);
            }

            @Override
            public String toString() {
                return "Restarting System";
            }
        }

        public class Terminate extends Event {

            public Terminate(long delayTime) {
                super(delayTime);
            }

            @Override
            public void action() {
                System.exit(0);
            }

            @Override
            public String toString() {
                return "Terminating";
            }
        }
    }

    @Test
    public void greenHourseController() {
        GreenhouseControls c = new GreenhouseControls();
        c.addEvent(c.new Bell(900));
        Event[] eventList = {
                c.new ThermostatNight(0),
                c.new LightOn(200),
                c.new LightOff(400),
                c.new WaterOn(600),
                c.new WaterOff(800),
                c.new ThermostatDay(1400)
        };
        c.addEvent(c.new Restart(2000, eventList));
        c.addEvent(c.new Terminate(8000));
        c.run();
    }

}
