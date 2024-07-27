package com.lnn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lnn.constants.SystemConstants;
import com.lnn.domin.ResponseResult;
import com.lnn.domin.entity.Comment;
import com.lnn.domin.entity.User;
import com.lnn.domin.vo.CommentVo;
import com.lnn.domin.vo.PageVo;
import com.lnn.enums.AppHttpCodeEnum;
import com.lnn.exception.SystemException;
import com.lnn.mapper.CommentMapper;
import com.lnn.mapper.UserMapper;
import com.lnn.service.CommentService;
import com.lnn.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2024-07-26 19:35:22
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserMapper userMapper;


    @Override
    public ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_ROOT_ID_STATUS_NORMAL);
        //评论类型
        queryWrapper.eq(Comment::getType,commentType);

        //分页查询
        Page<Comment> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //查询所以根评论对应的子评论，并且赋值给对应的属性
        commentVoList.forEach(commentVo -> commentVo.setChildren(getChildren(commentVo.getId())));

        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));

    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = commentService.list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);

        commentVos.forEach(commentVo -> {
            String nickName = userMapper.selectById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);

            Optional.of(commentVo.getToCommentUserId())
                    .filter(id -> id != -1)
                    .map(userMapper::selectById)
                    .map(User::getNickName)
                    .ifPresent(commentVo::setToCommentUserName);
        });

//        for (CommentVo commentVo : commentVos) {
//            //通过creatyBy查询用户的昵称并赋值
//            String nickName = userMapper.selectById(commentVo.getCreateBy()).getNickName();
//            commentVo.setUsername(nickName);
//            //通过toCommentUserId查询用户的昵称并赋值
//            //如果toCommentUserId不为-1才进行查询
//            if(commentVo.getToCommentUserId()!=-1){
//                String toCommentUserName = userMapper.selectById(commentVo.getToCommentUserId()).getNickName();
//                commentVo.setToCommentUserName(toCommentUserName);
//            }
//        }
        return commentVos;
    }
}


