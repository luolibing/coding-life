package test;

import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LuoLiBing on 15/7/20.
 */
public class RuntimeExecTest {


    @Test
    public void testExec() throws IOException {
        Process process = Runtime.getRuntime().exec(new String[]{"ffmpeg", "-i", "/Users/tim/Downloads/马达加斯加动物园的一天-quicktime.m4v"});

        InputStream inputStream = process.getInputStream();
        byte[] buffers = new byte[inputStream.available()];
        inputStream.read(buffers);
        Files.write(buffers, new File(("log2.txt")));


        InputStream errorStream = process.getErrorStream();
        byte[] errorBuffers = new byte[errorStream.available()];
        errorStream.read(errorBuffers);
        Files.write(errorBuffers, new File("error.log"));
    }

}
