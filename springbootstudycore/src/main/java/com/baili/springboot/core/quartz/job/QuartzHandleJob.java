package com.baili.springboot.core.quartz.job;


import com.alibaba.druid.util.StringUtils;
import com.baili.dao.QuartzJobMapper;
import com.baili.entity.QuartzJob;
import com.baili.entity.QuartzJobCriteria;
import com.baili.springboot.core.quartz.handler.*;
import com.baili.springboot.core.quartz.model.HandlerParam;
import com.baili.springboot.core.quartz.model.TaskGroupEnum;
import com.baili.springboot.core.util.JsonUtils;
import com.baili.springboot.study.common.util.SpringUtil;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * quartz任务执行器
 * Created by jiangjingming on 17/1/10.
 */
@Slf4j
public class QuartzHandleJob implements Job {
    /**
     * 注册handler
     */
    private static final List<Handler> handlers = Lists.newArrayList(
            SpringUtil.getBean(FullMenuHandler.class),
            SpringUtil.getBean(DailyRemindHandler.class),
            SpringUtil.getBean(ToDayMenuHandler.class));

    /**
     * 责任链
     */
    static {
        for (int i= 0; i < handlers.size()-1; i++) {
            handlers.get(i).setSuccessor(handlers.get(i + 1));
        }
    }

    /**
     * Quartz任务回调方法
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();

        HandlerParam param = new HandlerParam();
        param.setTaskGroup(TaskGroupEnum.of(jobKey.getGroup()));
        param.setKey(jobKey.getName());

        log.info("开始了定时任务==========================>>");
        log.info("开始时间是date:{}",new Date());
        log.info("定时任务jobkey参数为==>name:{},group:{}", jobKey.getName(), jobKey.getGroup());

        QuartzJobCriteria query = new QuartzJobCriteria();
        query.createCriteria().andJobGroupEqualTo(param.getTaskGroup().getCode()).andJobKeyEqualTo(param.getKey());
        List<QuartzJob> qrtzJobDOList = SpringUtil.getBean(QuartzJobMapper.class).selectByExample(query);
        if (CollectionUtils.isNotEmpty(qrtzJobDOList) && qrtzJobDOList.size() == 1) {
            if (!StringUtils.isEmpty(qrtzJobDOList.get(0).getJobParams())) {
                param.setParams(JsonUtils.toObject(qrtzJobDOList.get(0).getJobParams(), new TypeToken<Map<String, Object>>() {
                }.getType()));
            }
        }
        if (CollectionUtils.isEmpty(handlers)) {
            return;
        }

        //责任链调用
        handlers.get(0).runTaskHandler(param);

    }
}
