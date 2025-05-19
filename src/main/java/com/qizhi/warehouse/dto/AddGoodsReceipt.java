package com.qizhi.warehouse.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddGoodsReceipt extends UserToken{

    @NotNull
    private GoodsReceiptDTO goodsReceipt;

    @NotEmpty
    private List<GoodsReceiptItemDTO> goodsReceiptItems;
}
