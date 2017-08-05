package com.baili.springboot.core.quartz.model;


import lombok.Data;

import java.util.Map;

/**
 * Created by jiangjingming on 2017/6/26.
 */
@Data
public class HandlerParam {

    private TaskGroupEnum taskGroup;

    private String key;

    private Map<String,Object> params;
}
