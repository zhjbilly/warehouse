package com.qizhi.warehouse.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GoodsReceiptDTO {
    private Integer receiptId;

    private int isClosed;

    private Date closeDateTime;

    private String supplier;
}
