package cn.tim.thinking.clazz14.count;

import cn.tim.thinking.clazz14.entity.*;

import java.util.*;

/**
 * Created by LuoLiBing on 16/9/27.
 * 创建出一批Pet类, 使用types抽象模板方法来获取Class集合
 */
public abstract class PetCreator {

    private Random rand = new Random(100);

    public abstract List<Class<? extends Pet>> types();

    public Pet randomPet() {
        int n = rand.nextInt(types().size());
        try {
            return types().get(n).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Pet[] createArray(int size) {
        Pet[] result = new Pet[size];
        for(int i = 0; i < size; i++) {
            result[i] = randomPet();
        }
        return result;
    }

    /**
     * Class.forName()创建
     */
    public static class ForNameCreator extends PetCreator {

        private static List<Class<? extends Pet>> types = new ArrayList<>();

        private static String[] typeNames = {
//                "cn.tim.thinking.clazz14.CastClassTest$Person",
                "cn.tim.thinking.clazz14.entity.Dog",
                "cn.tim.thinking.clazz14.entity.Cat",
                "cn.tim.thinking.clazz14.entity.Pet",
                "cn.tim.thinking.clazz14.entity.Mutt",
                "cn.tim.thinking.clazz14.entity.Pug",
                "cn.tim.thinking.clazz14.entity.EgyptianMau",
                "cn.tim.thinking.clazz14.entity.Manx",
                "cn.tim.thinking.clazz14.entity.Cymric",
        };

        static {
            loader();
        }

        /**
         * 加载所有的Class
         */
        private static void loader() {
            for(String name : typeNames) {
                try {
                    types.add((Class<? extends Pet>) Class.forName(name));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public List<Class<? extends Pet>> types() {
            return types;
        }
    }

    /**
     * 类字面常量创建
     */
    public static class LiteralPetCreator extends PetCreator {

        public final static List<Class<? extends Pet>> allTypes = Collections.unmodifiableList(Arrays.asList(
                Dog.class, Cat.class, Pet.class, Mutt.class, Pug.class, Manx.class, Cymric.class
        ));


        @Override
        public List<Class<? extends Pet>> types() {
            return allTypes;
        }
    }

    public static class RegisteCreator extends PetCreator  {

        int size = Pet.petClassList.size();

        private Random rand = new Random(47);

        @Override
        public Pet randomPet() {
            int i = rand.nextInt(size);
            return Pet.create(i);
        }

        @Override
        public List<Class<? extends Pet>> types() {
            return Pet.petClassList;
        }
    }

}
