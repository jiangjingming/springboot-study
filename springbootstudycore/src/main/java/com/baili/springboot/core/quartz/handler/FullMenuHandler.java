package com.baili.springboot.core.quartz.handler;

import com.baili.springboot.core.quartz.model.HandlerParam;
import com.baili.springboot.core.quartz.model.TaskGroupEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 全部的菜单任务
 * Created by jiangjingming on 2017/6/26.
 */
@Component
@Slf4j
public class FullMenuHandler extends Handler {

    @Override
    public void handleRequest(HandlerParam handlerParam) {
        log.info("开始点菜任务");

    }

    @Override
    public TaskGroupEnum getTaskGroupEnum() {
        return TaskGroupEnum.FULL_MENU;
    }
}
