package cn.tim.thinking.enums;

import java.text.DateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;

/**
 * Created by LuoLiBing on 16/12/12.
 * EnumMap是一种特殊的Map,它要求其中的键(key)必须来自一个enum.
 * 由于enum本身的限制,所有EnumMap在内部可由数组实现. 因此EnumMap的速度很快,我们可以放心地使用enum实例在EnumMap中进行查找操作.
 */
public class EnumMapDemo1 {

    // 命令设计模式
    interface Command {
        void action();
    }

    static class EnumMaps {
        public static void main(String[] args) {
            // EnumMap<Enum, Command>枚举对应命令
            EnumMap<EnumSetDemo1.AlarmPoints, Command> em = new EnumMap<>(EnumSetDemo1.AlarmPoints.class);
            em.put(EnumSetDemo1.AlarmPoints.KITCHEN, () -> System.out.println("Kitchen fire!"));
            em.put(EnumSetDemo1.AlarmPoints.BATHROOM, () -> System.out.println("Bathroom alert!"));
            em.forEach((k, v) -> {
                System.out.println(k.name());
                v.action();
            });
            try {
                em.get(EnumSetDemo1.AlarmPoints.UTILITY).action();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // 常量相关方法, 要实现常量相关的方法,需要为enum定义一个或多个abstract抽象方法, 然后每个enum实例实现该抽象方法.
    enum ConstantSpecificMethod {
        // 匿名内部类 DATE_TIME是一个常量字段的名称
        DATE_TIME {
            @Override
            String getInfo() {
                return DateFormat.getDateInstance().format(new Date());
            }
        },
        CLASSPATH {
            @Override
            String getInfo() {
                return System.getenv("CLASSPATH");
            }
        },
        VERSION {
            @Override
            String getInfo() {
                return System.getProperty("java.version");
            }
        };

        // 要为每个常量添加相关方法,需要在enum中添加抽象方法. 表驱动的代码
        abstract String getInfo();

        public static void main(String[] args) {
            for(ConstantSpecificMethod method : values()) {
                System.out.println(method.getInfo());
            }
        }
    }



    static class CarWash {
        public enum Cycle {
            UNDERBODY {
                @Override
                void action() {
                    System.out.println("Spraying the underbody");
                }
            },
            WHEELWASH {
                @Override
                void action() {
                    System.out.println("Washing the wheels");
                }
            },
            PREWASH {
                @Override
                void action() {
                    System.out.println("Loosening the dirt");
                }
            },
            BASIC {
                @Override
                void action() {
                    System.out.println("The basic wash");
                }
            };

            abstract void action();
        }

        EnumSet<Cycle> cycles = EnumSet.of(Cycle.BASIC, Cycle.UNDERBODY);

        public void add(Cycle cycle) {
            cycles.add(cycle);
        }

        public void washCar() {
            for(Cycle c : cycles) {
                c.action();
            }
        }

        @Override
        public String toString() {
            return cycles.toString();
        }

        public static void main(String[] args) {
            CarWash wash = new CarWash();
            System.out.println(wash);
            wash.washCar();
            wash.add(Cycle.UNDERBODY);
            wash.add(Cycle.PREWASH);
            System.out.println(wash);
            wash.washCar();
        }
    }


    enum OverrideConstantSpecific {
        NUT, BOLT, WASHER {
            @Override
            void f() {
                System.out.println("Overridden method");
            }
        };

        /**
         *
         * final static OverrideConstantSpecific WASHER {
         *      // 匿名内部类
         *      void f() {}
         * }
         */

        void f() {
            System.out.println("default behavior");
        }

        public static void main(String[] args) {
            for(OverrideConstantSpecific ocs : values()) {
                System.out.println(ocs + ": ");
                ocs.f();
            }
        }
    }

}
