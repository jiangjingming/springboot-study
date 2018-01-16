package com.baili.springboot.core.util;

import com.baili.springboot.core.annotation.ValidMessage;
import com.baili.springboot.study.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

/**
 * 验证参数工具类
 * Created by jiangjingming on 2018/1/6.
 */
@Slf4j
public final class ValidParamsUtil {

    private static final int LIMIT = 5;
    private static int i = 0;
    private static final String ERROR_CODE = "3";

    /**
     * 验证入参是否合法(目前只支持简单的null,""，空集合）
     * @param o
     * @return
     */
    public static Result validParams(Object o) {
        return verifyRecursion(o,o.getClass());
    }
    /**
     * 集合不支持
     * 验证入参是否合法(目前只支持简单的null,""，空集合）
     * @param o
     * @return
     */
    public static Result verifyRecursion(Object o, Class c) {
        // 获取类中的所有定义字段
        Result Result = new Result();
        if (o instanceof Collection) {
            for (Object item: ((Collection) o)) {
                return verifyRecursion(item,item.getClass());
            }
        } else {
            Field[] fields = c.getDeclaredFields();
            // 循环遍历字段，获取字段对应的属性值
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    ValidMessage validMessage = field.getAnnotation(ValidMessage.class);
                    if (Objects.isNull(validMessage) || !validMessage.isValid()) {
                        continue;
                    }
                    //get方法获取属性值
                    Object object = field.get(o);
                    if (Objects.isNull(object)) {
                        String messageError = field.getName().concat("字段,").concat(validMessage.message());
                        return Result.create(ERROR_CODE, messageError);
                    }
                    if (object instanceof String) {
                        if (StringUtils.isEmpty(object)) {
                            String messageError = field.getName().concat(validMessage.message());
                            return Result.create(ERROR_CODE, messageError);
                        }
                    }
                    if (object instanceof Collection) {
                        if (CollectionUtils.isEmpty((Collection) object)) {
                            String messageError = field.getName().concat(validMessage.message());
                            return Result.create(ERROR_CODE, messageError);
                        }
                        Result = verifyRecursion(object,object.getClass());
                        if (!Result.isSuccess()) {
                            return Result;
                        }
                    }
                } catch (Exception e) {
                    log.error("验证参数出现异常，e = [{}]", e);
                    return Result.create(ERROR_CODE, "验证参数系统错误");
                }
            }
            if (c.getSuperclass() == Object.class) {
                return Result;
            }
            //防止潜在的数据类型导致bug
            if (i > LIMIT) {
                i++;
                return Result.create(ERROR_CODE, "超出正常情况，可能出现异常");
            }
        }
        return verifyRecursion(o, c.getSuperclass());
    }
}
