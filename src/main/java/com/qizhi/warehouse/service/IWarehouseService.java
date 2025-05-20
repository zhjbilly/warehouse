package com.qizhi.warehouse.service;


import com.qizhi.warehouse.dto.WareHouseDTO;

import java.util.List;

public interface IWarehouseService {

    /**
     * 仓库注册
     *
     * @param token
     * @param warehouseType
     * @param locationX
     * @param locationY
     */
    void registerWarehouse(String token, String warehouseType, int locationX, int locationY);

    /**
     * 删除仓库
     * @param token
     * @param warehouseId
     */
    void deleteWarehouse(String token, int warehouseId);

    /**
     * 仓库列表
     * @return
     */
    List<WareHouseDTO> listWarehouse(String token);

    /**
     * 仓库列表（无鉴权）
     * @return
     */
    List<WareHouseDTO> listWarehouse();

}
