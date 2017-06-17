package cn.tim.tika.exception;

/**
 * Created by LuoLiBing on 15/7/18.
 */
public class NotInstallFfmpegException extends Exception {

    private final static String message = "您未安装ffmpeg，请先安装!";

    public NotInstallFfmpegException() {
        super(message);
    }

    public NotInstallFfmpegException(String message) {
        super(message);
    }

    public NotInstallFfmpegException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotInstallFfmpegException(Throwable cause) {
        super(cause);
    }

    protected NotInstallFfmpegException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
