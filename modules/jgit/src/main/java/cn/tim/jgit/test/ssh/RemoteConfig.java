package cn.tim.jgit.test.ssh;

import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.URIish;

/**
 * Created by LuoLiBing on 15/7/10.
 */
public class RemoteConfig {

    public void config(String remote) {
        /*
        String trackingBranch = "refs/remotes/" + remote + "/master";
        RefUpdate trackingBranchRefUpdate = db.updateRef(trackingBranch);
        trackingBranchRefUpdate.setNewObjectId(commit1.getId());
        trackingBranchRefUpdate.update();

        URIish uri = new URIish(db2.getDirectory().toURI().toURL());
        remoteConfig.addURI(uri);
        remoteConfig.addFetchRefSpec(new RefSpec("+refs/heads/*:refs/remotes/"
                + remote + "/*"));
        remoteConfig.update(config);
        config.save();


        RevCommit commit2 = git.commit().setMessage("Commit to push").call();

        RefSpec spec = new RefSpec(branch + ":" + branch);
        Iterable resultIterable = git.push().setRemote(remote)
                .setRefSpecs(spec).call();*/
    }

}
