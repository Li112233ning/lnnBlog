package com.lnn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.constants.SystemConstants;
import com.lnn.domin.ResponseResult;
import com.lnn.domin.entity.Link;
import com.lnn.domin.vo.LinkVo;
import com.lnn.mapper.LinkMapper;
import com.lnn.service.LinkService;
import com.lnn.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2024-07-25 22:11:00
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus , SystemConstants.LINK_STATUS_NORMAL);
        List<Link> linkList = list(queryWrapper);

        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }
}


