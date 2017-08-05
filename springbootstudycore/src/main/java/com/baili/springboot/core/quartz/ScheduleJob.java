package com.baili.springboot.core.quartz;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by jiangjingming on 17/6/22.
 */
@Data
public class ScheduleJob implements Serializable {
    private static final long serialVersionUID = 6109706829518519401L;
    /**
     * 定时任务名称
     */
    private String jobName;
    /**
     * 定时任务组
     */
    private String jobGroup;
    /**
     * 定时任务时间表达式
     */
    private String cronExpression;
    /**
     * 任务描述
     */
    private String desc;
}
