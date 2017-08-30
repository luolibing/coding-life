package cn.tim.springboot.elasticsearch.entity.es;

import cn.tim.springboot.elasticsearch.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * User: luolibing
 * Date: 2017/8/30 18:25
 */
public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {
}
