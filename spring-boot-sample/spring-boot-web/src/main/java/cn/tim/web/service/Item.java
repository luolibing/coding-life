package cn.tim.web.service;

/**
 * User: luolibing
 * Date: 2018/3/15 12:50
 */
public abstract class Item {

    public abstract boolean processItem(Long productId, Integer quantity);
}
