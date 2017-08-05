package com.baili.springboot.core.service;


import com.baili.springboot.study.common.domain.EarlyWarningTaskDTO;

/**
 * 任务对外服务service
 * Created by jiangjingming on 2017/6/23.
 */
public interface IEarlyWarningQuartzService {

    /**
     * 添加预警任务
     * @param earlyWarningTaskDTO
     * @return
     */
    void addWarningTask(EarlyWarningTaskDTO earlyWarningTaskDTO);
    /**
     * 预警任务修改
     * @param earlyWarningTaskDTO
     * @return
     */
    void updateWarningTask(EarlyWarningTaskDTO earlyWarningTaskDTO);

    /**
     * 预警任务开启
     * @param earlyWarningTaskDTO
     * @return
     */
    void enabledWarningTask(EarlyWarningTaskDTO earlyWarningTaskDTO);

    /**
     * 预警任务禁用
     * @param earlyWarningTaskDTO
     * @return
     */
    void disabledWarningTask(EarlyWarningTaskDTO earlyWarningTaskDTO);
}
