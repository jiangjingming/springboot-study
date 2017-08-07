package com.baili.springboot.core.service;

import com.baili.springboot.core.model.MessageMarkDownModel;
import com.baili.springboot.core.model.MessageTextModel;

/**
 * Created by jiangjingming on 2017/8/5.
 */
public interface IDingDingRobotService {

    Boolean sendDingDingRobotMarkMessage(MessageMarkDownModel messageModel);

    Boolean sendDingDingRobotTextMessage(MessageTextModel messageTextModel);
}
