package com.baili.springboot.core.quartz.handler;


import com.baili.springboot.core.quartz.model.HandlerParam;
import com.baili.springboot.core.quartz.model.TaskGroupEnum;

/**
 * quartz任务抽象实现者
 * Created by jiangjingming on 2017/6/26.
 */
public abstract class Handler {
    
    /**
     * 持有后继的责任对象
     */
    protected Handler successor;
    /**
     * 示意处理请求的方法，虽然这个示意方法是没有传入参数的
     * 但实际是可以传入参数的，根据具体需要来选择是否传递参数
     */
    public abstract void handleRequest(HandlerParam handlerParam);

    public abstract TaskGroupEnum getTaskGroupEnum();

    public void runTaskHandler(HandlerParam handlerParam) {
        if (handlerParam.getTaskGroup().equals(getTaskGroupEnum())) {
            handleRequest(handlerParam);
        } else {
            if (getSuccessor() != null) {
                getSuccessor().runTaskHandler(handlerParam);
            }
        }
    }
    /**
     * 取值方法
     */
    public Handler getSuccessor() {
        return successor;
    }
    /**
     * 赋值方法，设置后继的责任对象
     */
    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }
    
}