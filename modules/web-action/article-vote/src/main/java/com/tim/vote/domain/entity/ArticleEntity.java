package com.tim.vote.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by luolibing on 2018/4/24.
 */
@Data
@EqualsAndHashCode
public class ArticleEntity {

    private Long id;

    private String title;

    private String link;

    private String poster;

    private long time;

    private long votes;
}
