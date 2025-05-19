package com.qizhi.warehouse.domain;

import lombok.Data;

import java.util.Date;

@Data
public class GoodsReceipt {
    private Integer receiptId;

    private int isClosed;

    private Date closeDateTime;

    private String supplier;

}