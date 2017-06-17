package cn.design.patterns.facade.principle;

/**
 * Created by LuoLiBing on 16/6/10.
 */
public class Key {

    public boolean turns() {
        System.out.println("验证钥匙");
        System.out.println("验证成功,开门!!!");
        return true;
    }
}
