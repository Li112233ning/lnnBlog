package com.lnn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lnn.domin.ResponseResult;
import com.lnn.domin.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2024-07-26 19:35:22
 */
public interface CommentService extends IService<Comment> {


    ResponseResult addComment(Comment comment);

    ResponseResult commentList(String articleComment, Long articleId, Integer pageNum, Integer pageSize);
}


