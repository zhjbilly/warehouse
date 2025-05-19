package com.qizhi.warehouse.dao;

import com.qizhi.warehouse.domain.GoodsReceiptItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsReceiptItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsReceiptItem record);

    GoodsReceiptItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(GoodsReceiptItem record);

    int batchInsert(@Param("records") List<GoodsReceiptItem> records);
}