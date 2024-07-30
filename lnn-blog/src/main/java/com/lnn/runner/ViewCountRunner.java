package com.lnn.runner;

import com.lnn.domain.entity.Article;
import com.lnn.service.ArticleService;
import com.lnn.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {

        List<Article> articleList = articleService.list();
        Map<String , Integer> viewCountMap = articleList.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));

        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
