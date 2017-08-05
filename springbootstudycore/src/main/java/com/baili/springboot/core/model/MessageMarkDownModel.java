package com.baili.springboot.core.model;

import lombok.Data;

import java.util.List;

/**
 * Created by jiangjingming on 2017/8/5.
 */
@Data
public class MessageMarkDownModel extends BaseModel {

    /**
     * msgtype : markdown
     * markdown : {"title":"杭州天气","text":"#### 杭州天气"}
     * at : {"atMobiles":["13599542030"],"isAtAll":false}
     */

    private String msgtype;
    private Markdown markdown;
    private At at;

    @Data
    public static class Markdown {
        /**
         * title : 杭州天气
         * text : #### 杭州天气
         */

        private String title;
        private String text;

    }

    @Data
    public static class At {
        /**
         * atMobiles : ["13599542030"]
         * isAtAll : false
         */

        private boolean isAtAll;
        private List<String> atMobiles;

    }
}
