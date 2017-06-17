package cn.tim.jgit.test.ssh;

import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.transport.*;

/**
 * Created by LuoLiBing on 15/7/9.
 */
public class JschConfig {

    public static void config() {
        JschConfigSessionFactory sessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host hc, Session session) {
                CredentialsProvider provider = new CredentialsProvider() {
                    @Override
                    public boolean isInteractive() {
                        return false;
                    }

                    @Override
                    public boolean supports(CredentialItem... items) {
                        return true;
                    }

                    @Override
                    public boolean get(URIish uri, CredentialItem... items) throws UnsupportedCredentialItem {
                        for (CredentialItem item : items) {
                            ((CredentialItem.StringType) item).setValue("yourpassphrase");
                        }
                        return true;
                    }
                };
                UserInfo userInfo = new CredentialsProviderUserInfo(session, provider);
                session.setUserInfo(userInfo);
            }
        };
        SshSessionFactory.setInstance(sessionFactory);
    }

}
