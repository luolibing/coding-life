package cn.tim.thinking.clazz14.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by LuoLiBing on 16/9/28.
 * 将对象的创建交给类自己去完成
 * 创建对象统一实现factory接口,并且每个类自己都有自己的factory来创建出自身.
 * 统一一个地方来生产出继承体系中的对象
 * 一种方法是使用添加静态初始化器,以便该初始化器可以将它的类添加到某个List中,但是初始化器只有在类首次被加载的情况下才能被调用.
 * 另一种方法是将factory置于一个列表,放在一个位于中心明显的地方,而基类可能是这个最佳位置
 */
public class Part {

    public String toString() {
        return getClass().getSimpleName();
    }

    static List<Factory<? extends Part>> partFactories = new ArrayList<>();

    static List<Class<? extends Part>> partClasses = new ArrayList<>();

    static int partFactoriesSize;

    static int partClassSize;

    static {
        // 将生成类的工厂至于一个List中
        partFactories.add(new FuelFilter.Factory());
        partFactories.add(new AirFilter.Factory());
        partFactories.add(new OilFilter.Factory());
        partFactories.add(new FanBelt.Factory());
        partFactories.add(new GeneratorBelt.Factory());

        // 使用Class类型class
        partClasses.add(FuelFilter.class);
        partClasses.add(AirFilter.class);
        partClasses.add(OilFilter.class);
        partClasses.add(FanBelt.class);
        partClasses.add(GeneratorBelt.class);

        // 大小
        partFactoriesSize = partFactories.size();
        partClassSize = partClasses.size();
    }

    private static Random rand = new Random(47);

    public static Part createRandom() {
        int n = rand.nextInt(partFactoriesSize);
        return partFactories.get(n).create();
    }

    public static Part createRandomClass() {
        int n = rand.nextInt(partClassSize);
        try {
            return partClasses.get(n).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
