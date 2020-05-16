package com.pgl.xiao.core.ui;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:easyui分页
 * @author: XiaoHongBing
 * @date: 2019年4月6日
 */
public class EasyUIDataGridResult implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 数据库中总记录数
     */
    private Integer total;
    /**
     * 当前页数据
     */
    private List<?> rows;

    public EasyUIDataGridResult() {
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
