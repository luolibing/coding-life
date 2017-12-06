package cn.tim.thinking.collection.complex17;

import java.util.BitSet;
import java.util.Random;

/**
 * Created by LuoLiBing on 17/1/7.
 * 如果想要高效率地存储大量"开/关"信息, BitSet是很好的选择. 不过它的效率仅是对空间而言, 如果需要高效的访问时间, BitSet比本地数组稍微慢一些.
 * BitSet最小容量为long: 64位.
 */
public class BitSetDemo {

    public static void printBitSet(BitSet b) {
        System.out.println("bits: " + b);
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < b.size(); i++) {
            builder.append(b.get(i) ? "1" : "0");
        }
        System.out.print("bit pattern: " + builder);
    }


    /**
     * 默认的BitSet是64位的, 所以只要小于64, 就不会进行扩容.
     * @param args
     */
    public static void main(String[] args) {
        Random rand = new Random(47);
        byte bt = (byte) rand.nextInt();
        BitSet bb = new BitSet();
        for(int i = 7; i >=0; i--) {
            if(((1 << i) & bt) != 0) {
                bb.set(i);
            } else {
                bb.clear(i);
            }
        }
        System.out.println("byte value: " + bt);
        printBitSet(bb);
        System.out.println();
        System.out.println(bb.size());

        bb.set(1024);
        printBitSet(bb);
        // 17个long值
        System.out.println(bb.size());

        BitSet bs = new BitSet();
        bs.set(63);
        // flip()设置指定位置的值为它的补码, 如果是0,则取1, 1则取0
        bs.flip(3);
        printBitSet(bs);
        System.out.println();
        System.out.println(bs.size());
    }
}
