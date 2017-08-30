package cn.tim.springboot.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * User: luolibing
 * Date: 2017/8/30 17:35
 */
@Data
@Document(indexName = "article", type = "article", shards = 1, replicas = 0, refreshInterval = "-1")
public class Article {

    @Id
    private Long id;

    private String title;

    private String author;

    private String content;
}
