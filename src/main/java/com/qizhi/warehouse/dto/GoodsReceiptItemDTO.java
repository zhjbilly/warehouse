package com.qizhi.warehouse.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class GoodsReceiptItemDTO {

    private Integer id;

    private Integer receiptId;

    @NotBlank
    private String name;

    @NotNull
    private Integer amount;

    @NotNull
    private BigDecimal price;
}
