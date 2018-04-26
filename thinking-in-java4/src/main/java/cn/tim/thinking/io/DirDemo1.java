package cn.tim.thinking.io;

import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by LuoLiBing on 16/12/1.
 * 目录的检查及创建
 */
public class DirDemo1 {

    static class MakeDirectories {
        private static void usage() {
            System.err.println("Usage: MakeDirectories path1 ... \n");
            System.exit(1);
        }

        public static void fileData(File f) {
            System.out.println(
                    // 绝对路径
                    "Absolute path: " + f.getAbsolutePath()
                    + "\n Can read: " + f.canRead()
                    + "\n Can write: " + f.canWrite()
                    + "\n Can execute: " + f.canExecute()
                    + "\n getName: " + f.getName()
                    + "\n getParent: " + f.getParent()
                    + "\n getPath: " + f.getPath()
                    + "\n length: " + f.length()
                    + "\n lastModified: " + f.lastModified()
            );
            if(f.isFile()) {
                System.out.println("It's a file");
            } else if(f.isDirectory()) {
                System.out.println("It's a directory");
            }
        }
    }

    @Test
    public void makeDirectories() {
        File old = new File("/Users/tim/Downloads/unmatch.txt"), rname = new File("/Users/tim/Downloads/bak/unmatch_bak.txt");
        // 文件重命名
        File parent = new File(rname.getParent());
        if(!new File(rname.getParent()).exists()) {
            parent.mkdirs();
        }
        old.renameTo(rname);
        MakeDirectories.fileData(old);
        MakeDirectories.fileData(rname);
    }


    public static final class Directory {

        public static File[] local(File dir, String regex) {
            return dir.listFiles(new FilenameFilter() {

                private Pattern pattern = Pattern.compile(regex);

                @Override
                public boolean accept(File dir, String name) {
                    return pattern.matcher(name).matches();
                }
            });
        }

        public static File[] local(String path, String regex) {
            return local(new File(path), regex);
        }

        // 文件树遍历,元组
        public static class TreeInfo implements Iterable<File> {

            // 文件集合
            public List<File> files = new ArrayList<>();

            // 目录集合
            public List<File> dirs = new ArrayList<>();

            @Override
            public Iterator<File> iterator() {
                return files.iterator();
            }

            public void addAll(TreeInfo other) {
                files.addAll(other.files);
                dirs.addAll(other.dirs);
            }

            @Override
            public String toString() {
                return "dirs: " + Arrays.toString(dirs.toArray()) + ", files: " + Arrays.toString(files.toArray());
            }
        }

        public static TreeInfo walk(String start, String regex, long time) {
            return recurseDirs(new File(start), regex, time);
        }

        public static TreeInfo walk(File start, String regex, long time) {
            return recurseDirs(start, regex, time);
        }

        public static TreeInfo walk(File start, long time) {
            return recurseDirs(start, ".*", time);
        }

        public static TreeInfo walk(String start, long time) {
            return recurseDirs(new File(start), ".*", time);
        }

        // 递归遍历目录
        static TreeInfo recurseDirs(File startDir, String regex, long time) {
            TreeInfo result = new TreeInfo();
            for(File item : startDir.listFiles()) {
                // 如果是目录递归调用添加
                if(item.isDirectory()) {
                    result.dirs.add(item);
                    result.addAll(recurseDirs(item, regex, time));
                } else {
                    if(item.getName().matches(regex) && item.lastModified() >= time) {
                        result.files.add(item);
                    }
                }
            }
            return result;
        }
    }


    static class ProcessFiles {

        // 策略
        public interface Strategy {
            void process(File file);
        }

        private Strategy strategy;

        private String ext;

        public ProcessFiles(Strategy strategy, String ext) {
            this.strategy = strategy;
            this.ext = ext;
        }

        public void start(String startPath) {
            File fileArg = new File(startPath);
            processDirectoryTree(fileArg);
        }

        // 执行目录树
        public void processDirectoryTree(File root) {
            for(File file : Directory.walk(root.getAbsolutePath(), ".*\\." + ext, System.currentTimeMillis() - 2000000000)) {
                strategy.process(file);
            }
        }

        public static void main(String[] args) {
            // 内部类
            new ProcessFiles(System.out::println, "java").start("/Users/tim/Documents/samples-tim/thinking-in-java");
        }
    }
}
