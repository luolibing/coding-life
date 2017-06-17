package cn.tim.spring4.ioc722;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by LuoLiBing on 16/5/27.
 */
public class CollectionsTest {

    private Properties adminEmails;

    private List<String> names;

    private Map<String, Float> accounts;

    private Map beanMap;

    public void setAdminEmails(Properties adminEmails) {
        this.adminEmails = adminEmails;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public void setBeanMap(Map beanMap) {
        this.beanMap = beanMap;
    }

    public void setAccounts(Map<String, Float> accounts) {
        this.accounts = accounts;
    }

    public void execute() {
        for(Map.Entry<Object, Object> entry: adminEmails.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        for(String name: names) {
            System.out.println("name:" + name);
        }

        accounts.forEach((key, val) -> {
            System.out.println( key + ":" + val);
        });
    }
}
