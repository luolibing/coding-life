package cn.tim.thinking.enums;

import java.util.Random;

/**
 * Created by LuoLiBing on 16/12/12.
 */
public class RandomEnumDemo1 {

    /**
     * 随机选取
     */
    static class Enums {
        private static Random rand = new Random(47);

        public static <T extends Enum<T>> T random(Class<T> clazz) {
            // 通过class.getEnumConstants()之后获取数组,然后再在这个数组中进行随机读取
            return random(clazz.getEnumConstants());
        }

        public static <T> T random(T[] array) {
            return array[rand.nextInt(array.length)];
        }
    }

    enum Acticity { SITTING, LYING, STANDING, HOPPING }

    static class RandomTest {
        public static void main(String[] args) {
            for(int i = 0; i < 20; i++) {
                System.out.println(Enums.random(Acticity.class) + " ");
            }
        }
    }


    // 方式一: 接口中嵌套枚举, 枚举实现接口
    // 无法从enum继承子类,如果希望扩展原enum中的元素.
    // 使用子类将enum中的元素进行分组, 可以达到将枚举元素分类组织的目的
    // 一下Food接口分为几组: 甜品, 主食, 咖啡等等. 所有的上例中的所有东西都是Food
    public interface Food {

        // enum实现接口, 对于enum而言,实现接口是使其子类化的唯一方法
        enum Appetizer implements Food {
            SALAD, SOUP, SPRING_ROLLS;
        }

        enum MainCourse implements Food {
            LASAGNE, BURRITO, PAID_THAI,
            LENTILS, HUMMOUS, VINDALOO
        }

        enum Dessert implements Food {
            TIRAMISU, GELATO, BLACK_FOREST_CAKE,
            FRUIT, CREME_CARAMEL;
        }

        enum Coffee implements Food {
            BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
            LATTE, CAPPUCCINO, TEA, HERB_TEA;
        }

        enum Bread implements Food {
            BREAD, MILK
        }
    }

    /**
     * 使用接口组织枚举
     */
    static class TypeOfFood {
        public static void main(String[] args) {
            // 这样可以将enum进行泛化,  可以向上转型为Food
            Food food = Food.Appetizer.SALAD;
            food = Food.MainCourse.BURRITO;
        }
    }


    // 方式二: 枚举中的枚举
    // 如果需要与一大堆类型打交道时,接口就不如enum好用了. 例如,如果想创建一个"枚举的枚举"
    enum  Course {
        // 枚举的枚举, 每一个枚举都对应着一组枚举类型
        APPETIZER(Food.Appetizer.class),
        MAINCOURSE(Food.MainCourse.class),
        DESSERT(Food.Dessert.class),
        COFFEE(Food.Coffee.class),
        BREAD(Food.Bread.class);

        private Food[] values;

        Course(Class<? extends Food> kind) {
            values = kind.getEnumConstants();
        }

        public Food randomSelection() {
            return Enums.random(values);
        }
    }

    static class Meal {
        public static void main(String[] args) {
            for(int i = 0; i < 5; i++) {
                // 遍历菜单类型,例如: 汤 主食 甜品类等等
                for(Course course : Course.values()) {
                    // 然后从每类中挑选一种
                    Food food = course.randomSelection();
                    System.out.println(food);
                }
                System.out.println("---");
            }
        }
    }


    // 方式三: 枚举嵌套
    // 另一种更简洁的管理枚举的方法,就是讲一个enum嵌套在另外一个enum内
    enum SecurityCategory {

        STOCK(Security.Stock.class), BOND(Security.Bond.class);

        Security[] values;

        SecurityCategory(Class<? extends Security> kind) {
            values = kind.getEnumConstants();
        }

        interface Security {
            enum Stock implements Security { SHORT, LONG, MARGIN }
            enum Bond implements Security { MUNICIPAL, JUNK }
        }

        public Security randomSelection() {
            return Enums.random(values);
        }

        public static void main(String[] args) {
            for(int i = 0; i < 10; i++) {
                SecurityCategory securityCategory = Enums.random(SecurityCategory.class);
                System.out.println(securityCategory + " : " + securityCategory.randomSelection());
            }
        }
    }
}
