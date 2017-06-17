package cn.design.patterns.composite.composite;

/**
 * Created by LuoLiBing on 16/6/18.
 * 组合模式
 * 允许你讲对象组合成树形结构来表现"整体/部分"层次结构.组合能让客户以一致的方式处理个别对象以及对象组合
 * 组合模式让我们能用树形方式创建对象的结构,树里面包含了组合以及个别的对象
 * 使用组合结构,我们能把相同的操作应用在组合和个别对象上.换句话说,在大多数情况下,我们可以忽略对象组合和个别对象之间的差别.
 *
 * 组合Menu和单元MenuItem都实现了MenuComponent,可以统一处理整体与部分
 *
 * 组合模式:
 * 集合节点和叶子节点都共同实现一个节点接口,所以可以忽略一些组合和个别对象之间的差别
 * 还可以通过根节点root节点,来遍历整棵树,进行递归调用
 * 组合模式通过牺牲单一责任原则换取透明性
 *
 * NullIterator: 空对象模式
 * 空的迭代器,客户不需要再担心返回值是否为null.我们等于是创建了一个迭代器,作用是"没作用"
 *
 * 类应该只有一个改变的理由
 *
 **/
public class WaitressDrive {

    public static void main(String[] args) {
        MenuComponent menu = new Menu("餐厅菜单", "餐厅菜单11111");
        MenuComponent menuItem = new MenuItem("早餐", "星巴克早餐", true, 1111);
        menu.add(menuItem);
        Waitress waitress = new Waitress(menu);
        waitress.printMenu();
        waitress.printVegetarianMenu();
    }
}
