package com.cheng.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheng.common.dto.LoginDto;
import com.cheng.common.lang.Result;
import com.cheng.entity.MUser;
import com.cheng.service.IMUserService;
import com.cheng.utils.JwtUtils;
import io.jsonwebtoken.lang.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("all")
/**
 * @Author: ChengJun
 * @CreateDate: 2021/7/27 14:09
 **/
@RestController
public class AccountController {

    @Autowired
    private IMUserService imUserService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        MUser mUser = imUserService.getOne(new QueryWrapper<MUser>().eq("username", loginDto.getUsername()));
        Assert.notNull(mUser, "用户不存在");


        if (!mUser.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.fail("密码错误");
        }

        String jwt = jwtUtils.generateToken(mUser.getId());

        response.setHeader("Authorization",jwt);
        response.setHeader("Access-Control-Expose-Headers","Authorization");

        return Result.succ(MapUtil.builder()
                .put("id",mUser.getId())
                .put("username",mUser.getUsername())
                .put("avatar",mUser.getAvatar())
                .put("emial",mUser.getEmail())
                .map()
        );
    }

    @GetMapping("logout")
    @RequiresAuthentication
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }
}
