package com.lnn.service;

import com.lnn.domin.ResponseResult;
import com.lnn.domin.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}