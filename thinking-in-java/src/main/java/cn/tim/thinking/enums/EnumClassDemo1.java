package cn.tim.thinking.enums;

import org.junit.Test;

// 静态导入用于enum
import static cn.tim.thinking.enums.EnumClassDemo1.Shrubbery.*;

/**
 * Created by LuoLiBing on 16/12/11.
 *
 */
public class EnumClassDemo1 {

    enum Shrubbery {
        GROUND, CRAWLING, HANGING
    }

    @Test
    public void enumClass() {
        for(Shrubbery s : Shrubbery.values()) {
            // 枚举的声明顺序
            System.out.println(s + " ordinal: " + s.ordinal());
            // 比较序号, a.compareTo(b). a比b小,则返回-1
            System.out.println("compareTo(CRAWLING)" + s.compareTo(CRAWLING));
            // equals是否相等
            System.out.println(s.equals(Shrubbery.CRAWLING));
            // == 判断是否是同一个对象
            System.out.println(s == Shrubbery.CRAWLING);
            //
            System.out.println(s.getDeclaringClass());
            System.out.println(s.name());
            System.out.println("-----------------------------");
        }

        // Use Shrubbery.valueOf(s)
        for(String s : "HANGING CRAWLING GROUND".split(" ")) {
            System.out.println(Shrubbery.valueOf(s));
        }

        // Use Enum.valueOf(class, className), 当不存在时抛出异常
        System.out.println("-----------------------------");
        for(String s : "HANGING CRAWLING GROUND".split(" ")) {
            Shrubbery shrub = Enum.valueOf(Shrubbery.class, s);
            System.out.println(shrub);
        }
    }

    // 自定义方法
    enum OzWitch {
        WEST("Miss Gulch"), NORTH("Glinda"), EAST("Beijing"), SOUTH("AFICA");

        private String desc;

        OzWitch(String description) {
            this.desc = description;
        }

        public String getDesc() {
            return desc;
        }

        public static void main(String[] args) {
            for(OzWitch witch : OzWitch.values()) {
                System.out.println(witch + " : " + witch.getDesc());
            }
        }
    }

    // switch语句中的enum
    enum Signal { GREEN, YELLOW, RED }

    static class TrafficLight {
        Signal color = Signal.RED;

        public void change() {
            switch (color) {
                // 这个地方不能用Signal.RED,因为case里面必须是常量不能是变量. 但是这个地方可以使用静态导入的方式
                case RED: color = Signal.GREEN; break;
                case GREEN: color = Signal.YELLOW; break;
                case YELLOW: color = Signal.RED; break;
            }
        }

        @Override
        public String toString() {
            return "The traffic light is " + color;
        }

        public static void main(String[] args) {
            TrafficLight t = new TrafficLight();
            for(int i = 0; i < 7; i++) {
                System.out.println(t);
                t.change();
            }
        }
    }
}
