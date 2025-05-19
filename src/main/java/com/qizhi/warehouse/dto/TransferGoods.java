package com.qizhi.warehouse.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class TransferGoods extends UserToken {

    private int sourceWare;
    private int targetWare;
    @NotBlank
    private String goodsName;
    private int batchId;
    @Min(1)
    private int amount;
    private int targetShelf;

}
