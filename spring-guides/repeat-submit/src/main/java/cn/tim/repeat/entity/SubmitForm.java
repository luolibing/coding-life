package cn.tim.repeat.entity;

/**
 * Created by LuoLiBing on 16/5/26.
 */
public class SubmitForm extends BaseForm {
    private String name;
    private String emailAddress;
    private int confirmationNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(int confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }
}
