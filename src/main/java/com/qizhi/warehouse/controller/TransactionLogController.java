package com.qizhi.warehouse.controller;

import com.qizhi.warehouse.constant.CommonResult;
import com.qizhi.warehouse.dto.TransactionLogDTO;
import com.qizhi.warehouse.service.ITransactionLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * transactionLog
 */
@Slf4j
@RestController
@RequestMapping("/transactionLog")
public class TransactionLogController {

    @Autowired
    private ITransactionLogService transactionLogService;

    /**
     * 查询时间线
     */
    @PostMapping("/view")
    public CommonResult<List<TransactionLogDTO>> view(@NotBlank String token, @NotBlank String goodsName) {
        return CommonResult.success(transactionLogService.viewGoodsTimeLine(token, goodsName));
    }

}
