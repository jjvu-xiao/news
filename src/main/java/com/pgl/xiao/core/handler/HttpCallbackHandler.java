package com.pgl.xiao.core.handler;
/**
 * 发送HTTP请求的回调处理
 * @author xiaohongbing
 * @Time 2019-12-09
 *
 */
public interface HttpCallbackHandler {
    // 当请求成功的处理
    void onFinish(String response);

    // 当请求失败时的处理
    void onError(Exception e);
}
