package cn.design.patterns.composite.iteratorsimple;

import cn.design.patterns.composite.iterator.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 16/6/18.
 * 迭代器模式
 * Iterator接口有两个方法next(),hasNext(),这样可以扩展到任何集合,迭代器封装了遍历集合的内部实现
 * 1 定义Menu接口,createIterator()方法
 * 2 定义Iterator接口,封装内部实现
 *
 * 迭代器模式
 * 迭代器模式提供一种方法顺序访问一个聚合对象的各个元素,而又不暴露其内部的表示.
 *
 * 单一责任
 * 一个雷应该只有一个引起变化的原因,类的每个责任都有改变的潜在区域.超过一个责任,意味着超过一个改变的区域
 * 这个原则告诉我们,尽量让每个类保持单一责任
 *
 * 内聚
 * 当一个模块或一个类被设计成只支持一组相关的功能时,我们说他具有高内聚;反之,当被设计成支持一组不相关的功能时,我们说它具有低内聚
 */
public class MenuTestDrive {

    public static void main(String[] args) {
        PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
        DinnerMenu dinnerMenu = new DinnerMenu();
        CafeMenu cafeMenu = new CafeMenu();

        List<Menu<MenuItem>> menuList = new ArrayList<>();
        menuList.add(pancakeHouseMenu);
        menuList.add(dinnerMenu);
        menuList.add(cafeMenu);
        Waitress waitress = new Waitress(menuList);
        waitress.printMenu();
    }
}
