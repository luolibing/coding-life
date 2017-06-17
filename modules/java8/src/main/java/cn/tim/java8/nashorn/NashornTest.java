package cn.tim.java8.nashorn;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by LuoLiBing on 17/2/15.
 */
public class NashornTest {

    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        double i = (Double) engine.eval("function f() { return 10;}; f() + 1");
        System.out.println("Result: " + i);
    }
}
