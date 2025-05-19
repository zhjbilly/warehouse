package com.qizhi.warehouse.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserToken implements Serializable {

    private String token;
}
