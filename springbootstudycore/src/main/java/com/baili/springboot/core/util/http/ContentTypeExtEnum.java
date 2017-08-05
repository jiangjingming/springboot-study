package com.baili.springboot.core.util.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 扩展contentType字符集
 * Created by jiangjingming on 2017/6/5.
 */
@AllArgsConstructor
public enum ContentTypeExtEnum {

    APPLICATION_X_WWWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    ;

    @Getter
    private String code;
}
