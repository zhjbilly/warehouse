package com.qizhi.warehouse.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddGoodsBatch extends UserToken{

    @NotNull
    private int receiptId;

    @NotBlank
    private String goodsName;

    private int shelfPosition;

    @NotNull
    @Min(1)
    private int amount;
}
