package cn.tim.thinking.clazz14.factory;

/**
 * Created by LuoLiBing on 16/9/28.
 */
public class AirFilter extends Filter {

    public static class Factory implements cn.tim.thinking.clazz14.factory.Factory<AirFilter> {

        @Override
        public AirFilter create() {
            return new AirFilter();
        }
    }
}
