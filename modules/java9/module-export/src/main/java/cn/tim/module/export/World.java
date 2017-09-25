package cn.tim.module.export;

import java.util.ArrayList;
import java.util.List;

/**
 * module打包
 * jar --create --file=mylib/cn.export@1.0.jar --module-version=1.0 -C mods/cn.export .
 *
 *
 * User: luolibing
 * Date: 2017/9/22 18:40
 */
public class World {

    public static String name() {
        List<String> list = new ArrayList<>();
        return "Hello world";
    }
}
