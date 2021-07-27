package com.cheng.common.exception;

import com.cheng.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SuppressWarnings("all")
/**
 * @Author: ChengJun
 * @CreateDate: 2021/7/25 16:14
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕捉shiro的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED) //因为前后端分离 返回一个状态 一般是401 没有权限
    @ExceptionHandler(value =  ShiroException.class)//捕捉shiro的异常ShiroException是大部分异常的父类
    public Result handler(ShiroException e){
        log.error("shiro异常：-----------------{}",e);
        return Result.fail(401,e.getMessage(),null);
    }

    /**
     * 实体校验异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) {
        log.error("实体校验异常：-----------------{}", e);
        return Result.fail(e.getBindingResult().getAllErrors().stream().findFirst().get().getDefaultMessage());
    }

    /**
     * Assert异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) {
        log.error("Assert异常：-----------------{}", e);
        return Result.fail(e.getMessage());
    }

    /**
     * 运行时异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) //因为前后端分离 返回一个状态
    @ExceptionHandler(value =  RuntimeException.class)//捕获运行时异常
    public Result handler(RuntimeException e){
        log.error("运行时异常：-----------------{}",e);
        return Result.fail(e.getMessage());
    }
}
