package com.qizhi.warehouse.dao;

import com.qizhi.warehouse.domain.GoodsBatch;
import com.qizhi.warehouse.domain.Warehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsBatchMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsBatch record);

    GoodsBatch selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsBatch record);

    int updateByPrimaryKey(GoodsBatch record);

    GoodsBatch selectByReceiptIdAndName(@Param("receiptId")int receiptId, @Param("name")String name);

    int selectLastShelf();

    GoodsBatch selectByWareAndName(@Param("warehouseId")int warehouseId, @Param("name")String name);

    List<GoodsBatch> selectByWareAndNameLike(@Param("warehouseId")int warehouseId, @Param("name")String name);

    List<GoodsBatch> selectByWareAndShelf(@Param("warehouseId")int warehouseId, @Param("shelf")int shelf);

    List<GoodsBatch> selectByReceiptId(@Param("receiptId")int receiptId);

    List<GoodsBatch> selectHasBatch(int warehouseId);

    List<Warehouse> selectWarehouseIdByCond(@Param("goodsName")String goodsName, @Param("amount")int amount);

    List<GoodsBatch> selectByWarehouseId(@Param("warehouseId")int warehouseId, @Param("goodsName")String goodsName);

    List<GoodsBatch> updateLocked(@Param("warehouseId")int warehouseId);

}