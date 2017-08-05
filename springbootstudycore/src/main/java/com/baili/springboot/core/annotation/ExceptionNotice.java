package com.baili.springboot.core.annotation;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionNotice {

    /** 需要通知人员的花名： **/
    String noticeNickName() default "百里";

    /** 通知消息的备注内容： **/
    String noticeRemarks() default "无";

    /**
     * 消息发送类型(位图,目前三位,短信,钉钉,邮件)
     * 0表示不发送，1表示发送
     * @return
     */
    String sendWayType() default "111";

}