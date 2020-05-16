package com.pgl.xiao.utils;

import com.pgl.xiao.core.Constants;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Descr 基础工具
 */
public class BasicUtils {

    /**
     * 从富文本html标签中将第一张图片的src属性值提取出来
     * @param htmlStr   一段富文本标签
     * @return  里面的第一张图片的src值
     */
    public static String getImageSrc(String htmlStr) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                String temp = m.group(1);
                if (!StringUtils.isEmpty(temp))
                    return temp;
            }
        }
        return null;
    }

    /**
     * 将'yyyy-MM-dd'风格的字符串解析为日期时间
     * @param szDateTime    'yyyy-MM-dd'风格的时间字符串
     * @return
     */
    public static LocalDateTime parseDateTime(String szDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(szDateTime, formatter);
    }

    /**
     * 为请求的参数字符串赋予默认值
     *
     * @param val
     * @param defaultVal
     * @return  当第一个参数为Null时，将返回第二个参数的值，如果不为Null，则返回值为第一个参数
     */
    public static String setObjectNotNull(String val, String defaultVal) {
        return Objects.isNull(val) ? val : defaultVal;
    }

    /**
     * 生成AOP的tag
     */
    public static String getLogTag(JoinPoint joinPoint) {
        StringBuilder tag = new StringBuilder();
        tag.append(joinPoint.getSignature().getDeclaringTypeName());
        tag.append(".");
        tag.append(joinPoint.getSignature().getName());
        tag.append(Constants.CHAR_SEPARATOR);
        return tag.toString();
    }

}
