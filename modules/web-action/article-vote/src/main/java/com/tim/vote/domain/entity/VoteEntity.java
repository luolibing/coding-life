package com.tim.vote.domain.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by luolibing on 2018/4/24.
 */
@Data
public class VoteEntity implements Serializable {

    private Long articleId;

    private String voter;
}
