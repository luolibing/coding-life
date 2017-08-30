package cn.tim.springboot.elasticsearch.service;

import cn.tim.springboot.elasticsearch.entity.Article;
import cn.tim.springboot.elasticsearch.entity.es.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * User: luolibing
 * Date: 2017/8/30 18:31
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article save(Article article) {
        return articleRepository.save(article);
    }

    public Page<Article> findPage(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
