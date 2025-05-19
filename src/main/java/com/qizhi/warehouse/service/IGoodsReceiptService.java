package com.qizhi.warehouse.service;

import com.qizhi.warehouse.dto.AddGoodsReceipt;

/**
 * 进货单
 */
public interface IGoodsReceiptService {

    /**
     * 导入进货单
     */
    void importGoodsReceipt(AddGoodsReceipt addGoodsReceipt);


    /**
     * 添加供货商
     * @param goodsReceiptId
     * @param suppler
     */
    void addSuppler(String token, int goodsReceiptId, String suppler);

    /**
     * 关闭进货单
     * @param token
     * @param goodsReceiptId
     */
    void closeReceipt(String token, int goodsReceiptId);
}
