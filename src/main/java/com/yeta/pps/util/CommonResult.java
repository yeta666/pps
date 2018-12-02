package com.yeta.pps.util;

import com.yeta.pps.vo.PageVo;

/**
 * @author YETA
 * @date 2018/12/02/19:01
 */
public class CommonResult {

    private Object title;

    private Object items;

    private PageVo pageVo;

    public CommonResult() {
    }

    public CommonResult(Object title, Object items, PageVo pageVo) {
        this.title = title;
        this.items = items;
        this.pageVo = pageVo;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getItems() {
        return items;
    }

    public void setItems(Object items) {
        this.items = items;
    }

    public PageVo getPageVo() {
        return pageVo;
    }

    public void setPageVo(PageVo pageVo) {
        this.pageVo = pageVo;
    }
}
