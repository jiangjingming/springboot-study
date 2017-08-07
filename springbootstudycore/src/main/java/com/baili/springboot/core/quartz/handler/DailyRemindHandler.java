package com.baili.springboot.core.quartz.handler;

import com.baili.springboot.core.quartz.model.HandlerParam;
import com.baili.springboot.core.quartz.model.TaskGroupEnum;
import com.baili.springboot.core.service.IDingDingRobotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 今天的任务
 * Created by jiangjingming on 2017/6/26.
 */
@Component
@Slf4j
public class DailyRemindHandler extends Handler {

    @Autowired
    private IDingDingRobotService dingDingRobotService;

    @Override
    public void handleRequest(HandlerParam handlerParam) {
        log.info("日常提醒");
        dingDingRobotService.sendDingDingRobotTextMessage(null);


    }

    @Override
    public TaskGroupEnum getTaskGroupEnum() {
        return TaskGroupEnum.DAILY_REMIND;
    }
}
