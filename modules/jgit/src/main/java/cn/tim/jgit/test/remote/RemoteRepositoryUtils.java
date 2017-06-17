package cn.tim.jgit.test.remote;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.*;

import java.nio.file.Path;

/**
 * Created by LuoLiBing on 15/7/9.
 */
public class RemoteRepositoryUtils {

    public static void cloneFromRemote(String remoteURL, Path localPath) throws GitAPIException {
        Git remoteGit = Git.cloneRepository()
                .setURI(remoteURL)
                .setDirectory(localPath.toFile())
                .call();
        remoteGit.close();
    }

    public static void commit(Git git, String message) throws GitAPIException {
        git.commit().setMessage(message).call();
    }


    public static void push(Git git) throws GitAPIException {
        git.push().call();
        git.close();
    }
}
