package cn.tim.reback
import groovy.io.FileType
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.IOUtils

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
/**
 * Created by LuoLiBing on 16/3/14.
 */
class ReadProduct {

    final static String parent = "/share/resource_reback"

    final static String source = "/share/resource_reback"

    final static String basesource = "/share/jackrabbiterror/jackrabbit0/repository/datastore"

    final static String image1Path = "/share/Users/tim/Desktop/target_image/"

    final static String image2Path = "/share/Users/tim/Desktop/target_image/"

    void copy(Path source) {

        source.toFile().withInputStream { is ->
            def md5 = DigestUtils.md5Hex(is)
            IOUtils.closeQuietly(is)
            def target = Paths.get(parent, md5[0..1], md5[2], md5)
            if(Files.notExists(target.parent)) {
                target.parent.toFile().mkdirs()
            }
            Files.move(source, target)
        }

    }

    void transfer() {
        int i = 0;
        Paths.get(basesource).eachFileRecurse(FileType.FILES) { Path sourcePath ->
            copy(sourcePath)
            println "num=" + i++
        }
    }


    void imageMd5(Path imagePath) {
        imagePath.eachFileRecurse(FileType.FILES) { Path path ->
            path.toFile().withInputStream { is ->
                def md5 = DigestUtils.md5Hex(is)
                IOUtils.closeQuietly(is)
                use(FilenameUtils) {
                    def targetPath = Paths.get(path.parent.toString(), path.toString().baseName + "_md5:" + md5 + "." + path.toString().extension)
                    println targetPath.toString()
                    Files.move(path, targetPath)
                }
            }
        }
    }

    /**
     * 唯一图片处理
     */
    void transferImage1(Path imagePath) {
        imagePath.eachFileRecurse(FileType.FILES) { Path path ->
            use(FilenameUtils) {
                def filename =  path.toString()
                filename = filename.baseName.split("\\|")[0] + "." + filename.extension
                def targetImage = Paths.get(image1Path, "image1", filename)
                if(Files.notExists(targetImage.parent)) {
                    targetImage.parent.toFile().mkdirs()
                }
                if(Files.notExists(targetImage)) {
                    println targetImage.toString()
                    Files.copy(path, targetImage, StandardCopyOption.REPLACE_EXISTING)
                }
            }
        }
    }


    void transferImage2(Path imagePath) {
        imagePath.eachFile(FileType.DIRECTORIES) { Path path ->
            if(Integer.valueOf(path.fileName.toString())< 2015110823) {

            } else {
                // 同一个文件夹下同名文件取最大面积的文件
                Map<String, String> beanMap = new HashMap<>()
                path.eachFile(FileType.FILES) { Path child ->
                    use(FilenameUtils) {
                        String filename = getFilename(child.toString())
                        // 同文件名取最大面积文件
                        if(beanMap.get(filename) != null) {
                            def firstFilename = beanMap.get(filename).toString()
                            Integer firstNum = getNum(firstFilename)
                            Integer secondNum = getNum(child.toString())
                            // 如果第二个比第一个大,则取第二个,否则取第一个
                            String targetFileName = null;
                            String targetPath = null;
                            if(secondNum > firstNum) {
                                targetFileName = getFilename(child.toString())
                                targetPath = child
                            } else {
                                targetFileName = getFilename(beanMap.get(filename))
                                targetPath = beanMap.get(filename)
                            }

                            copyImage("图片", targetFileName, Paths.get(targetPath))
                            copyImage("看图说话图片", targetFileName, Paths.get(targetPath))
                        } else {
                            beanMap.put(filename, child.toString())
                        }

                    }
                }

                beanMap.each { key, val ->
                    def filename = getFilename(val)
                    copyImage("图片", filename, Paths.get(val))
                    copyImage("看图说话图片", filename, Paths.get(val))
                }
                beanMap.clear()
            }


        }
    }

    void transferImage22(Path imagePath) {
        Map<String, Path> beanMap = [:]
        imagePath.eachFile(FileType.FILES) { Path image ->
            String filename = image.fileName.toString()
            def key = getFilename(filename)
            def beanMapImagePath = beanMap.get(key).toString()
            // 已经存在这个文件
            if(beanMap.get(key)) {

                image.withInputStream { is ->
                    def md5 = DigestUtils.md5Hex(is)
                    // 先比较md5,如果md5一样取任意一个都一样
                    Path smallImage = null
                    Path bigImage = null
                    if(md5.equals(getChecksum(beanMapImagePath))) {
                        smallImage = bigImage = image;
                    }

                    else {
                        // md5不一样再比较面积大小,面积大的为大图,面积小的为小图
                        if(getNum(filename) > getNum(beanMapImagePath)){
                            smallImage = beanMap.get(key)
                            bigImage = image
                        } else if(getNum(filename) < getNum(beanMapImagePath)) {
                            smallImage = image
                            bigImage = beanMap.get(key)
                        }
                        // 大小相等 则比较日期,取教后的日期
                        else if(getDate(filename) > getDate(beanMapImagePath)) {
                            smallImage = bigImage = image
                        } else {
                            smallImage = bigImage = beanMap.get(key)
                        }
                    }
                    copyImage("图片", key, smallImage)
                    copyImage("看图说话图片", key, bigImage)
                    IOUtils.closeQuietly(is)
                }


            } else {
                beanMap.put key, image
            }
        }

        beanMap.each { key, val ->
            copyImage("图片", key, val)
            copyImage("看图说话图片", key, val)
        }
    }


    private static void renameImage(Path imagePath) {
        imagePath.eachFileRecurse(FileType.FILES) { Path image ->
            use(FilenameUtils) {
                def name = image.toString().baseName
                if(name.split("\\|").length > 3) {
                    def newname = name.split("\\|")[0] + "-" + name.split("\\|")[5] + "." + image.toString().extension
                    Files.move(image, Paths.get(image.parent.toString(), newname))
                }
            }
        }
    }


    private static String getFilename(String filename) {
        use(FilenameUtils) {
            return filename.baseName.split("\\|")[0] + "." + filename.extension
        }
    }

    private static Integer getNum(String filename) {
        use(FilenameUtils) {
            return filename.baseName.split("\\|")[2].toInteger()
        }
    }

    private static String getChecksum(String filename) {
        use(FilenameUtils) {
            return filename.baseName.split("\\|")[4]
        }
    }

    private static Integer getDate(String filename) {
        use(FilenameUtils) {
            return filename.baseName.split("\\|")[1].toInteger()
        }
    }


    private static void copyImage(String type, String filename, Path source) {
        def targetImage = Paths.get(image2Path, type, filename)
        if(Files.notExists(targetImage.parent)) {
            targetImage.parent.toFile().mkdirs()
        }
        if(Files.notExists(targetImage)) {
            println targetImage.toString()
            Files.copy(source, targetImage, StandardCopyOption.REPLACE_EXISTING)
        }
    }

    public static void copyFile() {
        Paths.get("/Users/tim/Downloads/rms_checkmd5.log.new.new").toFile().eachLine { line ->
            def array = line.split("=")
            if(array.length != 2) {

            } else {
                def md5 = array[0].trim()
                def path = array[1].trim()
                def sourceFile = Paths.get(source, md5[0..1], md5[2], md5)
                if(Files.exists(sourceFile)) {

                    def target = Paths.get(parent, path)
                    if (Files.notExists(target.parent)) {
                        target.parent.toFile().mkdirs()
                    }
                    Files.copy(sourceFile, target, StandardCopyOption.REPLACE_EXISTING)
                }
            }
        }
    }

    public static void main(String[] args) {
        //new ReadProduct().copy(Paths.get("/share/jackrabbiterror/jackrabbit0/repository/datastore/00/00/62/000062a2842a09a3f9005fc1b9fa5637086e4966"))
        // new ReadProduct().transfer()
        // new ReadProduct().imageMd5(Paths.get("/share/target/image"))
        // new ReadProduct().transferImage1(Paths.get("/Users/tim/Desktop/image/target52"))
        // new ReadProduct().transferImage22(Paths.get("/Users/tim/Desktop/image/3"))
//        new ReadProduct().transfer()
//        new ReadProduct().renameImage(Paths.get("/Users/tim/Desktop/image/target52"))
        copyFile()
    }
}
