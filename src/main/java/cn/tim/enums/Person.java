package cn.tim.enums;

/**
 * Created by LuoLiBing on 16/9/5.
 */
public enum Person implements BaseEnum {
    MAN(0), WOMAN(1);

    private int code;

    Person(int code) {
        this.code = code;
    }

//    public static String getName(int id) throws OperationNotSupportedException {
//        if(string);
//    }
}
