package com.cheng.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@SuppressWarnings("all")
/**
 * @Author: ChengJun
 * @CreateDate: 2021/7/27 14:21
 **/
@Data

public class LoginDto implements Serializable {

    @NotBlank(message = "昵称不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
