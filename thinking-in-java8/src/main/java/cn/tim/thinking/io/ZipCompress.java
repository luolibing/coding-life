package cn.tim.thinking.io;

import org.junit.Test;

import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

/**
 * Created by LuoLiBing on 16/12/9.
 * 压缩类是基于InputStream和OutputStream体系,因为压缩类库是按字节方式而不是字符方式处理
 *
 * CheckedInputStream       getCheckSum()校验和
 * CheckedOutputStream
 * DeflaterOutputStream     压缩类的基类
 * ZipOutputStream          压缩成Zip
 * GZipOutputStream         压缩成GZip
 * InflaterInputStream      解压缩的基类
 * ZipInputStream           解压缩Zip
 * GZipInputStream          解压缩GZip
 *
 */
public class ZipCompress {


    /**
     * 直接将内容压缩进test.gz文件中
     * @throws IOException
     */
    @Test
    public void gzipCompress() throws IOException {
        // 压缩文件
        BufferedReader reader = new BufferedReader(new FileReader("1.txt"));
        // 使用BufferedOutputStream,GZipOutputStream,FileOutputStream配合使用写入GZip文件
        BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream("test.gz")));
        System.out.println("Writing file");
        int c;
        while ((c = reader.read()) != -1) {
            out.write(c);
        }
        reader.close();
        out.close();

        // 读取压缩文件. 直接读取,使用BufferedReader,InputStreamReader,GZipInputStream,FileInputStream一起配合读取GZip文件
        System.out.println("Reading file");
        BufferedReader reader2 = new BufferedReader(
                new InputStreamReader(
                        new GZIPInputStream(
                                new FileInputStream("test.gz"))));
        String s;
        while ((s = reader2.readLine()) != null) {
            System.out.println(s);
        }
    }


    /**
     * 对于每一个要压缩的文件,都必须调用putNextEntry,将其传递给一个ZipEntry.ZipEntry提供了许多Zip文件的支持,例如名字注释,压缩方法,目录入口等等
     * ZipFile可以快捷的读取到压缩包内的文件信息.
     * GZip和Zip库的使用不仅仅局限于文件,可以压缩任何东西,包括网络上发送的数据.
     * checksum类型  Adler32(更快)和CRC32(更准确)
     * @throws IOException
     */
    @Test
    public void zipCompress() throws IOException {
        // 使用BufferedOutputStream,ZipOutputStream,CheckedOutputStream,FileOutputStream一起提供checksum的压缩功能
        CheckedOutputStream cos = new CheckedOutputStream(
                new FileOutputStream("test.zip"), new Adler32());
        // 写入压缩文件
        ZipOutputStream zos = new ZipOutputStream(cos);
        BufferedOutputStream bos = new BufferedOutputStream(zos);
        // 添加描述 在Zos上添加comment貌似不管用, Zos提供了setComment()但是ZipInputStream却没有提供getComment()方法,所以这个地方用setComment貌似没什么用处
//        zos.setComment("a test of JAVA Zip");
        File f = new File(".");
        File[] files = f.listFiles(File::isFile);
        for(File file: files) {
            System.out.println("Writing file " + file);
            BufferedReader in = new BufferedReader(new FileReader(file));
            // 通过zos.putNextEntry()往压缩文件中添加文件
            ZipEntry entry = new ZipEntry(file.getName());
            entry.setComment("a test of JAVA Zip" + System.currentTimeMillis());
            zos.putNextEntry(entry);
            int c;
            // 而写数据则通过BufferedOutputStream带缓冲的写入
            while ((c = in.read()) != -1) {
                bos.write(c);
            }
            in.close();
            bos.flush();
        }
        bos.close();

        // 从压缩文件读取, 使用BufferedInputStream, ZipInputStream,CheckedInputStream,FileInputStream配合读取压缩文件
        // 读取校验和
        System.out.println("checksum: " + cos.getChecksum().getValue());
        System.out.println("Reading file");
        FileInputStream fi = new FileInputStream("test.zip");
        CheckedInputStream cis = new CheckedInputStream(fi, new Adler32());
        ZipInputStream zis = new ZipInputStream(cis);
        BufferedInputStream bis = new BufferedInputStream(zis);

        ZipEntry entry;
        // ZipInputStream通过getNextEntry获取到ZipEntry,然后再通过bis读取流
        while ((entry = zis.getNextEntry()) != null) {
            System.out.println("Reading file " + entry);
            Path basePath = Paths.get("/share/" + entry.getName());
            OutputStream os = Files.newOutputStream(basePath);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.close();
        }
        System.out.println("checksum: " + cis.getChecksum().getValue());
        bis.close();

        // ZipFile对象读取文件列表
        ZipFile zf = new ZipFile("test.zip");
        zf.stream().forEach(ze -> System.out.println("File: " + ze.getComment()));
    }

    /**
     * Zip格式也被应用于
     * 出于安全考虑jar文件中每个条目都可以加上数字化签名.
     * 一个Jar文件由一组压缩文件构成,同时还有一张描述所有文件的"文件清单"
     * jar指令
     *
     * jar [options] destination [manifest] inputfile(s)
     *
     * options:
     * c 创建
     * t 列出目录表
     * x 解压所有文件
     * x file 解压该文件
     * f 指定一个文件名
     * m 表示第一个参数为自建文件清单的名字
     * v 详细输出
     * O 只存储文件,不压缩文件
     * M 不创建文件清单
     *
     * 常用
     * jar cf my.jar *.class 打包
     * jar cmf my.jar manifest.mf *.class 用户自建清单文件
     * jar tf my.jar 查看jar包的目录
     * jar tvf my.jar 打印详细信息
     * jar cvf my.jar audio classes image 打包多个目录
     *
     */
    @Test
    public void jar() {

    }
}
