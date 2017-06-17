package cn.design.patterns.facade;

/**
 * Created by LuoLiBing on 16/6/10.
 * 外观模式:
 * 1 让接口更简单,简化了接口,也将客户从组建的子系统中解耦
 * 2 使用子系统中的组件来完成一系列任务,外观类起一个简化作用
 * 3 外观模式允许我们让客户和子系统之间避免紧耦合
 *
 * 外观模式定义:
 * 提供了一个统一的接口,用来访问子系统中一群接口.外观定义了一个高层接口,让子系统更容易使用.
 *
 * 最少知识原则
 * 减少对象之间的交互,只留下几个密友,简而言之:只和你的密友谈话.
 * 不要让太多的类耦合在一起,免得修改系统中一部分,会影响到其他部分.只需要修改变化的一部分即可
 *
 * 最少知识原则的方法,我们只应该调用属于以下范围的方法:
 * 1 该对象本身
 * 2 被当做方法的参数而传递进来的对象
 * 3 此方法所创建或实例化的任何对象,该方法中new出来的对象或者创建出来的对象
 * 4 对象的任何组件 Has-A
 *
 * 不推荐的方式: 与多个对象交互
 * public float getTemp() {
 *     Thermometer thermometer = station.getThermometer();
 *     return thermometer.getTemperature();
 * }
 *
 * 推荐方式: 只与一个对象交互
 * public float getTemp() {
 *     return station.getTemperature();
 * }
 *
 * 最少知识原则,有一个缺点,会导致更多的包装类被创建出来
 *
 * HomeTheaterTestDrive只有一个朋友HomeTheaterFacade,并不直接调用一些列的组件
 */
public class HomeTheaterTestDrive {

    public static void main(String[] args) {
        HomeTheaterFacade homeTheaterFacade = new HomeTheaterFacade(
                new Amplifier(), new Tuner(),
                new DvdPlayer(), new CdPlayer(),
                new Projector(), new TheaterLights(),
                new Screen(), new PopcornPopper());
        homeTheaterFacade.watchMovie("西游记");
        System.out.println();
        homeTheaterFacade.endMovie();
    }
}
