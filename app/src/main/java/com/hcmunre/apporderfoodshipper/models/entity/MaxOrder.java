package com.hcmunre.apporderfoodshipper.models.entity;

public class MaxOrder {
    private int maxRowNum;

    public MaxOrder(int maxRowNum) {
        this.maxRowNum = maxRowNum;
    }

    public int getMaxRowNum() {
        return maxRowNum;
    }

    public void setMaxRowNum(int maxRowNum) {
        this.maxRowNum = maxRowNum;
    }
}
