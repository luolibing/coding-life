package cn.tim.thinking.collection.complex17;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LuoLiBing on 17/1/4.
 * HashMap的性能因子:
 * 1 容量(表中桶位数)
 * 2 初始容量(初始桶位数)
 * 3 尺寸(当前存储的项数)
 * 4 负载因子(尺寸/容量), 空表为0, 半满为0.5. 当负载情况达到负载因子水平时, 容器将自动扩容(增加桶位数), 实现方式是使容量大致加倍, 并重新将现有对象分布到新的桶位集中(在散列)
 *   HashMap使用的默认负载因子是0.75(当尺寸/容量大于0.75时, 才进行再散列), 这个因子在时间和空间代价之间达到了平衡. 更高的负载因子, 表明map更满, 空间利用率较高, 但是会增加查找代价. 而get()和put()操作是我们大部分的操作.
 *   如果知道将要在HashMap中存储多少项, 那么创建一个具有恰当大小的初始容量将可以避免自动再散列的开销.
 *
 */
public class HashMapPerformance {

    /**
     * 负载因子: 通常低负载因子, 具备更高的查找性能(因为散列冲突少), 但是低负载意味着空间利用率低, 空间利用不充分, 很多桶都没有元素
     */
    static class HashMapLoadFactor {
        public static void testGet(Map<String, String> filledMap, int n) {
            for(int tc = 0; tc < 1_000_000; tc++) {
                for(int i = 0; i < AbstractCollection1.Countries.DATA.length; i++) {
                    String key = AbstractCollection1.Countries.DATA[i][0];
                    filledMap.get(key);
                }
            }
        }

        public static void main(String[] args) {
            HashMap<String, String> map1 = new HashMap<>();
            // 负载因子=11/16 = 0.69
            map1.putAll(AbstractCollection1.Countries.capitals(11));

            // 将容量提升到32, 避免了再散列
            HashMap<String, String> map2 = new HashMap<>(32);
            // 这个时候负载因子 = 11/32 = 0.34
            map2.putAll(map1);

            // 计算需要时间
            long t1 = System.currentTimeMillis();
            testGet(map1, 11);
            long t2 = System.currentTimeMillis();
            System.out.println("map1 : " + (t2 - t1));

            t1 = System.currentTimeMillis();
            testGet(map2, 11);
            t2 = System.currentTimeMillis();
            System.out.println("map2 : " + (t2 - t1));
        }
    }
}
