package cn.tim.spring.cache.service;

import cn.tim.spring.cache.entity.BookShelf;
import cn.tim.spring.cache.entity.Library;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.tim.spring.cache.constant.CachingKeys.KEY_BOOK_SHELF_ID;
import static cn.tim.spring.cache.constant.CachingKeys.KEY_BOOK_SHELF_LIST;

/**
 *
 * @CachePut的误区：
 * 1、原以为使用CachePut能够实现将缓存放入到List的效果，但是实验发现，并没有这个功能.
 * User: luolibing
 * Date: 2017/4/21 11:06
 * Email: 397911353@qq.com
 */
@Slf4j
@Service
public class BookShelfService {

    private Library library;

    public BookShelfService() {
        this.library = new Library();
    }


    @Cacheable(value = KEY_BOOK_SHELF_ID, key = "#id")
    public BookShelf detail(Long id) {
        log.info("detail {}", id);
        return library.getShelfById(id);
    }


    @Caching(
            // 添加的时候，顺便将新增的元素缓存起来，前提是，保存的时候需要返回Id
            put = @CachePut(value = KEY_BOOK_SHELF_ID, key = "#result.id"),
            // 对于集合缓存，不能细粒度的直接往list里面put,而是要使用evict，而且还得指定allEntries为true
            evict = @CacheEvict(value = KEY_BOOK_SHELF_LIST, allEntries = true)
    )
    public BookShelf add(BookShelf bookShelf) {
        log.info("add bookShelf {}", bookShelf);
        library.addBookShelf(bookShelf);
        return bookShelf;
    }


    @Cacheable(value = KEY_BOOK_SHELF_LIST)
    public List<BookShelf> list() {
        log.info("list bookShelf");
        return library.getBookShelves();
    }

    @Caching(
            put = {
                    /**
                     * 当使用ConcurrentHashMapCache时，当对象修改时，再次放入到List集合，这个地方默认会直接覆盖掉
                     */
                    @CachePut(value = KEY_BOOK_SHELF_ID, key = "#result.id")
            },
            evict = @CacheEvict(value = KEY_BOOK_SHELF_LIST, allEntries = true)
    )
    public BookShelf update(BookShelf bookShelf) {
        BookShelf result = library.getShelfById(bookShelf.getId());
        if(result == null) {
            return null;
        }
        result.setName(bookShelf.getName());
        log.info("update bookShelf {}", result);
        return result;
    }
}
