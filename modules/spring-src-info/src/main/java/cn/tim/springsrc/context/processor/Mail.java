package cn.tim.springsrc.context.processor;

import java.util.Arrays;

/**
 * Created by luolibing on 2017/6/1.
 */
public class Mail {

    private String message;

    private String password;

    private String credit;

    private String key;

    private String[] recipients;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getRecipients() {
        return recipients;
    }

    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "message='" + message + '\'' +
                ", password='" + password + '\'' +
                ", credit='" + credit + '\'' +
                ", key='" + key + '\'' +
                ", recipients=" + Arrays.toString(recipients) +
                '}';
    }
}
