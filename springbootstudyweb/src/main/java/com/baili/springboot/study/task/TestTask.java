package com.baili.springboot.study.task;

import com.baili.springboot.core.annotation.LockRedis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by jiangjingming on 2018/1/13.
 */
@Slf4j
@Component
public class TestTask {


    @Scheduled(cron = "0 */1 * * * ?")
    @LockRedis(isDelayReleaseLock = true)
    public void sout() throws InterruptedException {
        log.error("当前时间=========================："+ new Date());
    }


}
