package cn.tim.webhook.webhook;

/**
 * User: luolibing
 * Date: 2017/5/18 12:51
 */
public interface WebHook<T, R> {
    R hook(T t);
}
