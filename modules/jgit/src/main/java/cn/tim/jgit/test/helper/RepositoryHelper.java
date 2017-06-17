package cn.tim.jgit.test.helper;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by LuoLiBing on 15/7/8.
 */
public class RepositoryHelper {


    public static Repository createNewTempRepository() throws IOException {
        // 创建出一个临时文件，获取临时目录
        File tempPath = File.createTempFile("gitTestRepository","");
        tempPath.delete();

        Repository repository = FileRepositoryBuilder.create(Paths.get(tempPath.getPath(), ".git").toFile());
        repository.create();
        return repository;
    }


    public static Repository createNewRepository(String path) throws IOException {
        Repository repository = FileRepositoryBuilder.create(Paths.get(path, ".git").toFile());
        repository.create();
        return repository;
    }


    public static Repository getDefaultRepository(String path) throws IOException {

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        return builder.setGitDir(Paths.get(path).toFile())
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
    }

}
