package com.qizhi.warehouse.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsBatchQueryDTO implements Serializable {

    private String goodsName;

    private int amount;
}
