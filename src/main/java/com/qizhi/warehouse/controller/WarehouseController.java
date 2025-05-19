package com.qizhi.warehouse.controller;

import com.qizhi.warehouse.constant.CommonResult;
import com.qizhi.warehouse.dto.AddWarehouseDTO;
import com.qizhi.warehouse.dto.WareHouseDTO;
import com.qizhi.warehouse.service.IWarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * warehouse
 */
@Slf4j
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    private IWarehouseService warehouseService;

    /**
     * 创建仓库
     */
    @PostMapping("/add")
    public CommonResult<Void> add(@Validated @RequestBody AddWarehouseDTO warehouseDTO) {
        warehouseService.registerWarehouse(warehouseDTO.getToken(), warehouseDTO.getWarehouseType(), warehouseDTO.getLocationX(), warehouseDTO.getLocationY());
        return CommonResult.success();
    }

    /**
     * 删除仓库
     */
    @PostMapping("/delete")
    public CommonResult<Void> delete(@NotBlank String token, @NotBlank int warehouseId) {
        warehouseService.deleteWarehouse(token, warehouseId);
        return CommonResult.success();
    }

    /**
     * 仓库列表
     * @param token
     * @return
     */
    @PostMapping("/list")
    public CommonResult<List<WareHouseDTO>> list(@NotBlank String token) {
        return CommonResult.success(warehouseService.listWarehouse(token));
    }


}
