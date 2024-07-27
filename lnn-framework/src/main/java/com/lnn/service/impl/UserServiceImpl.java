package com.lnn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.domin.ResponseResult;
import com.lnn.domin.entity.User;
import com.lnn.domin.vo.UserInfoVo;
import com.lnn.mapper.UserMapper;
import com.lnn.service.UserService;
import com.lnn.utils.BeanCopyUtils;
import com.lnn.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }
}
