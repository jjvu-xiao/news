package com.pgl.xiao.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 图片文件的配置bean
 */
@Component
public class ImageFileSettings {

    // 文件的最大大小
    @Value("${upload.image.size}")
    private Long maxSize;

    // 文件的上传的图片路径
    @Value("${upload.image.path}")
    private String filePath;

    // 文件的上传URL
    @Value(("${upload.url}"))
    private String url;

    @Value("${upload.image.ext}")
    private String ext;

    // 允许的上传的文件后缀名
    private Set<String> exts;

    public ImageFileSettings() {
        exts = new HashSet<>();
    }

    public Long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Long maxSize) {
        this.maxSize = maxSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        String[] sz = ext.split(",");
        exts.addAll(Arrays.asList(sz));
        this.ext = ext;
    }

    public Set<String> getExts() {
        return exts;
    }

    public void setExts(Set<String> exts) {
        this.exts = exts;
    }
}
