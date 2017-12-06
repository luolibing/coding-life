package cn.tim.thinking.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by LuoLiBing on 17/2/16.
 */
public class MarketingDemo {

    private static final String graphic = "blahblah.txt";

    private static String[] col1 = {
            "Aggregate", "Enable", "Leverage",
            "Facilitate", "Synergize", "Repurpose",
            "Strategize", "Reinvent", "Harness"
    };

    private static String[] col2 = {
            "cross-platform", "best-of-breed", "frictionless",
            "ubiquitons", "extensiable", "compelling",
            "mission-critical", "collaborative", "integrated"
    };

    private static String [] col3 = {
            "methodologies", "infomediaries", "platforms",
            "schemas", "mindshare", "paradigms",
            "functionalities", "web services", "infrastructures"
    };

    private static String newline = System.getProperty("line.separator");

    private static ByteBuffer[] utterBs(int howMany) throws UnsupportedEncodingException {
        List<ByteBuffer> list = new LinkedList<>();
        for(int i = 0; i < howMany; i++) {
            list.add(pickRandom(col1, " "));
            list.add(pickRandom(col2, " "));
            list.add(pickRandom(col3, newline));
        }
        return list.toArray(new ByteBuffer[list.size()]);
    }

    private static Random rand = new Random(47);

    // 随机获取ByteBuffer
    private static ByteBuffer pickRandom(String[] strings, String suffix) throws UnsupportedEncodingException {
        String str = strings[rand.nextInt(strings.length)];
        int len = str.length() + suffix.length();
        ByteBuffer buffer = ByteBuffer.allocate(len);
        buffer.put(str.getBytes("US-ASCII"));
        buffer.put(suffix.getBytes("US-ASCII"));
        buffer.flip();
        return buffer;
    }


    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream(graphic);
        GatheringByteChannel gatheringChannel = fos.getChannel();
        ByteBuffer[] buffers = utterBs(10);

        // gatheringChannel.write(ByteBuffer[]); 可以从多个缓冲器写入数据
        while (gatheringChannel.write(buffers) > 0) {

        }
        System.out.println("Mindshare paradigms synergized to " + graphic);
        fos.close();
    }
}
