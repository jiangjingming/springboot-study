package com.baili.springboot.core.quartz.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baili.dao.QuartzJobMapper;
import com.baili.entity.QuartzJob;
import com.baili.entity.QuartzJobCriteria;
import com.baili.springboot.core.annotation.ExceptionNotice;
import com.baili.springboot.core.enums.JobStatusEnum;
import com.baili.springboot.core.quartz.ScheduleJob;
import com.baili.springboot.core.quartz.job.QuartzHandleJob;
import com.baili.springboot.core.quartz.service.IQurtzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jiangjingming on 17/6/22.
 */
@Slf4j
@Component
public class QurtzServiceImpl implements IQurtzService {

    @Autowired(required = false)
    private Scheduler scheduler;

    @Autowired
    private QuartzJobMapper qrtzJobManager;

    /**
     * 添加或更新任务
     * @param job
     */
    @Override
    @ExceptionNotice(noticeRemarks = "添加任务或者更新任务")
    public void addOrUpdateJob(ScheduleJob job) {
        try {
            addOrUpdateJob(QuartzHandleJob.class, job);
        } catch (SchedulerException e) {
            log.error("添加任务或者更新任务出现异常，SchedulerException：{}", e);
            throw new RuntimeException("添加任务或者更新任务出现异常，SchedulerException："+e.toString());
        }
    }

    /**
     * 删除任务
     * @param job
     */
    @Override
    @ExceptionNotice(noticeRemarks = "删除任务")
    public void deleteJob(ScheduleJob job) {
        JobKey jobKey = getJobkey(job);
        try {
            //停止任务
            scheduler.deleteJob(jobKey);
            log.info("删除任务==>name:{},group:{}", jobKey.getName(), jobKey.getGroup());
        } catch (SchedulerException e) {
            log.error("删除任务失败==>name:{},group:{},异常e:{}", jobKey.getName(), jobKey.getGroup(), e);
            throw new RuntimeException("预警实例任务删除失败");
        }
    }

    /**
     * 恢复任务
     * @param job
     */
    @Override
    @ExceptionNotice(noticeRemarks = "恢复任务")
    public void enableJob(ScheduleJob job) {
        JobKey jobKey = getJobkey(job);
        try {
            //恢复任务
            scheduler.resumeJob(jobKey);
            log.info("恢复任务==>name:{},group:{}", jobKey.getName(), jobKey.getGroup());
        } catch (SchedulerException e) {
            log.error("恢复任务失败==>name:{},group:{},异常e:{}", jobKey.getName(), jobKey.getGroup(), e);
            throw new RuntimeException("预警实例恢复任务失败");
        }
    }

    /**
     * 停止任务
     * @param job
     */
    @Override
    @ExceptionNotice(noticeRemarks = "停止任务")
    public void disableJob(ScheduleJob job) {
        JobKey jobKey = getJobkey(job);
        try {
            //停止任务
            scheduler.pauseJob(jobKey);
            log.info("停止任务==>name:{},group:{}", jobKey.getName(), jobKey.getGroup());
        } catch (SchedulerException e) {
            log.error("停止任务失败==>name:{},group:{},异常e:{}", jobKey.getName(), jobKey.getGroup(), e);
            throw new RuntimeException("预警实例停止任务失败");
        }
    }

    /**
     * 添加或修改定时任务实例
     * @param clazz
     * @param job
     * @throws SchedulerException
     */
    private void addOrUpdateJob(Class clazz, ScheduleJob job) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        Trigger trigger = scheduler.getTrigger(triggerKey);

        if (null == trigger) {
            //创建预警任务
            JobDetail jobDetail = JobBuilder.newJob(clazz).storeDurably(true).withIdentity(job.getJobName(), job.getJobGroup())
                    .withDescription(job.getDesc()).build();
            //jobDetail.getJobDataMap().put(job.getJobName() + "_" + job.getJobGroup(), job);

            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
                    .withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("创建任务，Job:{}", job.toString());
        } else {
            // 更新任务时间
            ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            trigger = trigger.getTriggerBuilder().withSchedule(scheduleBuilder).startNow().build();
            scheduler.rescheduleJob(triggerKey,trigger);
            log.info("任务已经存在，修改任务，Job:{}", job.toString());
        }
    }

    /**
     * 获取jobkey
     * @param job
     * @return
     */
    private JobKey getJobkey(ScheduleJob job) {
        return new JobKey(job.getJobName(), job.getJobGroup());
    }

    /**
     * 任务初始化
     * @return
     */
    @Override
    @Transactional
    @ExceptionNotice(noticeRemarks = "任务初始化")
    public Boolean initQuartzData() {
        QuartzJobCriteria query = new QuartzJobCriteria();
        query.createCriteria().andIsDeletedEqualTo(0).andJobStatusEqualTo(JobStatusEnum.UNINITIALIZED.getCode());
        List<QuartzJob> qrtzJobDOList = qrtzJobManager.selectByExample(query);
        if (CollectionUtils.isEmpty(qrtzJobDOList)) {
            log.info("没有需要初始化的定时任务");
            log.info("初始化任务默认扫描Qrtzjob表，jobStatus状态为-1的才能初始化");
            return Boolean.TRUE;
        }

        for (QuartzJob jobDO:qrtzJobDOList) {
            ScheduleJob job = new ScheduleJob();
            job.setJobGroup(jobDO.getJobGroup());
            job.setJobName(jobDO.getJobKey());
            job.setCronExpression(jobDO.getJobCronExpress());
            job.setDesc(jobDO.getJobGroup() + job.getJobName());
            if (isContainedJob(job)) {
                log.error("Scheduler中已经存在该job,参数为为param ==>{}",job.toString());
                continue;
            }
            jobDO.setJobStatus(JobStatusEnum.NORMAL.getCode());
            //修改定时任务状态为
            qrtzJobManager.updateByPrimaryKeySelective(jobDO);
            //添加定时任务实例
            this.addOrUpdateJob(job);
        }
        return Boolean.TRUE;
    }

    /**
     * 判断函数: 判断Scheduler中是否有该job
     * @param job
     * @return
     */
    private boolean isContainedJob(ScheduleJob job){
        boolean result = true;

        try{
            JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if(null == jobDetail){
                result = false;
            }
        }catch (SchedulerException e){
            log.error("查询判断Scheduler中是否有该job失败==>param:{},异常e:{}", job.toString(), e);
            throw new RuntimeException("判断函数Scheduler中是否有该job出现异常");
        }
        return result;
    }

}
