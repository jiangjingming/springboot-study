package com.baili.springboot.core.quartz.handler;

import com.baili.springboot.core.quartz.model.HandlerParam;
import com.baili.springboot.core.quartz.model.TaskGroupEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 今天的任务
 * Created by jiangjingming on 2017/6/26.
 */
@Component
@Slf4j
public class ToDayMenuHandler extends Handler {
    @Override
    public void handleRequest(HandlerParam handlerParam) {
        log.info("今天的菜单");

    }

    @Override
    public TaskGroupEnum getTaskGroupEnum() {
        return TaskGroupEnum.TODAY_MENU;
    }
}
