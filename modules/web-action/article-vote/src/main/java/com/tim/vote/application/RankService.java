package com.tim.vote.application;

import com.tim.vote.domain.entity.ArticleEntity;
import com.tim.vote.infrastructure.article.ArticleRedisRepository;
import com.tim.vote.infrastructure.constant.RedisKeyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by luolibing on 2018/5/3.
 */
@Service
public class RankService {

    @Autowired
    private ArticleRedisRepository articleRedisRepository;


    public Page<ArticleEntity> findArticle(Pageable pageable) {
        return articleRedisRepository.findArticlePage(RedisKeyEnum.ARTICLE_SCORE.name(), pageable);
    }
}
