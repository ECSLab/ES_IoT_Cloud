package com.wust.iot.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class PageDto<T> {

    @ApiModelProperty("当前页号")
    private int currentPageNumber;

    @ApiModelProperty("每一页的记录数")
    private int pageSize;

    @ApiModelProperty("总页数")
    private int totalPageNumber;

    @ApiModelProperty("当前页数据总量")
    private int currentPageDataCount;

    @ApiModelProperty("数据")
    private List<T> list;

    public PageDto() {
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPageNumber() {
        return totalPageNumber;
    }

    public void setTotalPageNumber(int totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
    }

    public int getCurrentPageDataCount() {
        return currentPageDataCount;
    }

    public void setCurrentPageDataCount(int currentPageDataCount) {
        this.currentPageDataCount = currentPageDataCount;
    }
}
