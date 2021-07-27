package com.cheng.shiro;

import org.apache.shiro.authc.AuthenticationToken;

@SuppressWarnings("all")
/**
 * @Author: ChengJun
 * @CreateDate: 2021/7/25 9:54
 **/
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
