package cn.tim.thinking.clazz14.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 16/9/27.
 */
public class Pet extends Individual {
    public Pet(String name) { super(name);}
    public Pet() {super();}

    public static List<Class<? extends Pet>> petClassList = new ArrayList<>();

    static {
        petClassList.add(Cat.class);
        petClassList.add(Cymric.class);
        petClassList.add(Dog.class);
        petClassList.add(EgyptianMau.class);
        petClassList.add(Manx.class);
        petClassList.add(Mutt.class);
        petClassList.add(Pug.class);
        petClassList.add(Pet.class);
    }

    public static Pet create(int index) {
        try {
            return petClassList.get(index).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}