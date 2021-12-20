package com.tim.java.agent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.ProtectionDomain;

/**
 * desc: 动态转换class类
 *
 * @author Kroos.luo
 * @since 2020-04-28 10:56
 */
public class Transformer implements ClassFileTransformer {

    public static final String classNumberReturns2 = "TransClass.class.1.class";

    public static byte[] getBytesFromFile(String fileName) {
        try {
            Path classFilePath = Paths.get("/Users/anker/Documents/codeRep/coding-life/modules/java-agent/versions", fileName);
            System.out.println("load classFilePath = " + classFilePath.toString());
            // precondition
            File file = classFilePath.toFile();
            InputStream is = new FileInputStream(file);
            long length = file.length();
            byte[] bytes = new byte[(int) length];

            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset <bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                throw new IOException("Could not completely read file "
                        + file.getName());
            }
            is.close();
            return bytes;
        } catch (Exception e) {
            System.out.println("error occurs in _ClassTransformer!"
                    + e.getClass().getName());
            return null;
        }
    }

    @Override
    public byte[] transform(ClassLoader l, String className, Class<?> c,
                            ProtectionDomain pd, byte[] b) throws IllegalClassFormatException {
        if (!className.contains("TransClass")) {
            return null;
        }
        System.out.println("load className " + className);
        return getBytesFromFile(classNumberReturns2);

    }
}
