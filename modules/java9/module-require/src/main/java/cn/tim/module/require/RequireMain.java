package cn.tim.module.require;

import cn.tim.module.export.World;

/**
 * 这个例子演示了，module之间如何依赖的问题
 * javac -d ../mods/exports src/main/java/module-info.java src/main/java/cn/tim/module/export/World.java
 * javac --module-path ../mods -d ../mods/requires src/main/java/module-info.java src/main/java/cn/tim/module/require/RequireMain.java
 * jar --create --file=mylib/cn.require@1.0.jar --main-class=cn.tim.module.require.RequireMain --module-version=1.0 -C mods/cn.require .
 * java -p mylib -m cn.require
 *
 * User: luolibing
 * Date: 2017/9/25 9:46
 */
public class RequireMain {

    public static void main(String[] args) {
        System.out.format("Greeting %s!%n", World.name());
    }
}
