package com.baili.springboot.study.common.util;

import lombok.Data;

/**
 * Created by jiangjingming on 2018/1/6.
 */
@Data
public class Result<T> {

    private boolean success = false;
    private T data;
    private String code;
    private String message;

    public Result() {
    }

    public <T> Result<T> Result() {
        return new Result();
    }

    public Result create(String code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public Result create(Boolean success) {
        Result result = new Result();
        result.setSuccess(success);
        return result;
    }
}
