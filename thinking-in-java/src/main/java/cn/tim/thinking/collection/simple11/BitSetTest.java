package cn.tim.thinking.collection.simple11;

import cn.tim.thinking.collection.simple11.collections.CollectionCreator;
import org.junit.Test;

import java.util.BitSet;

/**
 * Created by LuoLiBing on 16/9/29.
 */
public class BitSetTest {

    private CollectionCreator creator = new CollectionCreator();

    @Test
    public void test1() {
        BitSet bitSet = new BitSet();
        Integer[] array = creator.createRandomArray(10000);
        for(int i = 0, size = array.length; i < size; i++) {
            bitSet.set(array[i]);
        }

        System.out.println(bitSet);
        System.out.println(bitSet.get(10));
    }
}
