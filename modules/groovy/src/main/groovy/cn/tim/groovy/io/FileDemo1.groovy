package cn.tim.groovy.io
import groovy.io.FileType
import org.apache.commons.io.FilenameUtils
import org.junit.Test

import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

/**
 * Created by LuoLiBing on 15/7/18.
 */
class FileDemo1 implements Serializable {

    private String filePath;
    private String basePath;

    //@Before
    void init() {
        filePath = this.getClass().getClassLoader().getResource("luolibing.log").getPath();
        basePath = this.class.getClassLoader().getResource("").getPath()
    }

    @Test
    void readFile1() {
        new File(filePath).eachLine { line, i ->
            println "Line $i: $line"
        }
    }

    @Test
    void readFile2() {
        def count = 0;
        new File(filePath).withReader { reader ->
            count = reader.readLines().size()
        }
        println "count line is $count"
    }

    @Test
    void collect() {
        def list = new File(filePath).collect()
        println list.size()

        def strings = new File(filePath) as String[]
        println strings.length

        def bytes = new File(filePath).bytes
        println bytes.length
    }

    @Test
    void inputstreamTest() {
        def stream = new File(filePath).newInputStream()
        stream.close()
    }

    @Test
    void writeTest() {
        new File(filePath).withInputStream { stream ->

        }
    }

    @Test
    void writeFile1() {
        new File(filePath).withWriter('utf-8') { writer ->
            writer.writeLine '罗立兵'
            writer.writeLine 'A frog jumps'
            writer.writeLine 'age:27'
        }
    }

    @Test
    void writeFile2() {
        def file = new File(filePath)
        file.withWriterAppend { writer ->
            writer.writeLine '罗立兵' + System.currentTimeMillis()
            writer.writeLine  'A frog jumps'
            writer.writeLine  'age:27'
        }
    }

    @Test
    void writeFile3() {
        new File(filePath) <<
    '''<<test'
    姓名:罗立兵
    age:28
    公司:tim
    '''
    }

    @Test
    void writeFile4() {
        new File(filePath).bytes = "刘晓玲".bytes
    }

    @Test
    void writeFile5() {
        def os = new File(basePath, System.currentTimeMillis() + ".log").newOutputStream()
        os.write "罗立兵".bytes
        os.close()
    }

    @Test
    void writeFile6() {
        new File(basePath, System.currentTimeMillis()+".log").withOutputStream {
            stream ->  stream.write("刘晓玲".bytes)
        }
    }

    @Test
    void listFileTree1() {
        new File(basePath).eachFile { file ->
            println file.name
        }

        new File(basePath).eachFileMatch(~/.*\.log/) { file ->
            println file.name
        }
    }

    @Test
    void listFileTree2() {
        new File(basePath).eachFileRecurse { file ->
            println file.name
        }
    }

    @Test
    void createTempFile1() {
        def file = Files.createTempFile("groovy-", ".log")
        println file.toRealPath().toString()
        println file.fileName.toFile().getPath()
    }

    @Test
    void listFileRecurse2() {
        new File(basePath).eachFileRecurse(FileType.FILES) { file ->
            println file.name
        }
    }

    @Test
    void traverseFile() {
        new File(basePath).traverse { file ->
            if(file.directory && file.name == 'bin'){
                FileVisitResult.TERMINATE
            } else {
                println file.name
                FileVisitResult.CONTINUE
            }
        }
    }

    @Test
    void serialize1() {
        int i = 1
        boolean b = true
        String message = '我是罗立兵'
        Date now = new Date()

        new File(filePath).withDataOutputStream { out ->
            out.writeBoolean(b)
            out.writeInt(i)
            out.writeUTF(message)
        }

        new File(filePath).withDataInputStream { input ->
            assert input.readBoolean() == b
            assert input.readInt() == i
            assert input.readUTF() == message
        }
    }

    @Test
    void serialize2() {
        def p = new FileDemo1(name: 'luolibing', age:27)
        new File(filePath).withObjectOutputStream { out ->
            out.writeObject(p)
        }

        new File(filePath).withObjectInputStream{ input ->
            FileDemo1 p1 = input.readObject()
            assert p1.name == p.name
            assert p1.age == p.age
        }
    }

    @Test
    void shellCmd() {
        def process =  "cmd /c dir".execute()
        println "process is ${process.text}"
    }

    @Test
    void shellCmdList() {
        def process = "cmd /c dir c:".execute()
        process.in.eachLine { line ->
            println line
        }
    }

    @Test
    void baseName() {
        println Paths.get(basePath).toAbsolutePath().toString()
        println Paths.get(basePath).last().toString()
    }

    String name
    int age

    @Test
    void eachTest1() {
        def list = []
        Paths.get("/share/json/student").eachFileMatch (FileType.FILES, ~/.*\.json/) { f ->
            list << f
            println f.getAbsolutePath()
        }
        println list
    }

    @Test
    void fileMatch() {

    }

    /**
     * 根据MD5获取MD5值段路径
     * @param md5
     * @return
     */
    public String getMd5PathByMd5WithExtension(String md5) {
        char[] chars = md5.toCharArray();
        return chars[0] + chars[1] + "/" + chars[2] + "/" //+ md5;
    }


    @Test
    void testIni() {
        def filename = "aaaa.txt"
        use(FilenameUtils) {
            println  filename.extension
        }
    }

    @Test
    void shellTest() {

        def execute = "ls /share".execute()
        execute.waitFor(5000, TimeUnit.MILLISECONDS)
        println "end"
    }
}
