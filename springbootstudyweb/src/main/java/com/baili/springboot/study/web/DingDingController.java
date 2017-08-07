package com.baili.springboot.study.web;

import com.baili.springboot.core.service.IDingDingRobotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiangjingming on 2017/8/5.
 */
@Slf4j
@RequestMapping(value = "ding")
@RestController
public class DingDingController {
    @Autowired
    private IDingDingRobotService dingDingRobotService;

    @RequestMapping(value = "send",method = RequestMethod.GET)
    public Boolean DingDingSendMessage() {
        return dingDingRobotService.sendDingDingRobotMarkMessage(null);
    }
}
