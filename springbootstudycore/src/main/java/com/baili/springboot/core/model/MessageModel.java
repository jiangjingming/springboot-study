package com.baili.springboot.core.model;

import lombok.Data;

import java.util.List;

/**
 * Created by jiangjingming on 2017/8/5.
 */
@Data
public class MessageModel extends BaseModel {
    private String msgtype;
    private String title;
    private String text;
    private List<String> atMobiles;
    private Boolean isAtAll;

}
