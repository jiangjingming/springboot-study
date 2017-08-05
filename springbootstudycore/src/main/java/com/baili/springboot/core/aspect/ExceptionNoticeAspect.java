package com.baili.springboot.core.aspect;


import com.alibaba.fastjson.JSON;
import com.baili.springboot.core.annotation.ExceptionNotice;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 异常通知切面服务
 * Created by jiangjingming on 2017/6/28.
 */
@Aspect
@Component
@Slf4j
public class ExceptionNoticeAspect {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.baili.springboot.core.annotation.ExceptionNotice)")
    public void exceptionNoticeAspect() {
    }

    @AfterThrowing(pointcut = "exceptionNoticeAspect()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("-------------------******异步线程开始************-------------------");

        //异常通知控制次数
        if (!redisCount(joinPoint)) {
            return;
        }
        executorService.execute(() -> {
            log.error("异常开始捕捉=============================================");
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            Object[] args = joinPoint.getArgs();
            ExceptionNotice exceptionNotice = method.getAnnotation(ExceptionNotice.class);
            log.info("自定义注解的值：" + exceptionNotice.noticeNickName() + exceptionNotice.noticeRemarks() + exceptionNotice.sendWayType());


            // 获取用户请求方法的参数并序列化为JSON格式字符串
            String params = "";
            if (Objects.nonNull(args)) {
                params = JSON.toJSONString(args);
            }
            log.info("请求方法:" + (joinPoint.getTarget().getClass().getName() + "."
                    + joinPoint.getSignature().getName() + "()"));
            System.out.println();
            log.info("请求参数：{}" + params);

            log.error("-------------------afterThrowing.handler.start-------------------");
            log.error("异常名称：" + e.getClass().toString());
            log.error("异常栈信息:" + e);

            StringBuffer bufferErrorMsg = new StringBuffer();
            bufferErrorMsg
                    .append("异常自定义关键字：")
                    .append(exceptionNotice.noticeRemarks()).append(",").append("\n")
                    .append("请求方法：")
                    .append((joinPoint.getTarget().getClass().getName() + "."+ joinPoint.getSignature().getName() + "(),")).append("\n")
                    .append("请求参数：")
                    .append(params).append(",").append("\n")
                    .append("异常名称：")
                    .append(e.getClass().toString()).append(",").append("\n")
                    .append("异常栈信息：")
                    .append(e).append("\n");
            log.error(bufferErrorMsg.toString());
        });
        log.error("-------------------afterThrowing.handler.end-------------------");

    }

    /**
     * 同一方法异常通知次数控制
     * @param joinPoint
     * @return
     */
    private boolean redisCount(JoinPoint joinPoint) {
        String redisKey = String.valueOf((joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName()).hashCode());
        BoundValueOperations<String, Integer> operations = redisTemplate.boundValueOps(redisKey);
        redisTemplate.expire(redisKey, 5, TimeUnit.MINUTES);
        operations.increment(1);
        if (operations.get() > 20) {
            log.error("同一个请求5分钟之内异常超过20次,不再通知了");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
