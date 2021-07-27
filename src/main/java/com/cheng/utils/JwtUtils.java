package com.cheng.utils;

import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@SuppressWarnings("all")
/**
 * @Author: ChengJun
 * @CreateDate: 2021/7/25 10:35
 * jwt工具类
 **/
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "chengjun.jwt")
public class JwtUtils {

    private String secret;
    private Long expire;
    private String header;

    /**
     * 生成jwt token
     *
     * @param userId
     * @return
     */
    public String generateToken(Long userId) {
        Date nowDate = new Date();
        // 过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId + "")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.debug("validate is token error" + e);
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @param expiration
     * @return true为过期
     */
    public Boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

}
