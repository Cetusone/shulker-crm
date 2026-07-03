package com.cetus.shulkercrm.api.exception;

public class TransportNotFoundException extends ResourceNotFoundException {
    public TransportNotFoundException(Long id) {
        super("Транспорт с ID " + id + " не найден");
    }
}
