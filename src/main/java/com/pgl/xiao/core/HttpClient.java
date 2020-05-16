package com.pgl.xiao.core;

import com.pgl.xiao.core.handler.FileDownloadHandler;
import com.pgl.xiao.core.handler.HttpCallbackHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * @Time 2020-03-07
 * @Auhor xiaohongbing
 * @Desc JDK封装的网络请求客户端
 */
public class HttpClient {

    // 默认字符编码
    private String defaultCharset = "UTF-8";

    // 最大连接时间
    private int maxConnectionTime = 15000;

    // 最大读取数据时间
    private int maxReadTime = 60000;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 发送GET请求
     * @param address   请求地址
     * @param handler   请求之后的回调处理
     */
    public void doGet(final String address, HttpCallbackHandler handler) {
        HttpURLConnection con = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder callback = new StringBuilder();   // 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(address);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            con = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            con.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间
            con.setConnectTimeout(maxConnectionTime);
            // 设置读取远程返回的数据时间
            con.setReadTimeout(maxReadTime);
            // 发送请求
            con.connect();
            logger.debug("开始发送GET请求到" + Constants.CHAR_SEPARATOR + address);
            // 目标成功响应
            if (con.getResponseCode() == 200) {
                // 通过connection连接，获取输入流
                is = con.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, Charset.forName(defaultCharset)));
                // 临时存放数据
                String temp;
                while((temp = br.readLine()) != null) {
                    callback.append(temp);
                }
                handler.onFinish(callback.toString());
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            handler.onError(e);
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 关闭远程连接
            if (null != con)
                con.disconnect();
        }
    }

    /**
     * 发送POST请求
     * @param address   地址
     * @param param     参数
     * @param handler   回调处理
     */
    public void doPost(final String address, final String param, final HttpCallbackHandler handler) {
            HttpURLConnection connection = null;
            InputStream is = null;
            OutputStream os = null;
            BufferedReader br = null;
            String result;
            try {
                URL url = new URL(address);
                // 通过远程url连接对象打开连接
                connection = (HttpURLConnection) url.openConnection();
                // 设置连接请求方式
                connection.setRequestMethod("POST");
                // 设置连接主机服务器超时时间：15000毫秒
                connection.setConnectTimeout(maxConnectionTime);
                // 设置读取主机服务器返回数据超时时间：60000毫秒
                connection.setReadTimeout(maxReadTime);
                // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
                connection.setDoOutput(true);
                // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
                connection.setDoInput(true);
                // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
                connection.setRequestProperty("Content-Type", "application/json");
                // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
                //connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
                // 通过连接对象获取一个输出流
                os = connection.getOutputStream();
                // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
                os.write(param.getBytes());
                // 通过连接对象获取一个输入流，向远程读取
                if (connection.getResponseCode() == 200) {
                    is = connection.getInputStream();
                    // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                    br = new BufferedReader(new InputStreamReader(is, defaultCharset));
                    StringBuilder sbf = new StringBuilder();
                    String temp;
                    // 循环遍历一行一行读取数据
                    while ((temp = br.readLine()) != null) {
                        sbf.append(temp);
                    }
                    result = sbf.toString();
                    handler.onFinish(result);
                }
            } catch (IOException e) {
                e.printStackTrace();
                handler.onError(e);
            } finally {
                // 关闭资源
                if (null != br) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != os) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != is) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 断开与远程地址url的连接
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

    /**
     * 文件下载功能
     * @param address   地址URL
     * @param handler   回调处理接口
     */
    public void downloadFile(String address, FileDownloadHandler handler) {
        InputStream in = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(address);
            URLConnection urlConnection = url.openConnection();
            httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charsert", defaultCharset);
            httpURLConnection.setConnectTimeout(maxConnectionTime);
            httpURLConnection.setReadTimeout(maxReadTime);
            httpURLConnection.connect();
            in = httpURLConnection.getInputStream();
            handler.onFinish(in);
        } catch (IOException e) {
            e.printStackTrace();
            handler.onError(e);
        } finally {
            if (null != in) {
                try {
                    in.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpURLConnection) {
                httpURLConnection.disconnect();
            }
        }
    }
}
