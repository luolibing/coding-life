package cn.tim.thinking.clazz14.count;


import cn.tim.thinking.clazz14.entity.*;

import java.util.HashMap;

/**
 * Created by LuoLiBing on 16/9/27.
 * 静态instanceof 判断计数
 */
public class PetCount {

    public static class PetCounter extends HashMap<String, Integer> {
        public void count(String type) {
            compute(type, (k, v) -> v == null ? 1 : v + 1);
        }

        public static void countPets(PetCreator creator) {
            PetCounter counter = new PetCounter();
            for(Pet pet : creator.createArray(20)) {
                System.out.println(pet.getClass().getSimpleName() + " ");
                if(pet instanceof Dog) {
                    counter.count("dog");
                }
                if(pet instanceof Cat) {
                    counter.count("cat");
                }
                if(pet instanceof Mutt) {
                    counter.count("mutt");
                }
                if(pet instanceof Manx) {
                    counter.count("manx");
                }
                if(pet instanceof Cymric) {
                    counter.count("cymric");
                }
                if(pet instanceof Pet) {
                    counter.count("Pet");
                }
            }
            System.out.println();
            counter.forEach((key, val) -> System.out.println(key + "=" + val));
        }
    }

    public static void main(String[] args) {
        PetCounter.countPets(new PetCreator.ForNameCreator());
    }
}
