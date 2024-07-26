package com.lnn.service;

import com.lnn.domin.ResponseResult;
import com.lnn.domin.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);
}