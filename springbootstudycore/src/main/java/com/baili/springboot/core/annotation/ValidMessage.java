package com.baili.springboot.core.annotation;

import java.lang.annotation.*;

/**
 * Created by jiangjingming on 2018/1/6.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidMessage {
    /**
     * 字段不能为空含义
     * @return
     */
    String message() default "不能为空";

    /**
     * 是否验证
     * @return
     */
    boolean isValid() default true;
}
