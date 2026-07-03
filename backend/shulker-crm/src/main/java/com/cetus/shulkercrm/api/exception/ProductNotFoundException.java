package com.cetus.shulkercrm.api.exception;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(Long id) {
        super("Продукт с ID " + id + " не найден");
    }
}
