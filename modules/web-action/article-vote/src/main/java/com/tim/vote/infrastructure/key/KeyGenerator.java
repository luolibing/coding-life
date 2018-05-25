package com.tim.vote.infrastructure.key;

import com.tim.vote.infrastructure.constant.RedisKeyEnum;

/**
 * Created by luolibing on 2018/5/4.
 */
public class KeyGenerator {

    public static String articleIdKey(long articleId) {
        return "article_" + articleId;
    }

    public static String userIdKey(String userPin) {
        return "user_" + userPin;
    }

    public static String userVoteRedisKey(long articleId) {
        return RedisKeyEnum.USER_ARTICLE_VOTED + "_" + articleId;
    }

    public static String groupId(long groupId) {
        return "group_" + groupId;
    }
}
