package com.tim.springboot.docker.compose.web;

import com.tim.springboot.docker.compose.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Created by luolibing on 2017/8/31.
 */
@RestController
public class SequenceController {

    @Autowired
    private BookService bookService;

    @GetMapping("/nextId/{key}")
    public Object nextId(@PathVariable String key) {
        return Collections.singletonMap("nextId", bookService.nextId(key));
    }
}
