package cn.tim.atmosphere.entity;

import java.util.Date;

/**
 * Created by LuoLiBing on 16/1/19.
 */
public class ChatMessage {

    private String message;

    private String author;

    public ChatMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public ChatMessage() {
    }

    private long time = new Date().getTime();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
