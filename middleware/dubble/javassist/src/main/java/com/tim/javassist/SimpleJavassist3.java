package com.tim.javassist;

import javassist.bytecode.AccessFlag;
import javassist.bytecode.ClassFile;
import javassist.bytecode.DuplicateMemberException;
import javassist.bytecode.FieldInfo;
import org.junit.Test;

import java.io.*;

/**
 * Created by luolibing on 2019/4/2.
 */
public class SimpleJavassist3 {

    @Test
    public void obtaining() throws IOException, DuplicateMemberException {
        BufferedInputStream fin = new BufferedInputStream(new FileInputStream("/Users/luolibing/Documents/github/coding-life/middleware/dubble/javassist/target/classes/com/tim/javassist/simple1/B.class"));
        ClassFile cf = new ClassFile(new DataInputStream(fin));
        FieldInfo fieldInfo = new FieldInfo(cf.getConstPool(), "width", "I");
        fieldInfo.setAccessFlags(AccessFlag.PUBLIC);
        cf.addField(fieldInfo);

        // 操作字节码级别的文件
        cf.write(new DataOutputStream(new FileOutputStream("/Users/luolibing/Documents/github/coding-life/middleware/dubble/javassist/target/classes/com/tim/javassist/simple1/B.class")));
    }
}
