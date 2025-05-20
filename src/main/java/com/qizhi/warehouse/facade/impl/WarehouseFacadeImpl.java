package com.qizhi.warehouse.facade.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qizhi.warehouse.dto.WareHouseDTO;
import com.qizhi.warehouse.facade.WarehouseFacade;
import com.qizhi.warehouse.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class WarehouseFacadeImpl implements WarehouseFacade {

    @Autowired
    private IWarehouseService warehouseService;

    @Override
    public List<WareHouseDTO> viewAllWareHouse() {
        return warehouseService.listWarehouse();
    }
}
