package com.baili.springboot.study.web;

import com.baili.springboot.core.service.IRocketMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiangjingming on 2017/8/12.
 */
@Slf4j
@RestController
public class RocketMqController {

    @Autowired
    private IRocketMqService rocketMqService;

    @RequestMapping(value = "init",method = RequestMethod.GET)
    public Object sendMessage() {
        log.info("开始初始化任务");
        try {
            rocketMqService.sendMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.TRUE;
    }

}
