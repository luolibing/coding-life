package cn.tim.thinking.clazz14.count;

import cn.tim.thinking.clazz14.entity.Pet;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuoLiBing on 16/9/27.
 * 动态Class.isInstance(instance)
 */
public class PetCount3 {

    static class PetCounter extends LinkedHashMap<Class<? extends Pet>, Integer> {
        public PetCounter() {
            super(map(PetCreator.LiteralPetCreator.allTypes));
        }

        private static Map<Class<? extends Pet>, Integer> map(List<Class<? extends Pet>> petList) {
            Map<Class<? extends Pet>, Integer> count = new HashMap<>();
            for(Class<? extends Pet> pet : petList) {
                count.put(pet, 0);
            }
            return count;
        }

        public void count(Pet pet) {
            for(Map.Entry<Class<? extends Pet>, Integer> entry : entrySet()) {
                // 动态判断实例类型,省去了繁琐的instanceof
                if(entry.getKey().isInstance(pet)) {
                    put(entry.getKey(), entry.getValue() + 1);
                }
            }
        }

        public String toString() {
            StringBuilder result = new StringBuilder("{");
            for(Map.Entry<Class<? extends Pet>, Integer> entry : entrySet()) {
                result.append(entry.getKey().getSimpleName());
                result.append("=");
                result.append(entry.getValue());
                result.append(", ");
            }
            result.delete(result.length() - 2, result.length());
            result.append("}");
            return result.toString();
        }
    }

    public static void main(String[] args) {
        PetCounter petCounter = new PetCounter();
        for(Pet pet : Pets.createArray(10)) {
            System.out.println(pet.getClass().getSimpleName() + " ");
            petCounter.count(pet);
        }
        System.out.println();
        System.out.println(petCounter);
    }
}
