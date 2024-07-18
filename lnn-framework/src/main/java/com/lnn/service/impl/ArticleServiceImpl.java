package com.lnn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.constants.SystemConstants;
import com.lnn.domin.ResponseResult;
import com.lnn.domin.entity.Article;
import com.lnn.domin.vo.HotArticleVo;
import com.lnn.service.ArticleService;
import com.lnn.mapper.ArticleMapper;
import com.lnn.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2024-07-18 15:57:35
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

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

//        ArrayList<HotArticleVo> hotArticleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo hotArticleVo = new HotArticleVo();
//            BeanUtils.copyProperties(article,hotArticleVo);
//            hotArticleVos.add(hotArticleVo);
//        }

        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles,HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }
}


