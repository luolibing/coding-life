package com.tim.vote.domain.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by luolibing on 2018/4/24.
 */
@EqualsAndHashCode
public class ArticleEntity implements Serializable {

    private Long id;

    private String title;

    private String link;

    private String poster;

    private long time;

    private Long votes;

    public enum ArticleFileds {
        id, title, link, poster, time, votes;

        public static Set<ArticleFileds> ALL_FIELD = Stream.of(values()).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }
}
