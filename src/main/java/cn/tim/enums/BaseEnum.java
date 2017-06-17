package cn.tim.enums;

import javax.naming.OperationNotSupportedException;

/**
 * Created by LuoLiBing on 16/9/5.
 */
public interface BaseEnum {
    static String getName(int id) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }
}
