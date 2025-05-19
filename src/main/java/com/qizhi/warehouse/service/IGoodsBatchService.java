package com.qizhi.warehouse.service;

import com.qizhi.warehouse.dto.*;

import java.util.List;

/**
 * 库存
 */
public interface IGoodsBatchService {

    /**
     * 入库
     * @param addGoodsBatch
     */
    void in(AddGoodsBatch addGoodsBatch);

    /**
     * 调拨
     * @param transferGoods
     */
    void transfer(TransferGoods transferGoods);

    /**
     * 查看库存
     * @param viewGoosBatch
     * @return
     */
    List<GoodsBatchDTO> view(ViewGoosBatch viewGoosBatch);

    /**
     * 调整库存
     * @param adjustGoodsBatch
     */
    void adjust(AdjustGoodsBatch adjustGoodsBatch);
}
