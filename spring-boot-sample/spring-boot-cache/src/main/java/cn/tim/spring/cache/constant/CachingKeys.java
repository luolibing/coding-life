package cn.tim.spring.cache.constant;

/**
 * User: luolibing
 * Date: 2017/4/21 11:09
 * Email: 397911353@qq.com
 */
public interface CachingKeys {

    /**
     * 根据id查书架缓存
     */
    String KEY_BOOK_SHELF_ID = "KEY_BOOK_SHELF_ID";

    /**
     * 一个图书馆的所有书架
     */
    String KEY_BOOK_SHELF_LIST = "KEY_BOOK_SHELF_LIST";

    /**
     * 所有缓存
     */
    String[] CACHES = new String[] {KEY_BOOK_SHELF_ID, KEY_BOOK_SHELF_LIST};
}
