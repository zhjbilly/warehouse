package com.qizhi.warehouse.facade.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qizhi.warehouse.dao.GoodsBatchMapper;
import com.qizhi.warehouse.domain.GoodsBatch;
import com.qizhi.warehouse.domain.Warehouse;
import com.qizhi.warehouse.dto.GoodsBatchQueryDTO;
import com.qizhi.warehouse.dto.WareHouseDTO;
import com.qizhi.warehouse.facade.GoodsBatchFacade;
import com.qizhi.warehouse.service.IGoodsBatchService;
import com.qizhi.warehouse.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Component
@Service
public class GoodsBatchFacadeImpl implements GoodsBatchFacade {

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IGoodsBatchService goodsBatchService;
    @Autowired
    private GoodsBatchMapper goodsBatchMapper;

    @Override
    public Integer queryWarehouse(List<GoodsBatchQueryDTO> needs, int locationX, int locationY) {
        // 根据客户坐标位置选择能满足客户下单需求的最近的前置仓库
        // 查询能满足客户下单需求的所有前置仓
        Set<Warehouse> distinctWarehouse = null;
        for (GoodsBatchQueryDTO goods : needs) {
            List<Warehouse> warehouses = goodsBatchService.querySatisfy(goods);
            if (CollectionUtils.isEmpty(warehouses)) {
                return null;
            }
            if (null == distinctWarehouse) {
                distinctWarehouse = new HashSet<>(warehouses);
            } else {
                distinctWarehouse.retainAll(warehouses);
            }
        }
        if(CollectionUtils.isEmpty(distinctWarehouse)){
            return null;
        }
        Integer curWareHouse = null;
        double distance = 200;
        for(Warehouse warehouse : distinctWarehouse){
            // 计算与用户最近距离
            int x = Math.abs(warehouse.getLocationX() - locationX);
            int y = Math.abs(warehouse.getLocationY() - locationY);
            double curDistance = Math.sqrt(x * x + y * y);
            if(curDistance < distance){
                distance = curDistance;
                curWareHouse = warehouse.getWarehouseId();
            }
        }
        return curWareHouse;
    }

    @Transactional
    @Override
    public boolean lock(Integer wareHouseId, List<GoodsBatchQueryDTO> query) {
       // 查询当前仓库商品库存
        for(GoodsBatchQueryDTO goods : query){
            List<GoodsBatch> goodsBatch = goodsBatchService.query(wareHouseId, goods.getGoodsName());
            List<GoodsBatch> update = new LinkedList<>();
            int leftAmount = goods.getAmount();
            for(GoodsBatch updateOne : goodsBatch){
                leftAmount -= updateOne.getInAmount() - updateOne.getLockedAmount();
                if(leftAmount > 0){
//                    updateOne.setInAmount(0);
                    updateOne.setLockedAmount(updateOne.getInAmount());
                }else if(leftAmount == 0){
                    break;
                }else{
//                    updateOne.setInAmount(updateOne.getLockedAmount() - leftAmount);
                    updateOne.setLockedAmount(updateOne.getInAmount() + leftAmount);
                    break;
                }
                update.add(updateOne);
            }
            goodsBatchService.batchUpdate(update);
        }
        return true;
    }

    @Override
    public boolean dudect(Integer wareHouseId, List<GoodsBatchQueryDTO> query) {
        goodsBatchService.updateLocked(wareHouseId);
        return true;
    }
}
