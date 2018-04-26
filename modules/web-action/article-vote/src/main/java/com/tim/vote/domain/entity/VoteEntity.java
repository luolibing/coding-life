package com.tim.vote.domain.entity;

import lombok.Data;

/**
 * Created by luolibing on 2018/4/24.
 */
@Data
public class VoteEntity {

    private Long articleId;

    private String voter;
}
