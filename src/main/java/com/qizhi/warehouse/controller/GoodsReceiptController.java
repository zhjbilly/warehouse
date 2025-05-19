package com.qizhi.warehouse.controller;

import com.qizhi.warehouse.constant.CommonResult;
import com.qizhi.warehouse.dto.AddGoodsReceipt;
import com.qizhi.warehouse.service.IGoodsReceiptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * goodsReceipt
 */
@Slf4j
@RestController
@RequestMapping("/goodsReceipt")
public class GoodsReceiptController {

    @Autowired
    private IGoodsReceiptService goodsReceiptService;

    /**
     * 导入进货单
     */
    @PostMapping("/add")
    public CommonResult<Void> add(@Validated @RequestBody AddGoodsReceipt addGoodsReceipt) {
        goodsReceiptService.importGoodsReceipt(addGoodsReceipt);
        return CommonResult.success();
    }

    /**
     * 增加供应商
     */
    @PostMapping("/addSuppler")
    public CommonResult<Void> addSuppler(@NotBlank String token, @NotBlank int receiptId, @NotBlank @Max(20) String suppler) {
        goodsReceiptService.addSuppler(token, receiptId, suppler);
        return CommonResult.success();
    }

    /**
     * 关闭进货单
     *
     * @param token
     * @return
     */
    @PostMapping("/close")
    public CommonResult<Void> close(@NotBlank String token, @NotBlank int receiptId) {
        goodsReceiptService.closeReceipt(token, receiptId);
        return CommonResult.success();
    }


}
