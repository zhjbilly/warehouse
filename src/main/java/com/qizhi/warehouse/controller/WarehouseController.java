package com.qizhi.warehouse.controller;

import com.qizhi.warehouse.constant.CommonResult;
import com.qizhi.warehouse.dto.AddWarehouseDTO;
import com.qizhi.warehouse.service.IWarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * user
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
    public CommonResult<Void> add(@Validated @RequestBody AddWarehouseDTO warehouseDTO){
        warehouseService.registerWarehouse(warehouseDTO.getToken(), warehouseDTO.getWarehouseType(), warehouseDTO.getLocationX(), warehouseDTO.getLocationY());
        return CommonResult.success();
    }

//
//    /**
//     * 用户登陆
//     */
//    @PostMapping("/login")
//    public CommonResult<String> login(@NotBlank @Min(11) @Max(11) String phoneNumber){
//        UserDTO login = userService.login(phoneNumber);
//        if(null == login){
//            return CommonResult.failed(ExceptionCodeEnum.UNAUTHORIZED);
//        }
//        return CommonResult.success(login.getToken());
//    }

}
