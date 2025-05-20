package com.qizhi.warehouse.facade;

import com.qizhi.warehouse.dto.AddTransactionLogDTO;

public interface TransactionFacade {

    boolean addTransaction(AddTransactionLogDTO addTransactionLogDTO);
}
