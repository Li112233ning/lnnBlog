package com.lnn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnn.domin.entity.Article;
import org.springframework.stereotype.Repository;

/**
 * 文章表(Article)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-18 15:57:36
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

}

