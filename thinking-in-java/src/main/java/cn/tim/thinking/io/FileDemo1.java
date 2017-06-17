package cn.tim.thinking.io;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by LuoLiBing on 16/11/22.
 */
public class FileDemo1 {

    // 目录列表器
    @Test
    public void list() {
        File path = new File(".");
        // 获取文件夹下的子文件
        String[] list = path.list();
        // 排序
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for(String f : list) {
            System.out.println(f);
        }
    }

    @Test
    public void filterList() {
        File path = new File(".");
        // 使用FilenameFilter,使用的策略模式
        String[] list = path.list((dir, name) -> name.endsWith(".pdf"));
        for(String f : list) {
            System.out.println(f);
        }
    }

    @Test
    public void filterListInner() {
        FilenameFilter filter = filter("[a-z]*");
        String[] list = new File("/").list(filter);
        for(String f : list) {
            System.out.println(f);
        }
    }

    static FilenameFilter filter(final String regex) {
        return new FilenameFilter() {

            private Pattern pattern = Pattern.compile(regex);

            @Override
            public boolean accept(File dir, String name) {
                return pattern.matcher(name).matches();
            }
        };
    }

    @Test
    public void exercise1() {
        String keyword = "String";
        String[] list = new File("/Users/tim/Downloads").list((dir, name) -> {
            if(!name.endsWith(".java")) {
                return false;
            }
            try {
                Path path = Paths.get(dir.getPath(), name);
                if(Files.isDirectory(path)) {
                    return false;
                }
                String s = new String(Files.readAllBytes(Paths.get(dir.getPath(), name)));
                return s.contains(keyword);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        });
        for(String f : list ) {
            System.out.println(f);
        }
    }

    @Test
    public void exercise2() {
        class SortedDirList {
            private File path;
            public SortedDirList() {
                path = new File("/");
            }
            public SortedDirList(File path) {
                this.path = path;
            }

            public String[] list() {
                String[] list = path.list();
                Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
                return list;
            }

            public String[] list(final String regex) {
                String[] list = path.list(new FilenameFilter() {

                    private Pattern pattern = Pattern.compile(regex);

                    @Override
                    public boolean accept(File dir, String name) {
                        return pattern.matcher(name).matches();
                    }
                });
                Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
                return list;
            }
        }
        SortedDirList dir = new SortedDirList();
        System.out.println(Arrays.toString(dir.list("E0[12]_.*\\.java")));
    }

    @Test
    public void exercise3() {
        File path = new File("/Users/tim/Downloads");
        String[] list = path.list((dir, name) -> name.endsWith(".java"));
        long total = 0;
        for(String f : list) {
            File file = new File("/Users/tim/Downloads", f);
            total += file.length();
        }
        System.out.println(list.length + " file(s), " + total + " bytes");
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

        public static TreeInfo walk(String start, String regex) {
            return recurseDirs(new File(start), regex);
        }

        public static TreeInfo walk(File start, String regex) {
            return recurseDirs(start, regex);
        }

        public static TreeInfo walk(File start) {
            return recurseDirs(start, ".*");
        }

        public static TreeInfo walk(String start) {
            return recurseDirs(new File(start), ".*");
        }

        // 递归遍历目录
        static TreeInfo recurseDirs(File startDir, String regex) {
            TreeInfo result = new TreeInfo();
            for(File item : startDir.listFiles()) {
                // 如果是目录递归调用添加
                if(item.isDirectory()) {
                    result.dirs.add(item);
                    result.addAll(recurseDirs(item, regex));
                } else {
                    if(item.getName().matches(regex)) {
                        result.files.add(item);
                    }
                }
            }
            return result;
        }
    }

    // 遍历文件夹
    @Test
    public void walk() {
        Directory.TreeInfo treeInfo = Directory.walk(".");
        System.out.println("TreeInfo path: ");
        for(File f : treeInfo.dirs) {
            System.out.println(f.getPath());
        }

        System.out.println();
        System.out.println("===================");
        System.out.println("TreeInfo file: ");
        for(File f : treeInfo.files) {
            System.out.println(f.getPath());
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

        public void start(String[] args) {
            try {
                if (args.length == 0) {
                    // 执行目录树
                    processDirectoryTree(new File("."));
                } else {
                    for (String arg : args) {
                        File fileArg = new File(arg);
                        if (fileArg.isDirectory()) {
                            processDirectoryTree(fileArg);
                        } else {
                            if (!arg.endsWith("." + ext)) {
                                arg += "." + ext;
                            }
                            strategy.process(new File(arg).getCanonicalFile());
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // 执行目录树
        public void processDirectoryTree(File root) {
            for(File file : Directory.walk(root.getAbsolutePath(), ".*\\." + ext)) {
                strategy.process(file);
            }
        }

        public static void main(String[] args) {
            // 内部类
            new ProcessFiles(System.out::println, "java").start(new String[]{"FileDemo1"});
        }
    }

    @Test
    public void duration() throws ParseException {

        Date parse = new SimpleDateFormat("HH:mm:ss.SS").parse("00:00:06.24");
        System.out.println(parse.toInstant().toEpochMilli());

        LocalTime lt = LocalTime.parse("00:02:25", DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(lt.getSecond());
    }


    @Test
    public void append() {
        LocalDateTime localDateTime = LocalDateTime.parse("00:00:01.02", DateTimeFormat.forPattern("HH:mm:ss.SS"));
        System.out.println(localDateTime.getMillisOfDay());

    }
}
