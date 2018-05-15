package com.tim.vote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1 文章的发布与查看
 * 2 对文章进行投票
 * 3 每人对每篇文章只能投一次票
 * 4 随着时间的流逝，同样票数的文章，越新的文章分数越高
 * 5 文章的分组
 * 6 投反对票
 * Created by luolibing on 2018/4/24.
 */
@SpringBootApplication
public class VoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteApplication.class, args);
    }
}
