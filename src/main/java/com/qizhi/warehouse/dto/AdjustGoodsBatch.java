package com.qizhi.warehouse.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;

@Data
@ToString
public class AdjustGoodsBatch extends UserToken{

    private int batchId;

    @Min(0)
    private int newAmount;

    private String reason;
}
