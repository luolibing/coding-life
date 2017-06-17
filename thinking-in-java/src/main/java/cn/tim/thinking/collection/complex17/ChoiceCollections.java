package cn.tim.thinking.collection.complex17;

import cn.tim.thinking.array.TestArrayData;

import java.util.*;

/**
 * Created by LuoLiBing on 17/1/4.
 * 选择接口的不同实现
 */
public class ChoiceCollections {

    /**
     * 四种容器:
     * Map, List, Set和Queue
     *
     * 容器之间的区别通常归结为由什么在背后支持他们, 也就是说所使用的接口由什么样的底层数据结构实现.
     * 例如ArrayList和LinkedList都实现了List接口, 所以他们的基本操作是相同的.
     * 然而ArrayList底层是数组, LinkedList是由双向链表实现. 因此如果要经常在表中插入或删除元素,LinkedList比较合适; 否则应该使用速度更快的ArrayList.
     *
     * Set可被实现为TreeSet, HashSet和LinkedHashSet.
     * HashSet是最常用的, 查询速度最快; LinkedHashSet保持元素的插入顺序; TreeSet基于TreeMap, 生成一个处于排序状态的Set
     */

    public abstract static class Test<C> {
        String name;

        public Test(String name) {
            this.name = name;
        }

        // 测试方法, 包含一个容器container, 和测试参数TestParam
        abstract int test(C container, TestParam tp);
    }

    static class TestParam {
        public final int size;
        public final int loops;
        public TestParam(int size, int loops) {
            this.size = size;
            this.loops = loops;
        }

        public static TestParam[] array(int...values) {
            int size = values.length / 2;
            TestParam[] result = new TestParam[size];
            int n = 0;
            for(int i = 0; i < size; i++) {
                result[i] = new TestParam(values[n++], values[n++]);
            }
            return result;
        }

        public static TestParam[] array(String[] values) {
            int[] vals = new int[values.length];
            for(int i = 0; i < vals.length; i++) {
                // 对输入的字符串进行decode()为Integer, 例如负数, 16进制数
                vals[i] = Integer.decode(values[i]);
            }
            return array(vals);
        }
    }


    static class Tester<C> {
        public static int fieldWith = 8;

        public static TestParam[] defaultParams = TestParam.array(10, 5000, 100, 5000, 1000, 5000, 10000, 500);

        protected C container;

        private String headLine = "";

        private List<Test<C>> tests;

        private static int sizeWith = 5;

        private static String sizeField = "%" + sizeWith + "s";

        private TestParam[] paramList = defaultParams;

        private static String stringField() {
            return "%" + fieldWith + "s";
        }

        private static String numberField() {
            return "%" + fieldWith + "d";
        }

        protected C initialize(int size) {
            return container;
        }

        public Tester(C container, List<Test<C>> tests) {
            this.container = container;
            this.tests = tests;
            if(container != null) {
                headLine = container.getClass().getSimpleName();
            }
        }

        public Tester(C container, List<Test<C>> tests, TestParam[] paramList) {
            this(container, tests);
            this.paramList = paramList;
        }

        public void setHeadLine(String newHeadLine) {
            this.headLine = newHeadLine;
        }

        public static <C> void run(C cntnr, List<Test<C>> tests) {
            new Tester<>(cntnr, tests);
        }

        public static <C> void run(C cntnr, List<Test<C>> tests, TestParam[] paramList) {
            new Tester<>(cntnr, tests, paramList);
        }

        private void displayHeader() {
            int width = fieldWith * tests.size() + sizeWith;
            int dashLength = width - headLine.length() - 1;
            StringBuilder head = new StringBuilder(width);
            for(int i = 0; i < dashLength / 2; i++) {
                head.append("-");
            }
            head.append(" ");
            head.append(headLine);
            head.append(" ");

            for(int i = 0; i < dashLength / 2; i++) {
                head.append("-");
            }
            System.out.println(head);
            System.out.format(sizeField, "size");
            for(Test test : tests) {
                System.out.format(stringField(), test.name);
            }
            System.out.println();
        }


        public void timedTest() {
            displayHeader();
            for(TestParam param : paramList) {
                System.out.format(sizeField, param.size);
                for(Test<C> test : tests) {
                    C kcontainer = initialize(param.size);
                    long start = System.nanoTime();
                    int reps = test.test(kcontainer, param);
                    long duration = System.nanoTime() - start;
                    long timePerRep = duration / reps;
                    System.out.format(numberField(), timePerRep);
                }
            }
            System.out.println();
        }
    }

    @org.junit.Test
    public void decode() {
        System.out.println(Integer.decode("0x2"));
        System.out.println(Integer.valueOf("0x2"));
    }


    /**
     * 对List的选择
     * 对于随机访问来说, 使用数组支撑的List和ArrayList, 无论列表的大小如何, 这些访问都很快速和一致. 而对于LinkedList, 访问时间随着列表的增大将明显增加. 所以对于大量的随机访问, 链表不会是一种好的选择
     * 在列表中间插入元素, 对于ArrayList来说, 当列表变大时, 开销将变得高昂, 但对于LinkedList来说, 相对比较低廉, 并且不会随列表尺寸而发生变化. 因为ArrayList插入元素, 必须将后面的元素往后推, 而LinkedList只需要链接新的元素, 不必吸怪列表中剩余的元素, 所以无论列表尺寸的大小如何变化, 其代价大致相同.
     *
     *
     *
     */
    static class ListPerformance {
        static Random rand = new Random();

        static int reps = 1000;

        static List<Test<List<Integer>>> tests = new ArrayList<>();

        static List<Test<LinkedList<Integer>>> qTests = new ArrayList<>();

        static  {
            // 添加方法
            tests.add(new Test<List<Integer>>("add") {

                @Override
                int test(List<Integer> list, TestParam tp) {
                    int loops = tp.loops;
                    int listSize = tp.size;
                    for(int i = 0; i < loops; i++) {
                        list.clear();
                        for(int j = 0; j < listSize; j++) {
                            list.add(j);
                        }
                    }
                    return loops * listSize;
                }
            });

            // get方法, 随机访问
            tests.add(new Test<List<Integer>>("get") {
                @Override
                int test(List<Integer> list, TestParam tp) {
                    int loops = tp.loops * reps;
                    int listSize = list.size();
                    for(int i = 0; i < loops; i++) {
                        list.get(rand.nextInt(listSize));
                    }
                    return loops;
                }
            });

            // set, 随机设置
            tests.add(new Test<List<Integer>>("set") {
                @Override
                int test(List<Integer> list, TestParam tp) {
                    int loops = tp.loops * reps;
                    int listSize = list.size();
                    for(int i = 0; i < loops; i++) {
                        list.set(rand.nextInt(listSize), 47);
                    }
                    return loops;
                }
            });
        }

        public static void main(String[] args) {
            Tester<List<Integer>> arrayTest = new Tester<List<Integer>>(null, tests.subList(1, 3)) {
                @Override
                protected List<Integer> initialize(int size) {
                    try {
                        Integer[] ia = TestArrayData.Generated.array(Integer.class, new TestArrayData.CountingGenerator.Integer(), size);
                        return Arrays.asList(ia);
                    } catch (IllegalAccessException | InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            arrayTest.setHeadLine("Array as List");
            arrayTest.timedTest();
            Tester.defaultParams = TestParam.array(10, 5000, 100, 5000, 1000, 1000, 10000, 200);
        }
    }


    /**
     * 对Set的选择
     * HashSet的性能基本上总是比TreeSet好, 特别是在添加和查询元素时, 而这两个操作也是最重要的操作.
     * TreeSet存在的唯一原因是它可以维持元素的排序状态; 用TreeSet迭代通常比用HashSet要快.
     * 对于插入操作, LinkedHashSet比HashSet的代价更高; 这是由于维护了顺序链表带来的额外开销.
     */
    static class SetPerformance {
        static List<Test<Set<Integer>>> tests = new ArrayList<>();

        static {
            tests.add(new Test<Set<Integer>>("add") {

                @Override
                int test(Set<Integer> set, TestParam tp) {
                    int loops = tp.loops;
                    int size = tp.size;
                    for(int i = 0; i < loops; i++) {
                        set.clear();
                        for(int j = 0; j < size; j++) {
                            set.add(j);
                        }
                    }
                    return loops * size;
                }
            });

            tests.add(new Test<Set<Integer>>("contains") {
                @Override
                int test(Set<Integer> set, TestParam tp) {
                    int loops = tp.loops;
                    int span = tp.size * 2;
                    for(int i = 0; i < loops; i++) {
                        for(int j = 0; j < span; j++) {
                            set.contains(j);
                        }
                    }
                    return loops * span;
                }
            });

            tests.add(new Test<Set<Integer>>("iterate") {
                @Override
                int test(Set<Integer> set, TestParam tp) {
                    int loops = tp.loops * 10;
                    for(int i = 0; i < loops; i++) {
                        Iterator<Integer> it = set.iterator();
                        while (it.hasNext()) {
                            it.next();
                        }
                    }
                    return loops * set.size();
                }
            });
        }

        public static void main(String[] args) {
            Tester.fieldWith = 10;
            Tester.run(new TreeSet<>(), tests);
            Tester.run(new HashSet<>(), tests);
            Tester.run(new LinkedHashSet<>(), tests);
        }
    }


    /**
     * 对Map的选择
     * 除了IdentityHashMap, 所有的map实现的插入操作都会随着Map尺寸的变大而明显变慢. 但是查找的代价通常比插入的要小很多.
     * TreeMap通常比HashMap要慢, 因为TreeMap维护了排序.
     * LinkedHashMap在插入时比HashMap慢一点, 因为它维护散列数据结构的同事还要维护链表保持插入顺序. 正是由于这个列表, 使其迭代速度更快.
     * IdentityHashMap则具有完全不同的性能, 因为它使用==而不是equals()来比较元素.
     */

}
