package com.baili.springboot.core.aspect;

import com.baili.springboot.core.annotation.LockRedis;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * aop切面
 * Created by jiangjingming on 2018/1/12.
 */
@Slf4j
@Aspect
@Component
@ConditionalOnBean(JedisPool.class)
public class LockRedisService {

    @Autowired
    private JedisPool jedisPool;

    @Pointcut("@annotation(com.mhc.lock.annotion.LockRedis)")
    public void lockRedis() {

    }

    @Around(value = "lockRedis()")
    public void isGetLockRedis(ProceedingJoinPoint joinPoint) throws InterruptedException {
        Jedis jedis = jedisPool.getResource();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LockRedis lockRedis = method.getAnnotation(LockRedis.class);
        String redisKey = joinPoint.getTarget().getClass().getName().concat(".").concat(joinPoint.getSignature().getName());
        String redisValue = String.valueOf(System.currentTimeMillis());
        Long cxt = jedis.setnx(redisKey, redisValue);
        if (1L == cxt) {
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                log.error("出错异常，throwable = [{}]", throwable);
            } finally {
                manageLockRedis(lockRedis, jedis, redisKey, redisValue);
            }
        }
        if (0L == cxt) {
            String lockRedisTime = jedis.get(redisKey);
            if (StringUtils.isEmpty(lockRedisTime)) {
                managePolling(lockRedis, jedis, joinPoint);
                return;
            }
            Long lockTime = Long.valueOf(lockRedisTime);
            Long differSecond = (System.currentTimeMillis() - lockTime) / 1000;
            Long expireSecond = lockRedis.expireTime();
            if (differSecond < expireSecond) {
                managePolling(lockRedis, jedis, joinPoint);
                return;
            }
            if (differSecond >= expireSecond) {
                String oldTime = jedis.get(redisKey);
                String mayBeOldTime = jedis.getSet(redisKey,String.valueOf(System.currentTimeMillis()));
                if (Objects.equals(oldTime, mayBeOldTime)) {
                    jedis.del(redisKey);
                    String newRedisValue = String.valueOf(System.currentTimeMillis());
                    cxt = jedis.setnx(redisKey,newRedisValue);
                    if (1 != cxt) {
                        log.error("正常不会出现的情况");
                        return;
                    }
                    try {
                        joinPoint.proceed();
                    } catch (Throwable throwable) {
                        log.error("出错异常，throwable = [{}]", throwable);
                    } finally {
                        manageLockRedis(lockRedis, jedis, redisKey, newRedisValue);
                    }
                }
            }
        }
    }

    /**
     * 处理是否需要轮询
     * @param lockRedis
     * @param jedis
     * @param joinPoint
     */
    private void managePolling(LockRedis lockRedis, Jedis jedis, ProceedingJoinPoint joinPoint) {
        //是否轮询
        boolean flag = lockRedis.isPolling();
        if (!flag) {
            jedis.close();
            return;
        } else {
            int pollingIntervalTime = lockRedis.pollingIntervalTime();
            try {
                Thread.sleep(1000L * pollingIntervalTime);
                log.warn("Thread.sleep(),pollingIntervalTime==[{}]", pollingIntervalTime);
                isGetLockRedis(joinPoint);
            } catch (InterruptedException e) {
                log.error("处理是否需要轮询出现异常e = [{}]", e);
            } finally {
                jedis.close();
            }
        }
    }


    /**
     * 释放锁资源及设置过期时间
     * @param lockRedis
     * @param jedis
     * @param redisKey
     */
    private void manageLockRedis(LockRedis lockRedis, Jedis jedis, String redisKey, String redisValue) {
        boolean flag = lockRedis.isDelayReleaseLock();
        String value = jedis.get(redisKey);
        if (!flag) {
            //数据值相同进行删除
            if (Objects.equals(redisValue,value)) {
                jedis.del(redisKey);
            }
        } else {
            if (Objects.equals(redisValue,value)) {
                jedis.expire(redisKey, lockRedis.leaseTime());
            }
        }
        jedis.close();
    }
}

