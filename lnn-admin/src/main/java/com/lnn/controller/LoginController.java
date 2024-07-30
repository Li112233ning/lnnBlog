package com.lnn.controller;

import com.lnn.domain.ResponseResult;
import com.lnn.domain.entity.Menu;
import com.lnn.domain.entity.User;
import com.lnn.domain.vo.AdminUserInfoVo;
import com.lnn.domain.vo.RoutersVo;
import com.lnn.domain.vo.UserInfoVo;
import com.lnn.enums.AppHttpCodeEnum;
import com.lnn.exception.SystemException;
import com.lnn.service.LoginService;
import com.lnn.service.MenuService;
import com.lnn.service.RoleService;
import com.lnn.utils.BeanCopyUtils;
import com.lnn.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }


    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询权限信息
        List<String> perms =  menuService.selectPermsByUserId(userId);
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(userId);
        //获取用户信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(SecurityUtils.getLoginUser().getUser(), UserInfoVo.class);

        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }


    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = roleService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }


    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

}