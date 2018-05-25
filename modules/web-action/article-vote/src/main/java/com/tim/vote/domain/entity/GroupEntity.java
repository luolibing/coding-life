package com.tim.vote.domain.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by luolibing on 2018/5/18.
 */
@Data
public class GroupEntity {

    private Long id;

    private String name;

    private List<ArticleScore> articleScoreList;

    @Data
    public static class ArticleScore {
        private String articleId;

        private Double score;

    }
}
