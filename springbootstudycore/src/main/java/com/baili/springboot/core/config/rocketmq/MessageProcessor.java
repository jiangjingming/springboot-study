package com.baili.springboot.core.config.rocketmq;

import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * Created by eggyer on 2017/3/25.
 */
public interface MessageProcessor {
    /**
     * 处理消息的接口
     * @param messageExt
     * @return
     */
    boolean handleMessage(MessageExt messageExt);
}