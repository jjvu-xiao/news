package com.pgl.xiao.domain;

import java.io.Serializable;

public class Catalog implements Serializable {

    private Integer id;

    private String name;

    private Integer status;

    private String url;

    private String descr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr == null ? null : descr.trim();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (this.getClass() == obj.getClass()) {
            Catalog temp = (Catalog) obj;
            return (temp.getStatus() == 1 && temp.getName().equals(this.name));
        }
        return false;
    }


    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", url='" + url + '\'' +
                ", descr='" + descr + '\'' +
                '}';
    }

}