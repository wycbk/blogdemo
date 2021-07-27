package com.cheng.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.cheng.entity.MUser;
import com.cheng.service.IMUserService;
import com.cheng.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings("all")
/**
 * @Author: ChengJun
 * @CreateDate: 2021/7/24 17:55
 **/
@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    IMUserService imUserService;

    // 为了让realm支持jwt的凭证校验
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    // 权限校验
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    // 登录认证校验
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwt = (JwtToken) authenticationToken;
        log.info("jwt----------------->{}", jwt);

        String userId = jwtUtils.getClaimsFromToken((String) jwt.getPrincipal()).getSubject();
        MUser mUser = imUserService.getById(Long.valueOf(userId));

        if (mUser == null) {
            throw new UnknownAccountException("账户不存在");
        }

        if (mUser.getStatus() == -1) {
            throw new LockedAccountException("账户已被锁定");
        }
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(mUser, profile);
        return new SimpleAuthenticationInfo(profile,jwt.getCredentials(), getName());
    }

}
