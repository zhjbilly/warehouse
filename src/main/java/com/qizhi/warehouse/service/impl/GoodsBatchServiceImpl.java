package com.qizhi.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qizhi.user.constant.UserType;
import com.qizhi.user.dto.UserDTO;
import com.qizhi.user.facade.UserFacade;
import com.qizhi.warehouse.constant.Const;
import com.qizhi.warehouse.constant.WarehouseType;
import com.qizhi.warehouse.dao.GoodsBatchMapper;
import com.qizhi.warehouse.dao.GoodsReceiptItemMapper;
import com.qizhi.warehouse.dao.GoodsReceiptMapper;
import com.qizhi.warehouse.dao.WarehouseMapper;
import com.qizhi.warehouse.domain.GoodsBatch;
import com.qizhi.warehouse.domain.GoodsReceipt;
import com.qizhi.warehouse.domain.GoodsReceiptItem;
import com.qizhi.warehouse.domain.Warehouse;
import com.qizhi.warehouse.dto.*;
import com.qizhi.warehouse.service.IGoodsBatchService;
import com.qizhi.warehouse.service.ITransactionLogService;
import com.qizhi.warehouse.util.BizException;
import com.qizhi.warehouse.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class GoodsBatchServiceImpl implements IGoodsBatchService {

    @Reference
    private UserFacade userFacade;

    @Autowired
    private GoodsReceiptMapper goodsReceiptMapper;

    @Autowired
    private GoodsReceiptItemMapper goodsReceiptItemMapper;

    @Autowired
    private GoodsBatchMapper goodsBatchMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private ITransactionLogService transactionLogService;

    @Transactional
    @Override
    public void in(AddGoodsBatch addGoodsBatch) {
        UserDTO user = userAuth(addGoodsBatch.getToken());
        // 入库单校验
        GoodsReceipt goodsReceipt = goodsReceiptMapper.selectByPrimaryKey(addGoodsBatch.getReceiptId());
        if (null == goodsReceipt || Const.CLOSED == goodsReceipt.getIsClosed()) {
            throw new BizException("进货单不存在or已被关闭");
        }
        // 根据货物名称查找批次
        GoodsReceiptItem goodsReceiptItem = goodsReceiptItemMapper.selectByReceiptIdAndGoodsName(addGoodsBatch.getReceiptId(), addGoodsBatch.getGoodsName());
        if (null == goodsReceiptItem) {
            throw new BizException("货物不在此次入库名单里");
        } else {
            // 批次存在
            // 根据批次与货物查询已入库数量
            GoodsBatch goodsBatch = goodsBatchMapper.selectByReceiptIdAndName(addGoodsBatch.getReceiptId(), addGoodsBatch.getGoodsName());
            int curAmount = addGoodsBatch.getAmount();
            if (null != goodsBatch) {
                // 之前有入库
                curAmount += goodsBatch.getInAmount();
            }
            if (curAmount > goodsReceiptItem.getAmount()) {
                throw new BizException("入库数量超限");
            }
            // 更新批次
            if (null == goodsBatch) {
                goodsBatch = new GoodsBatch();
                goodsBatch.setGoodsName(addGoodsBatch.getGoodsName());
                goodsBatch.setWarehouseId(warehouseMapper.selectCity().getWarehouseId());
                goodsBatch.setShelfPosition(goodsBatchMapper.selectLastShelf() + 1);
                goodsBatch.setInAmount(addGoodsBatch.getAmount());
                goodsBatch.setLockedAmount(0);
                goodsBatch.setOutAmount(0);
                goodsBatch.setReceiptId(addGoodsBatch.getReceiptId());
                goodsBatch.setInwardDate(DateUtils.getNowDate());
                if (1 > goodsBatchMapper.insert(goodsBatch)) {
                    throw new BizException("新增入库失败");
                }
            } else {
                goodsBatch.setInAmount(curAmount);
                goodsBatch.setInwardDate(DateUtils.getNowDate());
                if (1 > goodsBatchMapper.updateByPrimaryKeySelective(goodsBatch)) {
                    throw new BizException("更新入库失败");
                }
            }
        }
        // TODO 增加操作入库（待改为切面）
        AddTransactionLogDTO addTransactionLogDTO = new AddTransactionLogDTO();
        addTransactionLogDTO.setActorType(Const.IMPORT);
        addTransactionLogDTO.setGoodsName(addGoodsBatch.getGoodsName());
        addTransactionLogDTO.setAmount(addGoodsBatch.getAmount());
        addTransactionLogDTO.setUserId(user.getUserId());
        transactionLogService.addTransactionLog(addTransactionLogDTO);
    }

    @Transactional
    @Override
    public void transfer(TransferGoods transferGoods) {
        UserDTO user = userAuth(transferGoods.getToken());
        // 仓库校验
        Warehouse source = warehouseMapper.selectByPrimaryKey(transferGoods.getSourceWare());
        if (null == source) {
            throw new BizException("调出仓不存在");
        }
        Warehouse target = warehouseMapper.selectByPrimaryKey(transferGoods.getTargetWare());
        if (null == target || !WarehouseType.FRONT.name().equals(target.getWarehouseType())) {
            throw new BizException("调入仓不存在or非前置仓");
        }
        // 转出库存校验
        GoodsBatch outGoodsBatch = goodsBatchMapper.selectByPrimaryKey(transferGoods.getBatchId());
        if (null == outGoodsBatch || outGoodsBatch.getInAmount() - outGoodsBatch.getLockedAmount() < transferGoods.getAmount()) {
            throw new BizException("库存批次不存在or可转移库存不足");
        }
        // 转出扣减库存
        outGoodsBatch.setInAmount(outGoodsBatch.getInAmount() - transferGoods.getAmount());
        outGoodsBatch.setOutAmount(outGoodsBatch.getOutAmount() + transferGoods.getAmount());
        if (1 > goodsBatchMapper.updateByPrimaryKeySelective(outGoodsBatch)) {
            throw new BizException("转出仓扣减库存失败");
        }
        // 转入增加库存
        GoodsBatch inGoodsBatch = goodsBatchMapper.selectByWareAndName(transferGoods.getTargetWare(), transferGoods.getGoodsName());
        if (null == inGoodsBatch) {
            // 新增批次
            inGoodsBatch = new GoodsBatch();
            inGoodsBatch.setGoodsName(transferGoods.getGoodsName());
            inGoodsBatch.setInAmount(transferGoods.getAmount());
            inGoodsBatch.setLockedAmount(0);
            inGoodsBatch.setOutAmount(0);
            inGoodsBatch.setShelfPosition(goodsBatchMapper.selectLastShelf() + 1);
            inGoodsBatch.setReceiptId(outGoodsBatch.getReceiptId());
            inGoodsBatch.setInwardDate(DateUtils.getNowDate());
            inGoodsBatch.setWarehouseId(transferGoods.getTargetWare());
            if (1 > goodsBatchMapper.insert(inGoodsBatch)) {
                throw new BizException("转入仓新增库存失败");
            }
        } else {
            // 更新
            inGoodsBatch.setInAmount(inGoodsBatch.getInAmount() + transferGoods.getAmount());
            inGoodsBatch.setInwardDate(DateUtils.getNowDate());
            if (1 > goodsBatchMapper.updateByPrimaryKeySelective(inGoodsBatch)) {
                throw new BizException("转入仓更新库存失败");
            }
        }
        // TODO 增加操作调拨
        AddTransactionLogDTO addTransactionLogDTO = new AddTransactionLogDTO();
        addTransactionLogDTO.setActorType(Const.TRANSFER);
        addTransactionLogDTO.setGoodsName(transferGoods.getGoodsName());
        addTransactionLogDTO.setAmount(transferGoods.getAmount());
        addTransactionLogDTO.setUserId(user.getUserId());
        transactionLogService.addTransactionLog(addTransactionLogDTO);
    }

    @Override
    public List<GoodsBatchDTO> view(ViewGoosBatch viewGoodsBatch) {
        userAuth(viewGoodsBatch.getToken());
        List<GoodsBatch> goodsBatches = null;
        if (Const.GOODS.equals(viewGoodsBatch.getSearchType())) {
            // 货物搜索
            goodsBatches = goodsBatchMapper.selectByWareAndNameLike(viewGoodsBatch.getWarehouseId(), viewGoodsBatch.getSearchValue());
        } else if (Const.SHELF.equals(viewGoodsBatch.getSearchType())) {
            // 货架搜索
            goodsBatches = goodsBatchMapper.selectByWareAndShelf(viewGoodsBatch.getWarehouseId(), Integer.parseInt(viewGoodsBatch.getSearchValue()));
        } else {
            goodsBatches = goodsBatchMapper.selectByReceiptId(viewGoodsBatch.getWarehouseId());
        }
        List<GoodsBatchDTO> rlt = new ArrayList<>();
        if (null != goodsBatches) {
            for (GoodsBatch batch : goodsBatches) {
                GoodsBatchDTO single = new GoodsBatchDTO();
                rlt.add(single);
                BeanUtils.copyProperties(batch, single);
            }
        }
        return rlt;
    }

    @Transactional
    @Override
    public void adjust(AdjustGoodsBatch adjustGoodsBatch) {
        UserDTO user = userAuth(adjustGoodsBatch.getToken());
        // 库存校验
        GoodsBatch goodsBatch = goodsBatchMapper.selectByPrimaryKey(adjustGoodsBatch.getBatchId());
        if (null == goodsBatch) {
            throw new BizException("库存不存在");
        }
        if (adjustGoodsBatch.getNewAmount() < goodsBatch.getLockedAmount()) {
            throw new BizException("不能设置低于锁定数量");
        }
        // 调整数量
        int adjustAmount = goodsBatch.getInAmount() - goodsBatch.getOutAmount() - goodsBatch.getLockedAmount() - adjustGoodsBatch.getNewAmount();
        goodsBatch.setInAmount(adjustGoodsBatch.getNewAmount());
        if (1 > goodsBatchMapper.updateByPrimaryKeySelective(goodsBatch)) {
            throw new BizException("调整库存失败");
        }
        // TODO 增加库存调整操作日志
        AddTransactionLogDTO addTransactionLogDTO = new AddTransactionLogDTO();
        addTransactionLogDTO.setActorType(Const.TRANSFER);
        addTransactionLogDTO.setGoodsName(goodsBatch.getGoodsName());
        // 调整数量
        addTransactionLogDTO.setAmount(adjustAmount);
        addTransactionLogDTO.setUserId(user.getUserId());
        addTransactionLogDTO.setReason(adjustGoodsBatch.getReason());
        transactionLogService.addTransactionLog(addTransactionLogDTO);
    }

    @Override
    public List<Integer> querySatisfy(List<GoodsBatchQueryDTO> needs) {
        // TODO
        return Collections.emptyList();
    }

    @Override
    public List<Warehouse> querySatisfy(GoodsBatchQueryDTO needs) {
        return goodsBatchMapper.selectWarehouseIdByCond(needs.getGoodsName(), needs.getAmount());
    }

    @Override
    public List<GoodsBatch> query(int warehouseId, String goodsName) {
        return goodsBatchMapper.selectByWarehouseId(warehouseId, goodsName);
    }

    @Override
    public void batchUpdate(List<GoodsBatch> records) {
        for (GoodsBatch single : records) {
            if (1 > goodsBatchMapper.updateByPrimaryKeySelective(single)) {
                throw new BizException("更新库存失败");
            }
        }
    }

    @Override
    public void updateLocked(int warehouseId) {
        goodsBatchMapper.updateLocked(warehouseId);
    }

    private UserDTO userAuth(String token) {
        // 用户校验
        UserDTO userDTO = null;
        if (null == token || null == (userDTO = userFacade.validToken(token)) || !UserType.STAFF.name().equals(userDTO.getUserType())) {
            log.warn("token校验失败,token = {}, user = {}", token, userDTO);
            throw new BizException("用户未登陆或非staff");
        }
        return userDTO;
    }
}
