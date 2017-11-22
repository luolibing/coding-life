package cn.tim.g1.intro;

/**
 * Created by luolibing on 2017/11/22.
 */
public class G1Intro {

    /**
     * g1 的目标：高吞吐量和低延迟
     * 吞吐量： 每秒执行事务的数量
     * 停顿：GC停顿
     *
     * 默认的停顿目标是200ms
     * 更高的停顿意味着：高吞吐量，更高的延迟
     * 更低的停顿意味着：低吞吐量，更低的延迟
     *
     */
    public void goal() {

    }

    /**
     * G1 区域内存管理
     * 堆内存被拆分为多个区域
     * 每个域的大小依赖于堆的大小，4GB堆，每个域为2MB
     *
     */
    public void intro() {

    }


    /**
     * 新对象被分配在eden（）区域中
     */
    public void newObj() {

    }

    /**
     * 当一定数量的eden区域被分配之后进行新生代回收
     * 将还存活的对象从eden域中复制到survivorE(S)域中
     * 接下来对象继续呗分配到E域中
     *
     */
    public void youngCollection() {

    }

    /**
     * 如果S域经过多次新生代回收之后还存活，则将其从S域复制到old（）域中
     */
    public void oldCollection() {

    }

    public void concurrently() {
        
    }
}
