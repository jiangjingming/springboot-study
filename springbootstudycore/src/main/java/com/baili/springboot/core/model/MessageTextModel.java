package com.baili.springboot.core.model;

import lombok.Data;

import java.util.List;

/**
 * Created by jiangjingming on 2017/8/5.
 */
@Data
public class MessageTextModel extends BaseModel {
    /**
     * msgtype : text
     * text : {"content":"我就是我, 是不一样的烟火"}
     * at : {"atMobiles":["156xxxx8827","189xxxx8325"],"isAtAll":false}
     */

    private String msgtype = "text";
    private Text text;
    private At at;
    @Data
    public static class Text {
        /**
         * content : 我就是我, 是不一样的烟火
         */

        private String content;

    }

    @Data
    public static class At {
        /**
         * atMobiles : ["156xxxx8827","189xxxx8325"]
         * isAtAll : false
         */

        private boolean isAtAll;
        private List<String> atMobiles;

    }

}
