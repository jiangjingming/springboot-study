package com.baili.springboot.study;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Spring Boot 应用启动类
 *
 * Created by jingjingming on 17/06/06.
 */
@EnableCaching
@ImportResource(locations = { "classpath:dubbo/dubbo-all.xml" })
@ComponentScan({"com.baili.springboot.core","com.baili.springboot"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(Application.class,args);
    }
}
