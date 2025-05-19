package com.qizhi.warehouse.dao;

import com.qizhi.warehouse.domain.GoodsReceipt;

public interface GoodsReceiptMapper {
    int deleteByPrimaryKey(Integer receiptId);

    int insert(GoodsReceipt record);

    GoodsReceipt selectByPrimaryKey(Integer receiptId);

    int updateByPrimaryKey(GoodsReceipt record);

    /**
     * 未关闭的进货单
     * @return
     */
    int countNotClosed();
}