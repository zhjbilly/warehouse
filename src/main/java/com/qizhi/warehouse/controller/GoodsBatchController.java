package com.qizhi.warehouse.controller;


import com.qizhi.warehouse.constant.CommonResult;
import com.qizhi.warehouse.dto.*;
import com.qizhi.warehouse.service.IGoodsBatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * goodsBatch
 */
@Slf4j
@RestController
@RequestMapping("/goodsBatch")
public class GoodsBatchController {

    @Autowired
    private IGoodsBatchService goodsBatchService;

    /**
     * 入库
     */
    @PostMapping("/in")
    public CommonResult<Void> in(@Validated @RequestBody AddGoodsBatch addGoodsBatch) {
        goodsBatchService.in(addGoodsBatch);
        return CommonResult.success();
    }

    /**
     * 调拨
     */
    @PostMapping("/transfer")
    public CommonResult<Void> transfer(@Validated @RequestBody TransferGoods transferGoods) {
        goodsBatchService.transfer(transferGoods);
        return CommonResult.success();
    }

    /**
     * 查看库存
     *
     * @return
     */
    @PostMapping("/view")
    public CommonResult<List<GoodsBatchDTO>> view(@Validated @RequestBody ViewGoosBatch viewGoosBatch) {
        return CommonResult.success(goodsBatchService.view(viewGoosBatch));
    }

    /**
     * 盘点
     */
    @PostMapping("/adjust")
    public CommonResult<Void> adjust(@Validated @RequestBody AdjustGoodsBatch adjustGoodsBatch) {
        goodsBatchService.adjust(adjustGoodsBatch);
        return CommonResult.success();
    }


}
