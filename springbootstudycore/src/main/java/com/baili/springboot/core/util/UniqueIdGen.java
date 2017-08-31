package com.baili.springboot.core.util;

/**
 * 全局唯一ID生成器，生成21位字符串，不支持高并发场景
 * Created by baili on 17/3/4 16:11.
 */
public class UniqueIdGen {

    public static String genUniqueId() {
        return DateUtil.getNowTime("yyyyMMddHHmmssSSS") + RandomCharGen.getRandChars();
    }

    public static String genUniqueId(String bizCode) {
        return new StringBuilder(bizCode).append("_").append(genUniqueId()).toString();
    }

}
