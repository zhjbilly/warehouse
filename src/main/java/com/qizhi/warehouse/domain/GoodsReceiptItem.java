package com.qizhi.warehouse.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsReceiptItem {
    private Integer id;

    private Integer receiptId;

    private String name;

    private Integer amount;

    private BigDecimal price;

}