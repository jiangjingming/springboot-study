package com.baili.springboot.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by baili
 */
@AllArgsConstructor
public enum JobStatusEnum {
    NORMAL(0, "正常"),
    UNINITIALIZED(-1,"未初始化"),
    FORBIDDEN(99, "禁用");

    @Getter
    private Integer code;
    @Getter
    private String desc;

    /**
     * 将code包装成枚举类型
     * @param code
     * @return
     */
    public static JobStatusEnum valueOf(Integer code){
        for (JobStatusEnum statusEnum: JobStatusEnum.values()) {
            if(statusEnum.getCode().equals(code)){
                return statusEnum;
            }
        }
        return null;
    }

    /**
     * 判断是否包含
     * @param code
     * @return
     */
    public static boolean contain(Integer code){
        for (JobStatusEnum statusEnum: JobStatusEnum.values()) {
            if(statusEnum.getCode().equals(code)){
                return true;
            }
        }
        return false;
    }
}
