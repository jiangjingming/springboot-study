package com.baili.springboot.study.common.config.quartz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步通知线程池
 * 适合处理服务耗时短的服务
 * Created by jiangjingming on 2017/6/16.
 */
@Configuration
public class CachedThreadPoolConfig {

    @Bean
    public ExecutorService getCachedThreadPool() {
        return Executors.newCachedThreadPool();
    }
}
