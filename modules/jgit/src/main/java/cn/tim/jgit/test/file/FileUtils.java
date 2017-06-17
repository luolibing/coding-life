package cn.tim.jgit.test.file;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by LuoLiBing on 15/7/8.
 */
public class FileUtils {

    public static Git addFile(String repPath, File file) throws GitAPIException, IOException {
        Git git = Git.open(Paths.get(repPath).toFile());
        Files.copy(file.toPath(), new File(repPath, file.getName()).toPath());
        git.add().addFilepattern(file.getName()).call();
        return git;
    }


    public static void commit(Git git, String message) throws GitAPIException {
        git.commit().setMessage(message).call();
    }


    public static void deleteFile(File file) {
        if(file.exists())
            file.delete();
    }


    public static void createTempFile(String gitPath) throws IOException {
        Files.createFile(Paths.get(gitPath, System.currentTimeMillis() + ".txt"));
    }

}
