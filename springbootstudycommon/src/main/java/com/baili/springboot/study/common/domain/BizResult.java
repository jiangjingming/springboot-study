package com.baili.springboot.study.common.domain;

import lombok.Data;

import java.util.Map;

/**
 * Created by jiangjingming on 2017/8/12.
 */
@Data
public class BizResult<T> {
    private boolean success = false;
    private String code;
    private String message;
    private T data;
    private Map<String, Object> extraInfo;
}
