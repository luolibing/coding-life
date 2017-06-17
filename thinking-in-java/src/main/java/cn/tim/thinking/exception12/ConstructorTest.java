package cn.tim.thinking.exception12;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by LuoLiBing on 16/9/23.
 */
public class ConstructorTest {

    public class InputFile {
        private BufferedReader in;
        public InputFile(String fileName) throws FileNotFoundException {
            try {
                in = new BufferedReader(new FileReader(fileName));
            } catch (FileNotFoundException e) {
                System.out.println("could not open " + fileName);
                throw e;
            } catch (Exception e) {
                try {
                    in.close();
                } catch (IOException e1) {
                    System.out.println("in.close() unsuccessful!");
                }
                throw e;
            } finally {

            }
        }

        public String getLine() {
            String s;
            try {
                s = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException("readLine failed");
            }
            return s;
        }

        public void dispose() {
            try {
                in.close();
                System.out.println("dispose() successful");
            } catch (IOException e) {
                throw new RuntimeException("in.close failed");
            }
        }
    }

    @Test
    public void test1() {
        InputFile inputFile = null;
        try {
            // 构造器失败的话直接抛出异常, 文件还没打开,不需要清理什么
            inputFile = new InputFile("/share/rms_resource.log");
            for(int i = 0; i < 10; i++) {
                // 读取过程中出现文件,需要关闭打开的文件
                System.out.println(inputFile.getLine());
            }
            inputFile.dispose();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(inputFile != null) {
                inputFile.dispose();
            }
        }
    }

    @Test
    public void test2() {
        try {
            InputFile in = new InputFile("/share/rms_resource.log");
            // 构造成功,需要确保in能够被正常的清理,需要使用一个嵌套try-finally确保资源能够被正常的清理
            try {
                String s;
                while((s = in.getLine()) != null) {
                    System.out.println(s);
                }
            } catch (Exception e) {
                System.out.println("readLine exception!!");
                e.printStackTrace();
            } finally {
                in.dispose();
            }
        } catch (FileNotFoundException e) {
            // 构造函数出现异常不需要清理
            System.out.println("inputFile construction failed");
        }
    }

    static class NeedsCleanup {
        private static long counter = 1;
        private final long id = counter++;
        public void dispose() {
            System.out.println("NeedsCleanup " + id + " disposed");
        }
    }

    class ConstructionException extends Exception {}

    class NeedsCleanup2 extends NeedsCleanup {
        public NeedsCleanup2() throws ConstructionException {}
    }

    @Test
    public void test3() {
        // 对于即使构造函数不抛出异常,但是需要最终进行清理资源的情况可以在创建完对象之后马上进入一个try-finally{}块
        NeedsCleanup nc1 = new NeedsCleanup();
        try {

        } finally {
            nc1.dispose();
        }

        NeedsCleanup nc2 = new NeedsCleanup();
        NeedsCleanup nc3 = new NeedsCleanup();
        try {

        } finally {
            // 反转顺序
            nc3.dispose();
            nc2.dispose();
        }

        // 基本上都是先try产生对象,这个时候不需要进行finally清理,因为对象还没创建好;
        // 创建好对象之后,紧接着又是一个try -finally这个时候需要清理代码,因为对象已经创建好
        try {
            NeedsCleanup2 nc4 = new NeedsCleanup2();
            try {
                NeedsCleanup2 nc5 = new NeedsCleanup2();
                try {
                    //
                } finally {
                    nc5.dispose();
                }
            } catch (ConstructionException e) {
                System.out.println(e);
            } finally {
                nc4.dispose();
            }
        } catch (ConstructionException e) {
            System.out.println(e);
        }
    }

    class NeedsCleanup3 extends NeedsCleanup2 {
        public NeedsCleanup3() throws ConstructionException {
        }
        // 不能try catch父类抛出的异常,因为如果父类不能正常初始化, 对象处于一种错误的状态.
//        public NeedsCleanup3(){
//            try {
//                super();
//            } catch (Exception e) {
//
//            }
//        }
    }
}
