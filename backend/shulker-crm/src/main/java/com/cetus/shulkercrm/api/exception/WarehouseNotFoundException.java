package com.cetus.shulkercrm.api.exception;

public class WarehouseNotFoundException extends RuntimeException {
    public WarehouseNotFoundException(Long message) {
        super(String.valueOf(message));
    }
}
