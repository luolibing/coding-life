package cn.tim.appcds;

/**
 * Created by luolibing on 2017/11/1.
 */
public class AppcdsApplication {

    public static void main(String[] args) {

    }

    /**
     * cds = class data sharing
     * 目标
     * 1 加快app启动时间
     * 2 减少运行时内存footprint
     *
     * cds方式
     * 1 预加载一些类
     * 2 类被转换成jvm内部使用的源数据metadata形式
     * 3 将metadata分成只读RO和只写RW,并且分配到不同的内存区域
     * 4 所有加载的class metadata都被保存到一个文件当中，共享起来
     *
     * cds运行时
     * 1 共享归档在jvm执行时映射到内存空间
     * 2 RO内存页在多个jvm之间共享，RW内存页通过copy-on-write进行共享
     * 3 jvm可以在映射内存中进行查找类，而不需要从jar包中进行查找
     * 4 映射类metadata可以直接被jvm使用，只需要很少的处理
     *
     * 好处
     * 1 减少了类加载的时间与系统启动的时间
     * 2 减少运行时内存通过共享
     *
     */
    public void what() {

    }


    /**
     * jdk8u40之前
     * 只支持核心类库通过bootstrap进行加载
     *
     * jdk8和jdk9之后
     * application class也被支持
     * 所有被类加载器加载的都可以被归档，包括
     * bootstrap class loader
     * platformClassLoader(扩展)
     * appClassLoader
     * user defined class loader自定义
     *
     * 同时
     * 允许归档和共享互联网上的java.lang.String对象
     * 进一步减少存档空间存储的大小
     *
     * 很好的支持云平台
     */
    public void why() {

    }

    /**
     * 如何使用appcds
     * 创建一个class列表，和存档文件在试着启动的时候
     */
    public void howToUse() {

    }
}
