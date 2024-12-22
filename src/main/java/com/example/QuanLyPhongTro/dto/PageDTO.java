package com.example.QuanLyPhongTro.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageDTO<T> {
    int pageTotal;
    int pageNum;
    List<T> adData;

    public PageDTO(Page<T> page ) {
        pageTotal=page.getTotalPages();
        pageNum=page.getNumber();
        adData=page.getContent();
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setAdData(List<T> adData) {
        this.adData = adData;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public int getPageNum() {
        return pageNum;
    }

    public List<T> getAdData() {
        return adData;
    }
}