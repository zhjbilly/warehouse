package com.qizhi.warehouse.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class WareHouseDTO implements Serializable {
    private Integer warehouseId;

    private String warehouseType;

    private Integer locationX;

    private Integer locationY;
}
