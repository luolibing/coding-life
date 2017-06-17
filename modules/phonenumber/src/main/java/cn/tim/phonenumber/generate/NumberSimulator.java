package cn.tim.phonenumber.generate;

/**
 * Created by LuoLiBing on 16/8/24.
 */
public class NumberSimulator {

    public static void main(String[] args) {
        NumberBookGenerator numberBookGenerator = new NumberBookGenerator();
        numberBookGenerator.initNumberBook();
        numberBookGenerator.generateBook();
    }
}
