package com.qizhi.warehouse.service;


import com.qizhi.warehouse.dto.WareHouseDTO;

import java.util.List;

public interface IWarehouseService {

    /**
     * 仓库注册
     *
     * @param warehouseType
     * @param locationX
     * @param locationY
     */
    void registerWarehouse(String warehouseType, int locationX, int locationY);

    /**
     * 删除仓库
     * @param userId
     * @param warehouseId
     */
    void deleteWarehouse(int warehouseId, int userId);

    /**
     * 仓库列表
     * @return
     */
    List<WareHouseDTO> listWarehoust();

}
