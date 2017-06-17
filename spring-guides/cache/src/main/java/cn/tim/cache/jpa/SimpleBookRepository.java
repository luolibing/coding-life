package cn.tim.cache.jpa;

import cn.tim.cache.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 15/9/30.
 */
@Component
public class SimpleBookRepository implements BookRepository {


    private final static Logger log = LoggerFactory.getLogger(SimpleBookRepository.class);

    CategoryRepository simpleCategoryRepository;

    @Autowired
    public SimpleBookRepository(CategoryRepository simpleCategoryRepository) {
        this.simpleCategoryRepository = simpleCategoryRepository;
    }


    /**
     * books缓存
     * condition 表示执行方法之前是否先到缓存中查询
     * 如果设置为false,则每次都执行该方法,然后将返回结果随后缓存起来,这样相当于只写
     * 如果设置为true,则每次执行方法之前都到缓存中查询
     * @param isbn
     * @return
     */
    @Override
    @Cacheable(value = "books", key = "#isbn", unless = "T(java.lang.System).getProperty('app.mode.read-only', 'false')")
    public Book getByIsbn(String isbn) {
        simulateSlowService();
        Book book = new Book(isbn, "Some book");
        /*for(int i=0; i<10; i++) {
            //book.setCategory(simpleCategoryRepository.getCategoryById("1"));
        }*/
        return book;
    }


    /**
     * 更新books
     * @param isbn
     */
    @CachePut(value = "books", key = "#isbn")
    public Book update(String isbn, String desc) {
        return new Book(isbn, desc);
    }


    /**
     * 清除isbn，不需要有方法实体
     * @param isbn
     */
    @CacheEvict(value = "books", key = "#isbn", allEntries = false)
    public void clear(String isbn) {

    }

    /**
     * 同类方法调用，不会使用缓存
     * @param isbn
     * @return
     */
    public Book test(String isbn) {
        return getByIsbn(isbn);
    }


    private void simulateSlowService() {
        try {
            log.info("simpleBookRepository invoke getByIsbn");
            long time = 5000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
