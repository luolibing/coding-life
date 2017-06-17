package cn.tim.spring4.ioc722;

import java.beans.ConstructorProperties;

/**
 * Created by LuoLiBing on 16/5/25.
 */
public class ExampleBean {

    private int years;

    private String ultimateAnswer;

    /**
     * javaBeans 的注解用来标注需要注入的属性的名称
     * @param years
     * @param ultimateAnswer
     */
    @ConstructorProperties({"years", "ultimateAnswer"})
    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }

    public String getUltimateAnswer() {
        return ultimateAnswer;
    }

    public int getYears() {
        return years;
    }
}
