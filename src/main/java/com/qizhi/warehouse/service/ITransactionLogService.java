package com.qizhi.warehouse.service;

import com.qizhi.warehouse.dto.AddTransactionLogDTO;
import com.qizhi.warehouse.dto.TransactionLogDTO;

import java.util.List;

/**
 * 时间线
 */
public interface ITransactionLogService {

    /**
     * 添加时间线
     * @param addTransactionLogDTO
     */
    void addTransactionLog(AddTransactionLogDTO addTransactionLogDTO);

    /**
     * 货物时间线
     * @param goodsName
     * @return
     */
    List<TransactionLogDTO> viewGoodsTimeLine(String token, String goodsName);
}
