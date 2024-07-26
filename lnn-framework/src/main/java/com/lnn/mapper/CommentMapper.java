package com.lnn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnn.domin.entity.Comment;
import org.springframework.stereotype.Repository;

/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-26 19:35:22
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

}

