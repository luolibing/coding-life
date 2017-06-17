package cn.tim.phonenumber.generate;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by LuoLiBing on 16/8/25.
 */
public class NumberBookWriter {

    public static void writeNumber(int num, FileWriter fileWriter) throws IOException {
        fileWriter.append(num + "\n");
        fileWriter.flush();
    }
}
