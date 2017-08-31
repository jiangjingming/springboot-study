package com.baili.springboot.core.util;

/**
 * 随机字符生成器，包含大小写字符和数字
 * Created by baili on 17/3/4 16:29.
 */
public class RandomCharGen {

    /**
     * 生成4位指定长度的随机字符串，内容包含数字和大小写字母
     * @return
     */
    public static String getRandChars() {
        return getRandChars(null);
    }

    /**
     * 生成指定长度的随机字符串，内容包含数字和大小写字母，长度限制1到32，默认返回4位长度随机字符串
     * @param length
     * @return
     */
    public static String getRandChars(Integer length) {
        if(null == length || length < 0 || length > 32){
            length = 4;
        }
        String a = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] rands = new char[length];
        for (int i = 0; i < rands.length; i++) {
            int rand = (int) (Math.random() * a.length());
            rands[i] = a.charAt(rand);
        }
        return new String(rands);
    }
}
