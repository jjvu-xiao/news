package com.pgl.xiao.core.handler;

import java.io.InputStream;

public interface FileDownloadHandler {
    /**
     * 文件下载请求的回调处理
     * @author xiaohongbing
     * @Time 2020-03-04
     *
     */
    // 当请求成功的处理
    public void onFinish(InputStream in);

    // 当请求失败时的处理
    public void onError(Exception e);

}
