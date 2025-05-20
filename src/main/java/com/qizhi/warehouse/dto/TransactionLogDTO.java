package com.qizhi.warehouse.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class TransactionLogDTO {

    private Integer id;

    private String actionType;

    private Integer actor;

    private Date operateDate;

    private String goods;

    private Integer amount;

    private String reason;

}
