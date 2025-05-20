package com.qizhi.warehouse.facade;

import com.qizhi.warehouse.dto.GoodsBatchQueryDTO;

import java.util.List;

/**
 * 扣减库存
 */
public interface GoodsBatchFacade {

    /**
     * 查询满足订单商品的最近的仓库，不存在返回null
     *
     * @param needs
     * @param locationX
     * @param locationY
     * @return
     */
    Integer queryWarehouse(List<GoodsBatchQueryDTO> needs, int locationX, int locationY);

    /**
     * 锁定库存
     * @param wareHouseId
     * @param query
     */
    boolean lock(Integer wareHouseId, List<GoodsBatchQueryDTO> query);

    /**
     * 扣减库存
     * @param wareHouseId
     * @param query
     */
    boolean dudect(Integer wareHouseId, List<GoodsBatchQueryDTO> query);
}
