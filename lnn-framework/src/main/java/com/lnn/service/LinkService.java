package com.lnn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lnn.domin.ResponseResult;
import com.lnn.domin.entity.Link;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2024-07-25 22:11:00
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}


