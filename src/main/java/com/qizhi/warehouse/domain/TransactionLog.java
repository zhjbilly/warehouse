package com.qizhi.warehouse.domain;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionLog {
    private Integer id;

    private String actionType;

    private Integer actor;

    private Date operateDate;

    private String goods;

    private Integer amount;

    private String reason;

}