package com.qizhi.warehouse.dto;

import lombok.Data;

@Data
public class AddTransactionLogDTO {

    private int userId;

    private String actorType;

    private String goodsName;

    private int amount;

    private String reason;

}
