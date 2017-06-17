package cn.tim.springboot.client;

/**
 * User: luolibing
 * Date: 2017/5/16 18:25
 */
public class NetworkException extends RuntimeException {

    public NetworkException() {
    }

    public NetworkException(String message) {
        super(message);
    }
}
