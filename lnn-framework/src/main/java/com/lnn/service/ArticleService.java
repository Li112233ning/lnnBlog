package com.lnn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lnn.domin.ResponseResult;
import com.lnn.domin.entity.Article;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2024-07-18 15:57:35
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);
}


