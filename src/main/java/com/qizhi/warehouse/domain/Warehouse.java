package com.qizhi.warehouse.domain;

import lombok.Data;

import java.util.Objects;

@Data
public class Warehouse {
    private Integer warehouseId;

    private String warehouseType;

    private Integer locationX;

    private Integer locationY;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(warehouseId, warehouse.warehouseId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(warehouseId);
    }

}