package cn.tim.thinking.clazz14.transaction;

import java.io.IOException;

/**
 * Created by LuoLiBing on 16/10/8.
 */
public interface Handler {

    void append(String string) throws IOException;

    void commit() throws IOException;
}
