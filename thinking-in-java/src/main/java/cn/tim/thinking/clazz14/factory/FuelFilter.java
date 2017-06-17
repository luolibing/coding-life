package cn.tim.thinking.clazz14.factory;

/**
 * Created by LuoLiBing on 16/9/28.
 */
public class FuelFilter extends Filter {

    public static class Factory implements cn.tim.thinking.clazz14.factory.Factory<FuelFilter> {

        @Override
        public FuelFilter create() {
            return new FuelFilter();
        }
    }
}
