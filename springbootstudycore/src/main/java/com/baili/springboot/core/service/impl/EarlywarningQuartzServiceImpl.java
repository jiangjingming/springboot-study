package com.baili.springboot.core.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.baili.dao.QuartzJobMapper;
import com.baili.entity.QuartzJob;
import com.baili.entity.QuartzJobCriteria;
import com.baili.springboot.core.annotation.ExceptionNotice;
import com.baili.springboot.core.enums.JobStatusEnum;
import com.baili.springboot.core.quartz.ScheduleJob;
import com.baili.springboot.core.quartz.service.IQurtzService;
import com.baili.springboot.core.service.IEarlyWarningQuartzService;
import com.baili.springboot.study.common.domain.EarlyWarningTaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 预警定时任务服务
 * Created by jiangjingming on 2017/6/23.
 */
@Slf4j
@Service
public class EarlywarningQuartzServiceImpl implements IEarlyWarningQuartzService {

    @Autowired
    private IQurtzService quartzServer;

    @Autowired
    private QuartzJobMapper quartzJobMapper;

    /**
     * 添加预警任务
     * 状态为禁用
     * @param earlyWarningTaskDTO
     * @return
     */
    @Override
    @ExceptionNotice(noticeRemarks = "添加预警任务")
    public void addWarningTask(EarlyWarningTaskDTO earlyWarningTaskDTO) {
        this.addQurzJob(earlyWarningTaskDTO);
    }

    /**
     * 预警任务修改
     * @param earlyWarningTaskDTO
     * @return
     */
    @Override
    @Transactional
    @ExceptionNotice(noticeRemarks = "预警任务修改")
    public void updateWarningTask(EarlyWarningTaskDTO earlyWarningTaskDTO) {
        //查询任务
        QuartzJob qrtzJobDO = this.queryQurzJob(earlyWarningTaskDTO, null);
        if (qrtzJobDO == null) {
            log.error("查询该任务为null,参数为Params:{}",earlyWarningTaskDTO);
            throw new RuntimeException("查询该任务为null");
        }
        int jobStatus = qrtzJobDO.getJobStatus();
        switch (jobStatus) {
            case 0:
                earlyWarningTaskDTO.setJobStatus(JobStatusEnum.NORMAL.getCode());
                this.updateQurzJob(qrtzJobDO, earlyWarningTaskDTO);
                this.addOrUpdateScheduleJob(earlyWarningTaskDTO);
                break;
            case 99:
                earlyWarningTaskDTO.setJobStatus(JobStatusEnum.FORBIDDEN.getCode());
                this.updateQurzJob(qrtzJobDO, earlyWarningTaskDTO);
                break;
            default:
                log.error("[预警系统]-预警任务修改状态不正常，参数为：{}", earlyWarningTaskDTO);
                throw new RuntimeException("[预警系统]-预警任务修改状态不正常");
        }
    }

    /**
     * 预警任务开启
     * @param earlyWarningTaskDTO
     * @return
     */
    @Override
    @Transactional
    @ExceptionNotice(noticeRemarks = "预警任务开启")
    public void enabledWarningTask(EarlyWarningTaskDTO earlyWarningTaskDTO) {
        //查询任务
        QuartzJob qrtzJobDO = this.queryQurzJob(earlyWarningTaskDTO, JobStatusEnum.FORBIDDEN);
        if (qrtzJobDO == null) {
            log.error("查询该任务为null,参数为Params:{}",earlyWarningTaskDTO);
            throw new RuntimeException("查询该任务为null");
        }
        earlyWarningTaskDTO.setCronExpression(null);
        earlyWarningTaskDTO.setJobStatus(JobStatusEnum.NORMAL.getCode());
        this.updateQurzJob(qrtzJobDO, earlyWarningTaskDTO);

        earlyWarningTaskDTO.setCronExpression(qrtzJobDO.getJobCronExpress());
        this.addOrUpdateScheduleJob(earlyWarningTaskDTO);
    }

    /**
     * 预警任务禁用
     * @param earlyWarningTaskDTO
     * @return
     */
    @Override
    @Transactional
    @ExceptionNotice(noticeRemarks = "预警任务禁用")
    public void disabledWarningTask(EarlyWarningTaskDTO earlyWarningTaskDTO) {
        //查询任务
        QuartzJob qrtzJobDO = this.queryQurzJob(earlyWarningTaskDTO, JobStatusEnum.NORMAL);
        if (qrtzJobDO == null) {
            log.error("查询该任务为null,参数为Params:{}",earlyWarningTaskDTO);
            throw new RuntimeException("查询该任务为null");
        }
        earlyWarningTaskDTO.setCronExpression(null);
        earlyWarningTaskDTO.setJobStatus(JobStatusEnum.FORBIDDEN.getCode());
        //修改任务
        this.updateQurzJob(qrtzJobDO, earlyWarningTaskDTO);
        //修改任务实例
        this.disableScheduleJob(earlyWarningTaskDTO);
    }

    /**
     * 查询预警
     * @param earlyWarningTaskDTO
     * @param jobStatusEnum
     * @return
     */
    private QuartzJob queryQurzJob(EarlyWarningTaskDTO earlyWarningTaskDTO, JobStatusEnum jobStatusEnum) {
        QuartzJobCriteria query = new QuartzJobCriteria();
        if (Objects.isNull(jobStatusEnum)) {
            query.createCriteria().andJobGroupEqualTo(earlyWarningTaskDTO.getTaskGroup())
                    .andJobKeyEqualTo(String.valueOf(earlyWarningTaskDTO.getRiskRuleId()))
                    .andIsDeletedEqualTo(0);
        } else {
            query.createCriteria().andJobGroupEqualTo(earlyWarningTaskDTO.getTaskGroup())
                    .andJobKeyEqualTo(String.valueOf(earlyWarningTaskDTO.getRiskRuleId()))
                    .andJobStatusEqualTo(jobStatusEnum.getCode())
                    .andIsDeletedEqualTo(0);
        }
        List<QuartzJob> qrtzJobDOList = quartzJobMapper.selectByExample(query);
        if (CollectionUtils.isEmpty(qrtzJobDOList)) {
            log.error("没有查询到quartz任务的实例，参数为params ==>{}", earlyWarningTaskDTO.toString());
            return null;
        }
        if (qrtzJobDOList.size() > 1) {
            log.error("查询到quartz任务的实例多个错误，参数为params ==>{}，集合个数：{}", earlyWarningTaskDTO.toString(), qrtzJobDOList.size());
            return null;
        }
        return qrtzJobDOList.get(0);
    }

    /**
     * 添加或者修改scheduleJob
     * @param earlyWarningTaskDTO
     */
    private void addOrUpdateScheduleJob(EarlyWarningTaskDTO earlyWarningTaskDTO) {
        ScheduleJob job = new ScheduleJob();
        job.setJobGroup(earlyWarningTaskDTO.getTaskGroup());
        job.setJobName(String.valueOf(earlyWarningTaskDTO.getRiskRuleId()));
        if (StringUtils.isNotEmpty(earlyWarningTaskDTO.getCronExpression())) {
            job.setCronExpression(earlyWarningTaskDTO.getCronExpression());
            job.setDesc(job.getJobGroup() + job.getJobName());
        }
        quartzServer.addOrUpdateJob(job);

    }

    /**
     * 禁用scheduleJob
     * @param earlyWarningTaskDTO
     */
    private void disableScheduleJob(EarlyWarningTaskDTO earlyWarningTaskDTO) {
        ScheduleJob job = new ScheduleJob();
        job.setJobGroup(earlyWarningTaskDTO.getTaskGroup());
        job.setJobName(String.valueOf(earlyWarningTaskDTO.getRiskRuleId()));
        quartzServer.deleteJob(job);

    }

    /**
     * 修改
     * @param earlyWarningTaskDTO
     * @return
     */
    private void updateQurzJob(QuartzJob qrtzJobDO, EarlyWarningTaskDTO earlyWarningTaskDTO) {
        QuartzJob jobDO = new QuartzJob();
        jobDO.setJobGroup(earlyWarningTaskDTO.getTaskGroup());
        jobDO.setJobKey(String.valueOf(earlyWarningTaskDTO.getRiskRuleId()));
        jobDO.setId(qrtzJobDO.getId());
        jobDO.setJobStatus(earlyWarningTaskDTO.getJobStatus());
        if (StringUtils.isNotEmpty(earlyWarningTaskDTO.getCronExpression())) {
            jobDO.setJobCronExpress(earlyWarningTaskDTO.getCronExpression());
            jobDO.setJobDesc(earlyWarningTaskDTO.getTaskGroup() + jobDO.getJobKey());
        }
        int cnt = quartzJobMapper.updateByPrimaryKeySelective(jobDO);
        if (cnt < 1) {
            log.error("修改qrtzJob表失败，参数为param ==>{}", earlyWarningTaskDTO.toString());
            throw new RuntimeException("添加失败");
        }
    }

    /**
     * 添加任务
     * @param earlyWarningTaskDTO
     * @return
     */
    private void addQurzJob(EarlyWarningTaskDTO earlyWarningTaskDTO) {
        QuartzJobCriteria query = new QuartzJobCriteria();
        query.createCriteria().andJobGroupEqualTo(earlyWarningTaskDTO.getTaskGroup())
                .andJobKeyEqualTo(String.valueOf(earlyWarningTaskDTO.getRiskRuleId()));
        List<QuartzJob> qrtzJobDOList = quartzJobMapper.selectByExample(query);
        if (CollectionUtils.isNotEmpty(qrtzJobDOList)) {
            log.error("添加任务已经在数据库存在，参数为param ==>{}",earlyWarningTaskDTO);
            throw new RuntimeException("添加任务已经在数据库存在");
        }
        QuartzJob jobDO = new QuartzJob();
        jobDO.setJobGroup(earlyWarningTaskDTO.getTaskGroup());
        jobDO.setJobKey(String.valueOf(earlyWarningTaskDTO.getRiskRuleId()));
        jobDO.setJobStatus(earlyWarningTaskDTO.getJobStatus());
        jobDO.setJobCronExpress(earlyWarningTaskDTO.getCronExpression());
        jobDO.setJobDesc(earlyWarningTaskDTO.getTaskGroup() + jobDO.getJobKey());
        int cnt = quartzJobMapper.insertSelective(jobDO);
        if (cnt < 1) {
            log.error("添加qrtzJob表失败，参数为param ==>{}", earlyWarningTaskDTO.toString());
            throw new RuntimeException("添加失败");
        }
    }

}
