package cn.design.patterns.template.sort;

/**
 * Created by LuoLiBing on 16/6/12.
 */
public class Duck implements Comparable<Duck> {

    private String name;

    private int weight;

    public Duck(String  name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String toString() {
        return name + " weights " + weight;
    }

    @Override
    public int compareTo(Duck duck) {
        if(duck == null) {
            throw new NullPointerException("不能为空");
        }
        if(weight < duck.weight) {
            return -1;
        } else if(weight == duck.weight) {
            return 0;
        } else {
            return -1;
        }
    }
}
