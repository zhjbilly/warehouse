package com.qizhi.warehouse.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@ToString
public class AddWarehouseDTO extends UserToken{

    /**
     * 仓库类型
     */
    private String warehouseType;

    @Min(0)
    @Max(100)
    private int locationX;

    @Min(0)
    @Max(100)
    private int locationY;
}
