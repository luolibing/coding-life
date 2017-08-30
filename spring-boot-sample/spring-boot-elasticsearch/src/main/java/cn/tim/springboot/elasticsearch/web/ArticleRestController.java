package cn.tim.springboot.elasticsearch.web;

import cn.tim.springboot.elasticsearch.entity.Article;
import cn.tim.springboot.elasticsearch.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * User: luolibing
 * Date: 2017/8/30 18:34
 */
@RestController
@RequestMapping("/article")
public class ArticleRestController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Article addArticle(@RequestBody Article article) {
        return articleService.save(article);
    }

    @GetMapping
    public Page<Article> findPage(Pageable pageable) {
        return articleService.findPage(pageable);
    }
}
