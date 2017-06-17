package cn.design.patterns.composite.iterator;

/**
 * Created by LuoLiBing on 16/6/18.
 */
public class DinerMenu {

    final static int MAX_ITEMS = 6;
    int numberOfItems = 0;
    MenuItem[] menuItems;

    public DinerMenu() {
        menuItems = new MenuItem[MAX_ITEMS];
        addItem("面包", "好吃", true, 111);
        addItem("培根", "好吃的肉", false, 222);
        addItem("肉", "猪肉", false, 333);
    }

    public void addItem(String name, String description,
                        boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        if(numberOfItems >= MAX_ITEMS) {
            System.out.println("menu is full");
        } else {
            menuItems[numberOfItems++] = menuItem;
        }
    }

    public MenuItem[] getMenuItems() {
        return menuItems;
    }
}
