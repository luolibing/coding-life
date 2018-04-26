package com.tim.vote.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Created by luolibing on 2018/4/24.
 */
@Getter
@EqualsAndHashCode
public class ArticleEntity {

    private Long id;

    private String title;

    private String link;

    private String poster;

    private long time;

    private Long votes;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setVotes(long votes) {
        if(this.votes != null) {
            throw new RuntimeException("can not set vote");
        }
        this.votes = votes;
    }
}
