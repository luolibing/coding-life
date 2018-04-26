package cn.tim.thinking.clazz14.count;

import cn.tim.thinking.clazz14.entity.Pet;

/**
 * Created by LuoLiBing on 16/9/27.
 */
public class Pets {

    public final static PetCreator creator = new PetCreator.RegisteCreator();

    public static Pet randomPet() {
        return creator.randomPet();
    }

    public static Pet[] createArray(int size) {
        return creator.createArray(size);
    }
}
