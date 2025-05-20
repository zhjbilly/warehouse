package com.qizhi.warehouse.facade.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qizhi.warehouse.dto.AddTransactionLogDTO;
import com.qizhi.warehouse.facade.TransactionFacade;
import com.qizhi.warehouse.service.ITransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service
public class TransactionFacadeImpl implements TransactionFacade {

    @Autowired
    private ITransactionLogService transactionLogService;
    @Override
    public boolean addTransaction(AddTransactionLogDTO addTransactionLogDTO) {
        transactionLogService.addTransactionLog(addTransactionLogDTO);
         return true;
    }
}
