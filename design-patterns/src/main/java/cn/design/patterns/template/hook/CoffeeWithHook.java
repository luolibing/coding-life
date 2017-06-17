package cn.design.patterns.template.hook;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by LuoLiBing on 16/6/12.
 */
public class CoffeeWithHook extends CaffeineBeverageWithHook {

    @Override
    void brew() {
        System.out.println("Dripping coffee through filter");
    }

    @Override
    void addCondiments() {
        System.out.println("Adding sugar and milk");
    }

    /**
     * 子类实现钩子方法,改变模板的行为
     * @return
     */
    @Override
    boolean customerWantsCondiments() {
        String answer = getUserInput();
        return (answer.toLowerCase().startsWith("y"));
    }

    private String getUserInput() {
        String answer = null;
        System.out.println("Would you like milk and sugar with your coffee (y/n)? ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            answer = bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(answer == null) {
            return "no";
        }
        return answer;
    }
}
