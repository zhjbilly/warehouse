package com.qizhi.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qizhi.user.constant.UserType;
import com.qizhi.user.dto.UserDTO;
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
import com.qizhi.user.facade.UserFacade;

import java.util.List;

@Slf4j
@Service
public class WarehouseService implements IWarehouseService {

    @Reference()
    private UserFacade userFacade;

    @Autowired
    private WarehouseMapper warehouseMapper;


    @Override
    public void registerWarehouse(String token, String warehouseType, int locationX, int locationY) {
        // 用户校验
        UserDTO userDTO = null;
        if(null == token || null == (userDTO = userFacade.validToken(token)) || !UserType.STAFF.name().equals(userDTO.getUserType())){
            log.warn("token校验失败,token = {}, user = {}", token, userDTO);
            throw new BizException("用户未登陆或非staff");
        }
        if(!WarehouseType.CITY.name().equals(warehouseType) && ! WarehouseType.FRONT.name().equals(warehouseType)){
            throw new BizException("不支持的仓库类型");
        }
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
