package com.tim.vote.application;

import org.springframework.stereotype.Service;

/**
 * Created by luolibing on 2018/5/3.
 */
@Service
public class VoteService {

    public void vote(long articleId, String userPin) {
        // 验证， 这个人是否已经投过票，不能重复投票

        // 投票记录流水表

        // 票数更新
    }
}
