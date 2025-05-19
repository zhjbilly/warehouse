package com.qizhi.warehouse.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDTO implements Serializable {
    private Integer userId;

    private String phoneNumber;

    private Integer locationX;

    private Integer locationY;

    private String idCard;

    private String userType;

    private String token;

    private Date expire;

}