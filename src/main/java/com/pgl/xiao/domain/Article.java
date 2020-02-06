package com.pgl.xiao.domain;

import com.pgl.xiao.utils.BasicUtils;

import java.time.LocalDateTime;

public class Article {
    private Integer id;

    private String title;

    private String content;

    private Long clicks;

    private Long praise;

    private LocalDateTime releasedate;

    private LocalDateTime lastupdate;

    private String status;

    private String author;

    private Integer type;

    private String img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
        this.img = BasicUtils.getImageSrc(content);
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Long getPraise() {
        return praise;
    }

    public void setPraise(Long praise) {
        this.praise = praise;
    }

    public LocalDateTime getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(LocalDateTime releasedate) {
        this.releasedate = releasedate;
    }

    public LocalDateTime getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(LocalDateTime lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}