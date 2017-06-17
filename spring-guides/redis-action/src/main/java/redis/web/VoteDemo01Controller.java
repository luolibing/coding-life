package redis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.entity.Article;
import redis.service.VoteDemo1;

/**
 * Created by LuoLiBing on 16/3/9.
 */
@RestController
@RequestMapping(value = "/article")
public class VoteDemo01Controller {

    @Autowired
    private VoteDemo1 chapter01;

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Object postArticle(@RequestBody Article article) {
        chapter01.postArticle(article);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET)
    public Object getArticles(@PathVariable Integer page) {
        return chapter01.getArticles(page);
    }

    @RequestMapping(value = "{voter}/vote/{articleId}", method = RequestMethod.PUT)
    public Object voteArticle(@PathVariable String voter, @PathVariable String articleId) {
        chapter01.articleVote("article:" + articleId, voter);
        return ResponseEntity.ok().build();
    }
}
