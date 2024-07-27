package com.lnn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lnn.domin.ResponseResult;
import com.lnn.domin.entity.Link;
import com.lnn.domin.entity.User;

public interface UserService extends IService<User> {
    ResponseResult userInfo();
}
