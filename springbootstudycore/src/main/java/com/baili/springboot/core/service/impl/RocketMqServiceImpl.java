package com.baili.springboot.core.service.impl;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.baili.springboot.core.service.IRocketMqService;
import com.baili.springboot.study.common.domain.BizResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangjingming on 2017/8/12.
 */
@Slf4j
@Service
public class RocketMqServiceImpl implements IRocketMqService {

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Override
    public BizResult<Boolean> sendMessage() throws Exception {
        for (int i = 0; i < 5; i++) {
            Message msg = new Message("TEST",// topic
                    "first",// tag
                    "KKK",//key用于标识业务的唯一性
                    ("Hello RocketMQ !!!!!!!!!!" + "-->>" + i).getBytes()// body 二进制字节数组
            );
            SendResult result = defaultMQProducer.send(msg);
            System.out.println(result);
        }
        return new BizResult<>();
    }
}
