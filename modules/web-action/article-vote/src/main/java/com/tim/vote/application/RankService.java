package com.tim.vote.application;

import com.tim.vote.infrastructure.constant.RedisKeyEnum;
import com.tim.vote.infrastructure.redis.RedisSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by luolibing on 2018/5/3.
 */
@Service
public class RankService {

    @Autowired
    private RedisSupport redisSupport;

    public Set<ZSetOperations.TypedTuple<Object>> articleRank(boolean asc, int start, int end) {
        return redisSupport.range(RedisKeyEnum.ARTICLE_SCORE.name(), asc, start, end);
    }
}
