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
     * https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html#app_class_data_sharing
     *
     * 1 创建一个共享类的列表
     *   java -Xshare:off -XX:+UnlockCommercialFeatures -XX:DumpLoadedClassList=hello.classlist -XX:+UseAppCDS -cp jcmd-1.0-SNAPSHOT.jar
     *
     * 2 创建一个共享归档
     *   java -XX:+UnlockCommercialFeatures -Xshare:dump -XX:+UseAppCDS -XX:SharedArchiveFile=hello.jsa -XX:SharedClassListFile=hello.classlist -cp -jar jcmd-1.0-SNAPSHOT.jar
     *
     * 3 使用共享归档进行启动
     *   java -XX:+UnlockCommercialFeatures -Xshare:on -XX:+UseAppCDS -XX:SharedArchiveFile=hello.jsa -cp -jar jcmd-1.0-SNAPSHOT.jar
     *
     * 4 验证类加载时从共享归档中进行的
     *   java -XX:+UnlockCommercialFeatures -Xshare:on -XX:+UseAppCDS -XX:SharedArchiveFile=hello.jsa -cp jcmd-1.0-SNAPSHOT.jar -verbose:class java.util.regex.Matcher
     *   结果显示: Loaded java.lang.Cloneable from shared objects file
     *
     */
    public void howToUse() {

    }
}
