package cn.tim.thinking.clazz14.count;

import cn.tim.thinking.clazz14.entity.Pet;

import java.util.HashMap;

/**
 * Created by LuoLiBing on 16/9/27.
 * 统计继承体系计数,使用isAssignableFrom()
 */
public class TypeCounter extends HashMap<Class<?>, Integer> {

    private Class<?> baseType;
    public TypeCounter(Class<?> baseType) {
        this.baseType = baseType;
    }

    public void count(Object obj) {
        Class<?> type = obj.getClass();
        if(!baseType.isAssignableFrom(type)) {
            throw new RuntimeException("error type!!");
        }
        countClass(type);
    }

    private void countClass(Class<?> type) {
        // 类型本身
        compute(type, (k, v) -> v == null ? 1 : v + 1);

        // 继承结构递归处理
        Class<?> superclass = type.getSuperclass();
        if(superclass != null && baseType.isAssignableFrom(superclass)) {
            countClass(superclass);
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for(Entry<Class<?>, Integer> entry : entrySet()) {
            result.append(entry.getKey().getSimpleName());
            result.append("=");
            result.append(entry.getValue());
            result.append(", ");
        }
        result.delete(result.length() - 2, result.length());
        result.append("}");
        return result.toString();
    }

    public static void main(String[] args) {
        TypeCounter counter = new TypeCounter(Pet.class);
        for(Pet pet : Pets.createArray(20)) {
            System.out.println(pet.getClass().getSimpleName() + " ");
            counter.count(pet);
        }
        System.out.println();
        System.out.println(counter);
    }
}
