package com.cheng.utils;

import com.cheng.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

@SuppressWarnings("all")
/**
 * @Author: ChengJun
 * @CreateDate: 2021/7/27 17:15
 **/
public class ShiroUtils {

    public static AccountProfile getProfile() {
     return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
