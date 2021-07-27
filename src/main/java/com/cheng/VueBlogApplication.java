package com.cheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("all")
/**
 * @Author: ChengJun
 * @CreateDate: 2021/7/24 10:26
 **/
@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScans({@ComponentScan("com.cheng.config"),@ComponentScan("com.cheng.utils")})
public class VueBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueBlogApplication.class, args);
    }
}
