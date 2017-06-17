package redis.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.entity.Article;

import java.util.*;

/**
 * Created by LuoLiBing on 16/2/27.
 * 一个文章网站的投票功能
 * 每个用户可以对文章一天投一次票
 */
@Service
public class VoteDemo1 {

    public final static int ONE_WEEK_IN_SECONDS = 7 * 86400;

    /**
     * 每次新增的分数
     */
    public final static int VOTE_SCORE = 10;


    private static final int ARTICLES_PER_PAGE = 25;


    /**
     * 发表文章
     * 1    添加一个文章投票的一个集合
     *
     * @param articleBean
     * @return
     */
    public String postArticle(Article articleBean) {
        Jedis conn = new Jedis("localhost");
        conn.select(15);
        String articleId = String.valueOf(conn.incr("article:"));

        // 新增一个投票SET,并且设置过期时间
        String voted = "voted:" + articleId;
        conn.sadd(voted, articleBean.getUser());
        conn.expire(voted, ONE_WEEK_IN_SECONDS);

        // 添加一个文章的hash
        Map<String, String> articleData = new HashMap<String, String>();
        long now = System.currentTimeMillis() / 1000;
        String article = "article:" + articleId;
        articleData.put("now", String.valueOf(now));
        articleData.put("title", articleBean.getTitle());
        articleData.put("user", articleBean.getUser());
        articleData.put("link", articleBean.getLink());
        articleData.put("votes", "0");
        conn.hmset(article, articleData);

        // 添加时间列表
        conn.zadd("time:", now, article);
        // 添加一个得分列表,考虑时间加权,越新的文章有加权
        conn.zadd("score:", now + 100, article);
        return articleId;
    }


    /**
     * 文章投票功能
     * @param article
     * @param voter         投票人
     */
    public void articleVote(String article, String voter) {
        Jedis conn = new Jedis("localhost");
        conn.select(15);
        /***
         * 超过一周前的不能再次投票
         */
        long cutoff = (System.currentTimeMillis() / 1000) - ONE_WEEK_IN_SECONDS;
        if(conn.zscore("time:", article) < cutoff) {
            return;
        }

        /**
         * article:1001
         * 1    可以在这个地方限制,一天一个用户只能投一次票,每天生成一个新的voted set
         */
        String articleId = article.substring(article.indexOf(":") + 1);
        // voted:1001 这篇文章这个用户之前没有投过票,才可以投票
        if(conn.sadd("voted:" + articleId, voter) == 1) {
            conn.zincrby("score:", VOTE_SCORE, article);
            // 给文章加票数
            conn.hincrBy(article, "votes", 1L);

        }
    }


    /**
     * 返回文章列表
     * @param page
     * @return
     */
    public List<Map<String, String>> getArticles(int page) {
        Jedis conn = new Jedis("localhost");
        conn.select(15);
        return getArticles(page, "score:");
    }


    public List<Map<String, String>> getArticles(int page, String order) {
        Jedis conn = new Jedis("localhost");
        conn.select(15);
        int start = (page -1) * ARTICLES_PER_PAGE;
        int end  = start + ARTICLES_PER_PAGE - 1;
        Set<String> ids = conn.zrevrange(order, start, end);
        List<Map<String,String>> articles = new ArrayList<Map<String,String>>();
        for(String id: ids) {
            Map<String, String> articleData = conn.hgetAll(id);
            articleData.put("id", id);
            articles.add(articleData);
        }
        return articles;
    }

    /**
     * 返回今天还剩余的时间
     * @return
     */
    private Long getSurplusTodayTime() {
        return null;
    }

}
