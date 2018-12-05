package com.yeta.pps.util;

import com.yeta.pps.vo.PageVo;

import java.util.List;

/**
 * @author YETA
 * @date 2018/12/02/19:01
 */
public class CommonResult<T> {

    private List<Title> title;

    private T items;

    private PageVo pageVo;

    public CommonResult() {
    }

    public CommonResult(List<Title> title, T items, PageVo pageVo) {
        this.title = title;
        this.items = items;
        this.pageVo = pageVo;
    }

    public List<Title> getTitle() {
        return title;
    }

    public void setTitle(List<Title> title) {
        this.title = title;
    }

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }

    public PageVo getPageVo() {
        return pageVo;
    }

    public void setPageVo(PageVo pageVo) {
        this.pageVo = pageVo;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "title=" + title +
                ", items=" + items +
                ", pageVo=" + pageVo +
                '}';
    }
}
