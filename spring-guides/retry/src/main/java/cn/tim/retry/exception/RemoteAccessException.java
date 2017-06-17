package cn.tim.retry.exception;

/**
 * Created by LuoLiBing on 16/4/25.
 */
public class RemoteAccessException extends Exception {

    public RemoteAccessException() {
    }

    public RemoteAccessException(String message) {
        super(message);
    }
}
