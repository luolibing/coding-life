//package cn.tim.reback.resource.utils
//
//import org.apache.commons.io.FilenameUtils
//
//import java.nio.file.Files
//import java.nio.file.Path
//import java.nio.file.Paths
//
///**
// * Created by LuoLiBing on 16/3/15.
// */
//class FileUtils {
//
//    public static boolean copyFile(Path sourcePath, Path targetPath, String type, String checksum) throws IOException {
//
//        // 源文件不存在返回空
//        if(Files.notExists(sourcePath)) {
//            return false;
//        }
//
//        if(type.equals("image")) {
//            use(FilenameUtils) {
//                targetPath = Paths.get(targetPath.getParent().toString(), targetPath.toString().baseName + "_md5:" + checksum + "." + targetPath.toString().extension);
//            }
//        }
//
//        // 目标文件不存在,则移动文件
//        if(Files.notExists(targetPath)) {
//            if(Files.notExists(targetPath.getParent())) {
//                targetPath.getParent().toFile().mkdirs();
//            }
//            Files.move(sourcePath, targetPath);
//            System.out.println(targetPath.toString());
//            return true;
//        } else {
//            return false;
//        }
//
//    }
//
//}