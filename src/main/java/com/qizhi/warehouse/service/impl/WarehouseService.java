package com.qizhi.warehouse.service.impl;

import com.qizhi.warehouse.constant.WarehouseType;
import com.qizhi.warehouse.dao.WarehouseMapper;
import com.qizhi.warehouse.domain.Warehouse;
import com.qizhi.warehouse.dto.WareHouseDTO;
import com.qizhi.warehouse.service.IWarehouseService;
import com.qizhi.warehouse.util.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class WarehouseService implements IWarehouseService {

    @Autowired
    private WarehouseMapper warehouseMapper;


    @Override
    public void registerWarehouse(String warehouseType, int locationX, int locationY) {
        //
        if(WarehouseType.CITY.name().equals(warehouseType)){
            // 城市仓
            Warehouse warehouse = warehouseMapper.selectCity();
            if(null != warehouse){
                throw new BizException("不能重复创建中心仓");
            }
        }
        List<Warehouse> warehouses = warehouseMapper.selectByLocation(locationX, locationY);
        if(!CollectionUtils.isEmpty(warehouses)){
            throw new BizException("仓库位置重复");
        }
        Warehouse domain = new Warehouse();
        domain.setWarehouseType(warehouseType);
        domain.setLocationX(locationX);
        domain.setLocationY(locationY);
        if(1 > warehouseMapper.insert(domain)){
            throw new BizException("仓库创建失败");
        }
    }

    @Override
    public void deleteWarehouse(int warehouseId, int userId) {
        // TODO
    }

    @Override
    public List<WareHouseDTO> listWarehoust() {
        // TODO
        return null;
    }


}
