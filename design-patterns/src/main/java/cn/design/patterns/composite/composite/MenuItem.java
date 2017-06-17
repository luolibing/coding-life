package cn.design.patterns.composite.composite;

import java.util.Iterator;

/**
 * Created by LuoLiBing on 16/6/18.
 * 单个元素
 */
public class MenuItem extends MenuComponent {

    private String name;

    private String description;

    private boolean vegetarian;

    private double price;

    public MenuItem(String name, String description, boolean vegetarian, double price) {
        this.name = name;
        this.description = description;
        this.vegetarian = vegetarian;
        this.price = price;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public boolean isVegetarian() {
        return vegetarian;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void print() {
        System.out.println(" " + getName());
        if(isVegetarian()) {
            System.out.println("(v)");
        }
        System.out.println(", " + getPrice());
        System.out.println("  -- " + getDescription());
    }

    /**
     * 不可以遍历的元素,直接返回一个空的迭代器
     * @return
     */
    @Override
    public Iterator createIterator() {
        return new NullIterator();
    }
}
