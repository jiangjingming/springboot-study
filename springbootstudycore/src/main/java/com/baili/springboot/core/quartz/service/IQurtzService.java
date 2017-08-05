package com.baili.springboot.core.quartz.service;


import com.baili.springboot.core.quartz.ScheduleJob;

/**
 * Created by jiangjingming on 17/6/22.
 */
public interface IQurtzService {

    /**
     * 添加或更新任务
     * @param job
     */
    void addOrUpdateJob(ScheduleJob job);
    /**
     * 删除任务
     * @param job
     */
    void deleteJob(ScheduleJob job);

    /**
     * 恢复任务
     * @param job
     */
    void enableJob(ScheduleJob job);

    /**
     * 停止任务
     * @param job
     */
    void disableJob(ScheduleJob job);

    /**
     * 初始化任务
     */
    Boolean initQuartzData();

}
