package cn.tim.spring.cache.web;

import cn.tim.spring.cache.entity.BookShelf;
import cn.tim.spring.cache.service.BookShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: luolibing
 * Date: 2017/4/21 12:58
 * Email: 397911353@qq.com
 */
@RequestMapping(value = "/library")
@RestController
public class LibraryController {

    @Autowired
    private BookShelfService bookShelfService;

    @GetMapping(value = "/{id}")
    public BookShelf detail(@PathVariable Long id) {
        return bookShelfService.detail(id);
    }

    @GetMapping(value = "/list")
    public List<BookShelf> list() {
        return bookShelfService.list();
    }

    @PostMapping
    public ResponseEntity add(@RequestBody BookShelf bookShelf) {
        bookShelfService.add(bookShelf);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity update(@RequestBody BookShelf bookShelf) {
        bookShelfService.update(bookShelf);
        return ResponseEntity.ok().build();
    }
}
