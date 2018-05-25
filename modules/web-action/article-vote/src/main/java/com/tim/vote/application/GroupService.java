package com.tim.vote.application;

import com.tim.vote.domain.entity.GroupEntity;
import com.tim.vote.infrastructure.article.ArticleGroupRedisRepository;
import com.tim.vote.infrastructure.article.ArticleRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luolibing on 2018/5/18.
 */
@Service
public class GroupService {

    @Autowired
    private ArticleGroupRedisRepository articleGroupRedisRepository;

    @Autowired
    private ArticleRedisRepository articleRedisRepository;

    public void addGroup(GroupEntity groupEntity) {
        articleGroupRedisRepository.saveGroup(groupEntity);
    }

    public void addArticleToGroup(long groupId, long articleId) {
        double articleScore = articleRedisRepository.getScore(articleId);
        articleGroupRedisRepository.addArticleScoreSet(groupId, articleId, (long) articleScore);
    }
}
