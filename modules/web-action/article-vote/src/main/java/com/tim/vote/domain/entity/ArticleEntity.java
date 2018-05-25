package com.tim.vote.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by luolibing on 2018/4/24.
 */
@Data
@EqualsAndHashCode
public class ArticleEntity implements Serializable {

    private Long id;

    private String title;

    private String link;

    private String poster;

    private Long time;

    private Long votes;

    public enum ArticleFileds {
        id, title, link, poster, time, votes;

        public static Set<ArticleFileds> ALL_FIELD = Stream.of(values()).collect(Collectors.toSet());

        public static Set<ArticleFileds> ALL_INIT_FIELD = Stream.of(values()).filter(f -> f!= votes).collect(Collectors.toSet());
    }
}
