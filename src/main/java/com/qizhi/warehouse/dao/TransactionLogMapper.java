package com.qizhi.warehouse.dao;

import com.qizhi.warehouse.domain.TransactionLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TransactionLog record);

    TransactionLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TransactionLog record);

    int updateByPrimaryKey(TransactionLog record);

    List<TransactionLog> selectByGoodsName(String goodsName);
}