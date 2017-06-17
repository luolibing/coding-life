package cn.design.patterns.combining;

/**
 * Created by LuoLiBing on 16/6/25.
 * 1 适配模式
 * 鹅适配器,将鹅适配成鸭子
 */
public class GooseAdapter implements Quackable {

    private Goose goose;

    public GooseAdapter(Goose goose) {
        this.goose =goose;
    }

    @Override
    public void quack() {
        goose.honk();
    }

    @Override
    public void registerObserver(Observer observer) {

    }

    @Override
    public void notifyObservers() {

    }
}
