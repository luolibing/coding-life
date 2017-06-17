package cn.tim.springsrc.ioc;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.SimpleAutowireCandidateResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luolibing on 2017/3/26.
 */
public class Test1 {

    /**
     * 使用最简单的XmlBeanFactory加载
     * XmlBeanFactory继承自DefaultListableBeanFactory，在此基础上添加了自己的XmlBeanDefinitionReader解析xml
     *
     * DefaultListableBeanFactory类图
     * AliasRegistry                    别名的增删改查注册操作
     * BeanDefinitionRegistry           在AliaRegistry的基础上添加定义对BeanDefinition的增删改注册操作
     *
     * ConfigurableListableBeanFactory  BeanFactory配置清单，提供忽略类型和接口的方法。冻结配置等等功能
     * ListableBeanFactory              扩展了BeanFactory，提供枚举所有实例的功能，而不是客户端一个一个尝试查找加载。
     * AutowireCapableBeanFactory       提供创建Bean,初始化，自动注入等功能。 注入的级别分为：不注入，按名字注入，按类型注入，注入到构造函数中。
     * ConfigurableBeanFactory          提供配置的Factory的各种方法。 这个接口实现是为了允许框架内部插件和特殊BeanFactory配置方法。 Scope(Singleton, prototype)
     * HierarchicalBeanFactory          在BeanFactory的基础上提供了层次结构，父BeanFactory的支持.
     *
     * BeanFactory                      定义获取Bean和Bean的各种属性
     * SingletonBeanRegistry            定义对单例Bean的定义和获取
     * AbstractAutowireCapableBeanFactory 提供了对象创建（包括构造函数创建），属性注入，Bean注入，初始化，处理运行时对象引用，解析管理集合，调用初始化方法。 支持注入构造函数，根据名称或者属性进行注入。不承担对象注册相关的工作。
     * DefaultSingletonBeanRegistry     对BeanRegister的默认实现。
     * SimpleAliasRegistry              对alias进行缓存cache
     *
     */
    @Test
    public void simpleClassLoad() {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("sample1/mypojo.xml"));
        List<String> list = new ArrayList<>(Arrays.asList("luolibing", "liuxiaoling", "luominghao"));
//        beanFactory.ignoreDependencyType(MyPojo.class);

        // 使用SingletonBeanRegistry注册管理单例Bean
        beanFactory.registerSingleton("list", list);
        BaseInterface bean = beanFactory.getBean(BaseInterface.class);
        System.out.println(bean.getName());

        List list1 = beanFactory.getBean("list", List.class);
        System.out.println(list1);
    }


    /**
     * XmlBeanDefinitionReader定义了资源配置文件的读取、解析以及注册
     * XmlBeanDefinitionReader大致包含以下几个元素：
     *
     * ResourceLoader           定义资源加载器，用于根据配置文件获取到对应的Resource对象。
     * BeanDefinitionReader     定义了读取资源文件或者Resource,并转换成BeanDefinition的过程。 loadBeanDefinitions(Resource); loadBeanDefinitions(location);
     * EnvironmentCapable       环境能力，applicationContext上下文都具有环境能力，
     * DocumentLoader           加载xml文件进行解析，成为Document对象。
     *
     */
    @Test
    public void reader() {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("sample1/mypojo.xml"));
    }

    /**
     * XmlBeanFactory启动的过程：
     * 1 XmlBeanFactory初始化需要定义XmlBeanDefinitionReader用来做xml解析读取，将配置文件作为Resource作为资源文件进行加载导入。
     *   忽略指定接口的自动注入autowired, BeanNameAware, BeanFactoryAware, BeanClassLoaderAware, 并且设置一个父BeanFactory.
     *   忽略制定接口的实现原理是因为BeanNameAware，带Aware的接口会有一个注入方法，当需要的时候会调用这些方法，然后传入需要的对象实例，所以这些对象不再需要autowired.
     *
     * 2 使用XmlBeanDefinitionReader.loadBeanDefinitions(resource); 同时维护一个正在被加载的resourcesCurrentlyBeingLoaded集合。
     *   读取对应的配置文件， 首先用DocumentLoader.doLoadDocument()读取配置，生成一个Document. 接下来就是解析RootElement读取xml文件结构了。
     *   preProcessXml(root); parseBeanDefinitions(root, this.delegate); postProcessXml(root); 根据根节点
     */
    @Test
    public void startWithXmlBeanFactory() {
        // 1 new XmlBeanFactory，做自身的设置，例如设置忽略自动注入的类型

        // 2 将配置文件传入，封装成Resource，使用XmlBeanDefinitionReader.loadBeanDefinitions(resource)。
        new XmlBeanDefinitionReader(null).loadBeanDefinitions(new ClassPathResource(""));

        // 3 使用XmlBeanDefinitionReader将Resource解析成Document(dom技术)
        // Document document = new XmlBeanDefinitionReader(null).doLoadDocument()

        // 4 委托给DefaultBeanDefinitionDocumentReader进行Document的解析读取，先获取到Root根节点， 然后逐个获取到对应的Node，进行解析封装成BeanDefinitionHolder

        // 5 使用Register.registerBeanDefinition()进行Bean的注册，将BeanDefinition保存到对应的Map当中。 BeanDefinition封装了Bean的所有描述，源信息等。
    }


    /**
     * 获取Bean的过程
     * 先在当前的BeanFactory中查找， 如果没有找到委托给父工厂查找， 如果还没有找到则抛出异常。
     */
    @Test
    public void getBean() {
        // 一 先在自己的BeanFactory查找对应的NamedBeanHolder<T>, 此对象封装了BeanName，和Bean实例。
        // NamedBeanHolder<T> namedBean = resolveNamedBean(requiredType, args);
        // 查找NamedBeanHolder又分成以下几步：
        // 1.1 查找候选Bean名单列表，例如同一个接口有多个实现。
        // 1.2 因为有多个候选Bean名单，所以需要选择一个其中一个，选择策略是优先使用primary配置，接着使用@Ordered优先级配置，如果没有这些配置，则抛出异常。
        // 1.3 根据所选择的候选Bean，获取到对应的实例对象。
        /**
         * 1.3.1 获取的先后顺序是：
         *      1 判断手工创建Singleton是否存在对应缓存
         *      2 BeanFactory中是否存在对应的Bean
         *      3 以上都没找到则进入创建流程，创建之前先保证它所依赖的对象都创建好。 这个地方有可能抛出循环依赖的异常
         *      4 判断需要的BeanScope的范围
         *      5 singleton类型，则判断BeanRegistry中是否已经保存有这个单例，如果存在则返回。不存在则进行创建，走创建流程。
         *          创建的时候根据RootBeanDefinition创建对象，并且会查看是否有BeanPostProcessor决定是返回一个代理对象，还是一个对象本身。
         *          创建对象的时候使用spring封装好的BeanUtils使用Constructor进行创建实例对象。 创建完对象之后再设置对象的属性列表值。
         *      6 prototype类型，直接创建对象。
         */


        // 二 如果没找到，则查找其父工厂，如果还没找到则抛出异常
        // BeanFactory parent = getParentBeanFactory();
    }


    /** 此factory可选的serializationId，此Id用于序列化 */
    private String serializationId;

    /** 是否允许不同类型的Bean注册相同的名字 */
    private boolean allowBeanDefinitionOverriding = true;

    /** 是否允许标注为lazy-init的类也能进行类加载 */
    private boolean allowEagerClassLoading = true;

    /** 对于数组或者集合依赖顺序比较器 */
    private Comparator<Object> dependencyComparator;

    /** 解析器用于检查一个Bean定义是自动注入的候选 */
    private AutowireCandidateResolver autowireCandidateResolver = new SimpleAutowireCandidateResolver();

    /** 从依赖类型映射到对应的注入，是否生成的Bean对象都可以丢这个里面 */
    private final Map<Class<?>, Object> resolvableDependencies = new ConcurrentHashMap<Class<?>, Object>(16);

    /** 存储Bean定义对象，名字为定义的名称 */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(256);

    /** 存储单例和非单例Bean名称，key为他们的Type */
    private final Map<Class<?>, String[]> allBeanNamesByType = new ConcurrentHashMap<Class<?>, String[]>(64);

    /** 存储单例Bean名称，Key为他们的Type*/
    private final Map<Class<?>, String[]> singletonBeanNamesByType = new ConcurrentHashMap<Class<?>, String[]>(64);

    /** 所有的Bean定义，顺序为他们的注册顺序*/
    private volatile List<String> beanDefinitionNames = new ArrayList<String>(256);

    /** 手动注册的单例Bean名称Set,注册顺序 */
    private volatile Set<String> manualSingletonNames = new LinkedHashSet<String>(16);

    /** 在冻结配置的情况下，缓存Bean数组的名称 */
    private volatile String[] frozenBeanDefinitionNames;

    /** 是否可以为所有Bean缓存Bean的元数据 */
    private volatile boolean configurationFrozen = false;
}
