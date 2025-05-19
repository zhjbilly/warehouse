package com.qizhi.warehouse.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class GoodsBatchDTO {

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
