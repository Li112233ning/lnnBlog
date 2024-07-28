package com.lnn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.constants.SystemConstants;
import com.lnn.domin.ResponseResult;
import com.lnn.domin.entity.Article;
import com.lnn.domin.entity.Category;
import com.lnn.domin.vo.ArticleListVo;
import com.lnn.domin.vo.HotArticleVo;
import com.lnn.domin.vo.PageVo;
import com.lnn.domin.vo.articleDetailVo;
import com.lnn.service.ArticleService;
import com.lnn.mapper.ArticleMapper;
import com.lnn.service.CategoryService;
import com.lnn.utils.BeanCopyUtils;
import com.lnn.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.RC2ParameterSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2024-07-18 15:57:35
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);

        Page<Article> page = new Page<>(SystemConstants.PAGE_STATUS_CURRENT,SystemConstants.PAGE_STATUS_SIZE);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();

        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles,HotArticleVo.class);

        hotArticleVos.forEach(hotArticleVo ->
                hotArticleVo.setViewCount(((Integer) redisCache.getCacheMapValue("article:viewCount", hotArticleVo.getId().toString())).longValue())
        );


        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getIsTop);


        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();

        //查询categoryName
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        articleListVos.forEach(articleListVo ->
                articleListVo.setViewCount(((Integer) redisCache.getCacheMapValue("article:viewCount", articleListVo.getId().toString())).longValue())
        );

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);
        //从redis中获取viewCount后存入article
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        articleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, articleDetailVo.class);

        Category category = categoryService.getById(article.getCategoryId());
        if(category != null){
            articleDetailVo.setCategoryName(category.getName());
        }

        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }
}


