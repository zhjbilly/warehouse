package com.qizhi.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qizhi.user.constant.UserType;
import com.qizhi.user.dto.UserDTO;
import com.qizhi.user.facade.UserFacade;
import com.qizhi.warehouse.dao.TransactionLogMapper;
import com.qizhi.warehouse.domain.TransactionLog;
import com.qizhi.warehouse.dto.AddTransactionLogDTO;
import com.qizhi.warehouse.dto.TransactionLogDTO;
import com.qizhi.warehouse.service.ITransactionLogService;
import com.qizhi.warehouse.util.BizException;
import com.qizhi.warehouse.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class TransactionLogServiceImpl implements ITransactionLogService {

    @Reference()
    private UserFacade userFacade;

    @Autowired
    private TransactionLogMapper transactionLogMapper;


    @Override
    public void addTransactionLog(AddTransactionLogDTO addTransactionLogDTO) {
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setAmount(addTransactionLogDTO.getAmount());
        transactionLog.setActor(addTransactionLogDTO.getUserId());
        transactionLog.setActionType(addTransactionLogDTO.getActorType());
        transactionLog.setGoods(addTransactionLogDTO.getGoodsName());
        transactionLog.setReason(addTransactionLogDTO.getReason());
        transactionLog.setOperateDate(DateUtils.getNowDate());
        if(1 > transactionLogMapper.insert(transactionLog)){
            throw new BizException("插入时间线失败");
        }
    }

    @Override
    public List<TransactionLogDTO> viewGoodsTimeLine(String token, String goodsName) {
        userAuth(token);
        List<TransactionLog> transactionLogs = transactionLogMapper.selectByGoodsName(goodsName);
        if(CollectionUtils.isEmpty(transactionLogs)) {
            return Collections.emptyList();
        }
        List<TransactionLogDTO> rlt = new ArrayList<>();
        transactionLogs.forEach(t ->{
            TransactionLogDTO single = new TransactionLogDTO();
            BeanUtils.copyProperties(t, single);
            rlt.add(single);
        });
        return rlt;
    }

    private UserDTO userAuth(String token){
        // 用户校验
        UserDTO userDTO = null;
        if(null == token || null == (userDTO = userFacade.validToken(token)) || !UserType.STAFF.name().equals(userDTO.getUserType())){
            log.warn("token校验失败,token = {}, user = {}", token, userDTO);
            throw new BizException("用户未登陆或非staff");
        }
        return userDTO;
    }
}
