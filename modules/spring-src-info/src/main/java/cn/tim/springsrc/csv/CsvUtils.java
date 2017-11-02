package cn.tim.springsrc.csv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * User: luolibing
 * Date: 2017/10/24 14:13
 */
public class CsvUtils {

    private String title = "列1,列2,列3,列4\n";

    public void writeCsv() throws IOException {
        File file = new File("D://luolibing.csv");
        if(file.exists()) {
            file.delete();
        }


        byte[] bom ={(byte) 0xEF,(byte) 0xBB,(byte) 0xBF};

        file.createNewFile();
        StringBuilder builder = new StringBuilder();
        builder.append(title);
        for(int i = 0; i < 10; i++) {
            builder.append(convert("\"1")).append(",");
            builder.append(toStringNullToEmpty("44142419920818585")).append(",");
            builder.append(toStringNullToEmpty(4.142419920818585f)).append(",");
            builder.append(convert("0.1元/条."));
            builder.append(convert("翁玥佳宋垚余燚\n"));
            builder.append("\n");
        }

        FileOutputStream fs = new FileOutputStream(file, true);
        FileChannel channel = fs.getChannel();
        channel.write(ByteBuffer.wrap(bom));
        channel.write(ByteBuffer.wrap(builder.toString().getBytes("UTF-8")));
        channel.close();
    }

    private String convert(String str) {
        if(str == null) {
            return "";
        }
        return "\"" + str.replace("\"", "\"\"") + "\"";
    }

    private String toStringNullToEmpty(Object object) {
        return object == null ? "" :
                "\"\t" + String.valueOf(object).replace("\"", "\"\"") + "\"";
    }

    public static void main(String[] args) throws IOException {
        new CsvUtils().writeCsv();
    }
}
