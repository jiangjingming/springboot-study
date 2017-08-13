package com.baili.springboot.core.service;

import com.baili.springboot.study.common.domain.BizResult;

/**
 * Created by jiangjingming on 2017/8/12.
 */
public interface IRocketMqService {

    BizResult<Boolean> sendMessage() throws Exception;
}
