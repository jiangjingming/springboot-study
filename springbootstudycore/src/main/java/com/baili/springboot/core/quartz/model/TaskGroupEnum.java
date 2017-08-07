package com.baili.springboot.core.quartz.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Quarz定时任务分组
 * Created by baili
 */
@AllArgsConstructor
public enum TaskGroupEnum {

    FULL_MENU("FULL_MENU", "全部菜单任务"),
    TODAY_MENU("TODAY_MENU","今天要点的菜"),
    DAILY_REMIND("DAILY_REMIND","日常提醒")

    ;

    @Getter
    private String code;
    @Getter
    private String desc;

    /**
     * 将code包装成枚举类型
     * @param code
     * @return
     */
    public static TaskGroupEnum of(String code){
        for (TaskGroupEnum statusEnum: TaskGroupEnum.values()) {
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
    public static boolean contain(String code){
        for (TaskGroupEnum statusEnum: TaskGroupEnum.values()) {
            if(statusEnum.getCode().equals(code)){
                return true;
            }
        }
        return false;
    }
}
