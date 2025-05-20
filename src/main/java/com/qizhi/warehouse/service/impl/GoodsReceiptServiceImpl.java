package com.qizhi.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qizhi.user.constant.UserType;
import com.qizhi.user.dto.UserDTO;
import com.qizhi.user.facade.UserFacade;
import com.qizhi.warehouse.constant.Const;
import com.qizhi.warehouse.dao.GoodsBatchMapper;
import com.qizhi.warehouse.dao.GoodsReceiptItemMapper;
import com.qizhi.warehouse.dao.GoodsReceiptMapper;
import com.qizhi.warehouse.domain.GoodsBatch;
import com.qizhi.warehouse.domain.GoodsReceipt;
import com.qizhi.warehouse.domain.GoodsReceiptItem;
import com.qizhi.warehouse.dto.AddGoodsReceipt;
import com.qizhi.warehouse.dto.GoodsReceiptItemDTO;
import com.qizhi.warehouse.service.IGoodsReceiptService;
import com.qizhi.warehouse.util.BizException;
import com.qizhi.warehouse.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GoodsReceiptServiceImpl implements IGoodsReceiptService {

    @Reference
    private UserFacade userFacade;

    @Autowired
    private GoodsReceiptMapper goodsReceiptMapper;

    @Autowired
    private GoodsReceiptItemMapper goodsReceiptItemMapper;

    @Autowired
    private GoodsBatchMapper goodsBatchMapper;

    @Transactional
    @Override
    public void importGoodsReceipt(AddGoodsReceipt addGoodsReceipt) {
        userAuth(addGoodsReceipt.getToken());
        // 是否有未关闭的进货单
        int notClosed = goodsReceiptMapper.countNotClosed();
        if (notClosed != 0) {
            throw new BizException("存在未关闭的进货单");
        }
        // 保存
        GoodsReceipt goodsReceipt = new GoodsReceipt();
        BeanUtils.copyProperties(addGoodsReceipt.getGoodsReceipt(), goodsReceipt);
        goodsReceipt.setIsClosed(Const.OPEN);
        goodsReceipt.setCloseDateTime(DateUtils.getNowDate());
        goodsReceipt.setSupplier(null);
        if (1 > goodsReceiptMapper.insert(goodsReceipt)) {
            throw new RuntimeException("进货单保存失败");
        }
        int goodsReceiptId = goodsReceipt.getReceiptId();
        List<GoodsReceiptItem> goodsReceiptItem = new ArrayList<>();
        for (GoodsReceiptItemDTO dto : addGoodsReceipt.getGoodsReceiptItems()) {
            GoodsReceiptItem record = new GoodsReceiptItem();
            BeanUtils.copyProperties(dto, record);
            record.setReceiptId(goodsReceiptId);
            goodsReceiptItem.add(record);
        }
        if (1 > goodsReceiptItemMapper.batchInsert(goodsReceiptItem)) {
            throw new RuntimeException("保存进货单明细失败");
        }
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

    @Override
    public void addSuppler(String token, int goodsReceiptId, String suppler) {
        userAuth(token);
        // 进货单存在or未关闭
        GoodsReceipt goodsReceipt = goodsReceiptMapper.selectByPrimaryKey(goodsReceiptId);
        if (null == goodsReceipt || Const.CLOSED == goodsReceipt.getIsClosed()) {
            throw new BizException("进货单不存在or已关闭，不能更新供应商");
        }
        goodsReceipt.setSupplier(suppler);
        if (1 > goodsReceiptMapper.updateByPrimaryKey(goodsReceipt)) {
            throw new BizException("进货单更新进货商失败");
        }
    }

    @Override
    public void closeReceipt(String token, int goodsReceiptId) {
        userAuth(token);
        // 进货单存在or已关闭
        GoodsReceipt goodsReceipt = goodsReceiptMapper.selectByPrimaryKey(goodsReceiptId);
        if (null == goodsReceipt || Const.CLOSED == goodsReceipt.getIsClosed()) {
            throw new BizException("进货单不存在or已关闭，不能关闭");
        }
        // 库存校验
        List<GoodsReceiptItem> goodsReceiptItems = goodsReceiptItemMapper.selectByReceiptId(goodsReceiptId);
        List<GoodsBatch> goodsBatches = goodsBatchMapper.selectByReceiptId(goodsReceiptId);
        if (goodsReceiptItems.stream().anyMatch(item -> goodsBatches.stream().noneMatch(batch -> batch.getGoodsName().equals(item.getName()) || item.getAmount().equals(batch.getInAmount() + batch.getOutAmount() - batch.getLockedAmount())))) {
            // 未完成全入库，无法关闭
            throw new BizException("进货单未全部入库，无法关闭");
        }
        // 关闭
        goodsReceipt.setIsClosed(Const.CLOSED);
        if (1 > goodsReceiptMapper.updateByPrimaryKey(goodsReceipt)) {
            throw new BizException("进货单关闭失败");
        }
    }


}
