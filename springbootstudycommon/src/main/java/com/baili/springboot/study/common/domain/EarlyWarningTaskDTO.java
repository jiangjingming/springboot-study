package com.baili.springboot.study.common.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Created by jiangjingming on 17/6/22.
 */
@Builder
@Data
public class EarlyWarningTaskDTO extends BaseDTO {

    /**
     * 任务分组
     */
    private String taskGroup;
    /**
     * 预警规则id
     */
    private Long riskRuleId;
    /**
     * 状态(0：正常，99：禁用）
     */
    private Integer jobStatus;
    /**
     * 定时任务时间表达式
     * 备注：启用、禁用功能时，这个参数不更新;
     */
    private String cronExpression;
}
