package cn.tim.thinking.concurrent21.thread;

/**
 * Created by LuoLiBing on 16/10/14.
 * 让步
 * 如果知道已经完成了在run()方法的循环的一次迭代过程中所需的工作,就可以给调度器一个暗示:我的工作已经做得差不多了,可以让别的线程使用CPU了.
 * 这个方法可以使用yield()方法来做出(不过这只是一个暗示,没有任何机制保证它一定被采纳),调用时,也是在建议具有相同优先级的其他线程可以运行.对于任何重要的控制或在调整应用时,都不能依赖yield()
 */
public class YieldDemo {
}
