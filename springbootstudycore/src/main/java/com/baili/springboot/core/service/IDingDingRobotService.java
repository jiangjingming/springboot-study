package com.baili.springboot.core.service;

import com.baili.springboot.core.model.MessageMarkDownModel;
import com.baili.springboot.core.model.MessageModel;

/**
 * Created by jiangjingming on 2017/8/5.
 */
public interface IDingDingRobotService {

    Boolean sendDingDingRobotMessage(MessageMarkDownModel messageModel);
}
