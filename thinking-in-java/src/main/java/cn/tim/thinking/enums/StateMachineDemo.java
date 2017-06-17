package cn.tim.thinking.enums;

import java.util.EnumMap;
import java.util.Random;
import static cn.tim.thinking.enums.StateMachineDemo.Input.*;

/**
 * Created by LuoLiBing on 16/12/12.
 * 状态机
 * 枚举类型非常适合用来创建状态机. 一个状态机可以具有有限个特定的状态,它根据输入,从一个状态转移到下一个状态,不过也可能存在瞬时状态,而一旦任务执行结束,状态机立即离开瞬时状态.
 *
 */
public class StateMachineDemo {

    // 售货机输入
    enum Input {
        NICKEL(5), DIME(10), QUARTER(25), DOLLAR(100),
        TOOTHPASTE(200), CHIPS(75), SODA(100), SOAP(50),
        // 其他的都带有价格,只有下面2个没有,并且不支持amount()
        ABORT_TRANSACTION {
            @Override
            int amount() {
                // 不支持抛出异常
                throw new RuntimeException("ABORT.amount()");
            }
        },
        STOP {
            @Override
            int amount() {
                // 不支持抛出异常
                throw new RuntimeException("SHUT_DOWN.amount()");
            }
        };

        int value;

        Input(int val) {
            this.value = val;
        }

        Input() {}

        int amount() {
            return value;
        }

        static Random rand = new Random(47);

        public static Input randomSelection() {
            return values()[rand.nextInt(values().length - 2)];
        }
    }


    // 输入分类, 枚举的枚举
    enum Category {

        // 钱币类输入
        MONEY(NICKEL, DIME, QUARTER, DOLLAR),

        // 购买选项输入
        ITEM_SELECTION(TOOTHPASTE, CHIPS, SODA, SOAP),

        // 终止交易输入
        QUIT_TRANSACTION(ABORT_TRANSACTION),

        // 停止输入
        SHUT_DOWN(STOP);

        private Input[] values;

        Category(Input...types) {
            this.values = types;
        }

        private static EnumMap<Input, Category> categories = new EnumMap<>(Input.class);

        static {
            for(Category c : Category.values()) {
                for(Input input : c.values) {
                    categories.put(input, c);
                }
            }
        }

        public static Category categorize(Input input) {
            return categories.get(input);
        }
    }


    // 售票机
    static class VendingMachine {

        private static State state = State.RESTING;

        private static int amount = 0;

        private static Input selection = null;

        // 瞬时状态
        enum StateDuration { TRANSIENT }

        // 状态枚举, 对于每种不同的输入都有对应的处理方式
        enum State {
            // 重置
            RESTING {
                // RESTING的下一个指令
                @Override
                void next(Input input) {
                    switch (Category.categorize(input)) {
                        case MONEY:
                            amount += input.amount();
                            state = ADDING_MONEY;
                            break;
                        case SHUT_DOWN:
                            state = TERMINAL;
                            break;
                        // 其他的指令默认为不操作
                        default:
                    }
                }
            },

            // 投钱
            ADDING_MONEY {
                @Override
                void next(Input input) {
                    switch (Category.categorize(input)) {
                        // 继续投
                        case MONEY:
                            amount += input.amount();
                            break;
                        case ITEM_SELECTION:
                            selection = input;
                            // 钱不够,不能购买
                            if(amount < selection.amount()) {
                                System.out.println("Insufficient money for " + selection);
                            } else {
                                // 否则为分配状态
                                state = DISPENSING;
                            }
                            break;
                        case QUIT_TRANSACTION:
                            // 如果选择终止,则需要退款
                            state = GIVING_CHANGE;
                            break;
                        default:
                    }
                }
            },

            // 分配
            DISPENSING {
                @Override
                void next() {
                    System.out.println("here is your " + selection);
                    // 扣除掉购买所需的钱
                    amount -= selection.amount();
                    // 退还剩余钱币
                    state = GIVING_CHANGE;
                }
            },

            // 退还剩余钱币
            GIVING_CHANGE {
                @Override
                void next() {
                    if(amount > 0) {
                        System.out.println("Your change : " + amount);
                        amount = 0;
                    }
                    state = RESTING;
                }
            },

            // 终点
            TERMINAL {
                @Override
                void next() {
                    System.out.println("Halted");
                }
            };

            // 是否是瞬时状态
            private boolean isTransient = false;

            State() {}

            State(StateDuration trans) {
                isTransient = true;
            }

            // 默认抛出异常, 下一个输入
            void next(Input input) {
                throw new RuntimeException("Only call next(Input input) for non-transient states");
            }

            // 瞬时状态,不需要输入
            void next() {
                throw new RuntimeException("Only call next() for StateDuration.TRANSIENT states");
            }

            // 输出, 默认输出为打印剩余数量
            void output() {
                System.out.println(amount);
            }
        }


        static void run(Generator<Input> gen) {
            while (state != State.TERMINAL) {
                state.next(gen.next());
                while (state.isTransient) {
                    state.next();
                }
                state.output();
            }
        }

        interface Generator<T> {
            T next();
        }

        static class RandomInputGenerator implements Generator<Input> {

            @Override
            public Input next() {
                return Input.randomSelection();
            }
        }

        public static void main(String[] args) {
            RandomInputGenerator generator = new RandomInputGenerator();
            run(generator);
        }
    }
}
