package cn.tim.spring.cache.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: luolibing
 * Date: 2017/4/21 11:12
 * Email: 397911353@qq.com
 */
@Data
public class Library implements Serializable {

    private Map<Long,BookShelf> bookShelvesMap;

    private List<BookShelf> bookShelves;

    public Library() {
        bookShelvesMap = new HashMap<>();
        bookShelves = new ArrayList<>();
    }

    public void addBookShelf(BookShelf bookShelf) {
        bookShelvesMap.put(bookShelf.getId(), bookShelf);
        bookShelves.add(bookShelf);
    }

    public BookShelf removeBookShelfById(Long id) {
        BookShelf bookShelf = bookShelvesMap.remove(id);
        bookShelves.remove(bookShelf);
        return bookShelf;
    }

    public BookShelf getShelfById(Long id) {
        return bookShelvesMap.get(id);
    }
}
