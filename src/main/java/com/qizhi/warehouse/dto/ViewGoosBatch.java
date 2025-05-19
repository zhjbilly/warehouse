package com.qizhi.warehouse.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ViewGoosBatch extends UserToken{

    private int warehouseId;

    private String searchType;

    private String searchValue;
}
