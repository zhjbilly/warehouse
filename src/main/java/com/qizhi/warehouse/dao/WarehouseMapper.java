package com.qizhi.warehouse.dao;

import com.qizhi.warehouse.domain.Warehouse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WarehouseMapper {
    int deleteByPrimaryKey(Integer warehouseId);

    int insert(Warehouse record);

    Warehouse selectByPrimaryKey(Integer warehouseId);

    int updateByPrimaryKey(Warehouse record);

    Warehouse selectCity();

    List<Warehouse> selectByLocation(int locationX, int locationY);
}