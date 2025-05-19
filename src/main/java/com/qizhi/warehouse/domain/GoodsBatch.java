package com.qizhi.warehouse.domain;

import lombok.Data;

import java.util.Date;

@Data
public class GoodsBatch {
    private Integer id;

    private String goodsName;

    private Integer warehouseId;

    private Integer shelfPosition;

    private Integer inAmount;

    private Integer outAmount;

    private Integer lockedAmount;

    private Date inwardDate;

    private Integer receiptId;

}