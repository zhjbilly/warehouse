package com.qizhi.warehouse.facade;

import com.qizhi.warehouse.dto.WareHouseDTO;

import java.util.List;

/**
 * 库存查询facade
 */
public interface WarehouseFacade {

    List<WareHouseDTO> viewAllWareHouse();
}
