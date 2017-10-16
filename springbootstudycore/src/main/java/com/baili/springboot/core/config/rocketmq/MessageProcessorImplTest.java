package com.baili.springboot.core.config.rocketmq;

import com.alibaba.rocketmq.common.message.MessageExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by eggyer on 2017/3/26.
 */
@Slf4j
@Component
public class MessageProcessorImplTest implements MessageProcessor {
    @Override
    public boolean handleMessage(MessageExt messageExt) {
        log.info("receive : " + messageExt.toString());
        log.info("消息内容：{}", new String(messageExt.getBody()));
        return true;
    }
}