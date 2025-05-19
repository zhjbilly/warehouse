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

    GoodsReceiptItem selectByReceiptIdAndGoodsName(@Param("receiptId")int receiptId, @Param("name")String name);

    List<GoodsReceiptItem> selectByReceiptId(@Param("receiptId")int receiptId);
}