package com.qizhi.warehouse.dao;

import com.qizhi.warehouse.domain.Warehouse;

import java.util.List;

public interface WarehouseMapper {
    int deleteByPrimaryKey(Integer warehouseId);

    int insert(Warehouse record);

    Warehouse selectByPrimaryKey(Integer warehouseId);

    int updateByPrimaryKey(Warehouse record);

    Warehouse selectCity();

    List<Warehouse> selectByLocation(int locationX, int locationY);

    List<Warehouse> selectAll();
}