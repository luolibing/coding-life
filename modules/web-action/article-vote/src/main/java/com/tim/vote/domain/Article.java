package com.tim.vote.domain;

import com.tim.vote.domain.entity.ArticleEntity;
import com.tim.vote.domain.entity.ScoreEntity;
import com.tim.vote.domain.entity.VoteEntity;
import lombok.Data;

import java.util.List;

/**
 * Created by luolibing on 2018/4/24.
 */
@Data
public class Article {

    private ArticleEntity articleInfo;

    private ScoreEntity score;

    private List<VoteEntity> voteList;
}
