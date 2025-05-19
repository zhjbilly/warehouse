package com.qizhi.warehouse.controller;

import com.qizhi.warehouse.service.IWarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * user
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class WarehouseController {

    @Autowired
    private IWarehouseService warehouseService;

//    /**
//     * 用户注册
//     */
//    @PostMapping("/register")
//    public CommonResult<Void> register(@Validated @RequestBody UserRegister user){
//        if(UserType.STAFF.name().equals(user.getUserType())){
//            // 员工
//            if(StringUtils.isBlank(user.getIdCard()) || 18 != user.getIdCard().length()){
//                log.warn("身份证格式有误, {}", user);
//               return CommonResult.failed("身份证格式有误");
//            }
//            userService.registerStaff(user.getPhoneNumber(), user.getIdCard());
//        }else if(UserType.CUSTOMER.name().equals(user.getUserType())){
//            // 客户
//            if(user.getLocationX() < 0 || user.getLocationX() > 100 || user.getLocationY() < 0 || user.getLocationY() > 100){
//                log.warn("地理位置有误, {}", user);
//                return CommonResult.failed("地理位置有误");
//            }
//            userService.registerCust(user.getPhoneNumber(), user.getLocationX(), user.getLocationY());
//        }else{
//            log.warn("参数有误, {}", user);
//            return CommonResult.failed("不接受的注册方式");
//        }
//        return CommonResult.success();
//    }
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
