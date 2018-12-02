package com.yeta.pps.vo;

/**
 * 分页
 * @author YETA
 * @date 2018/12/02/18:22
 */
public class PageVo {

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页显示条数
     */
    private Integer pageSize;

    /**
     * 总共页数
     */
    private Integer totalPage;

    public PageVo() {
    }

    public PageVo(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "PageVo{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                '}';
    }
}
