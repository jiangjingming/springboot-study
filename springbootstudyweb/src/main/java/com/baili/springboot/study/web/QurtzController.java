package com.baili.springboot.study.web;


import com.baili.springboot.core.quartz.ScheduleJob;
import com.baili.springboot.core.quartz.service.IQurtzService;
import com.baili.springboot.core.service.IEarlyWarningQuartzService;
import com.baili.springboot.study.common.domain.EarlyWarningTaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jiangjingming on 2017/6/22.
 */
@Slf4j
@Controller
@RequestMapping("/qurtz")
public class QurtzController {

    @Autowired
    private IQurtzService qurtzService;

    @Autowired
    private IEarlyWarningQuartzService IEarlyWarningQuartzService;

    /**
     * 初始化任务实例
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "init",method = RequestMethod.GET)
    public Object initQuartzData() {
        log.info("开始初始化任务");
        return qurtzService.initQuartzData();
    }


    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateQurtz(@RequestBody ScheduleJob job) {
        EarlyWarningTaskDTO earlyWarningTaskDTO = EarlyWarningTaskDTO.builder().taskGroup(job.getJobGroup()).riskRuleId(Long.valueOf(job.getJobName())).cronExpression(job.getCronExpression()).jobStatus(0).build();
        IEarlyWarningQuartzService.updateWarningTask(earlyWarningTaskDTO);
        return earlyWarningTaskDTO;
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addQurtz(@RequestBody ScheduleJob job) {
        EarlyWarningTaskDTO earlyWarningTaskDTO = EarlyWarningTaskDTO.builder().taskGroup(job.getJobGroup()).riskRuleId(Long.valueOf(job.getJobName())).cronExpression(job.getCronExpression()).jobStatus(99).build();
        IEarlyWarningQuartzService.addWarningTask(earlyWarningTaskDTO);
        return earlyWarningTaskDTO;
    }

    @ResponseBody
    @RequestMapping(value = "/enable",method = RequestMethod.POST)
    public Object enableQurtz(@RequestBody ScheduleJob job) {
        EarlyWarningTaskDTO earlyWarningTaskDTO = EarlyWarningTaskDTO.builder().taskGroup(job.getJobGroup()).riskRuleId(Long.valueOf(job.getJobName())).cronExpression(job.getCronExpression()).build();
        IEarlyWarningQuartzService.enabledWarningTask(earlyWarningTaskDTO);
        return earlyWarningTaskDTO;
    }

    @ResponseBody
    @RequestMapping(value = "/disable",method = RequestMethod.POST)
    public Object deleteQurtz(@RequestBody ScheduleJob job) {
        EarlyWarningTaskDTO earlyWarningTaskDTO = EarlyWarningTaskDTO.builder().taskGroup(job.getJobGroup()).riskRuleId(Long.valueOf(job.getJobName())).cronExpression(job.getCronExpression()).build();
        IEarlyWarningQuartzService.disabledWarningTask(earlyWarningTaskDTO);
        return earlyWarningTaskDTO;
    }

}
