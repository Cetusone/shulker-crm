package com.cetus.shulkercrm.service.inventory.entity;

public enum MovementType {
    REPLENISHMENT, // Пополнение (приемка)
    SHIPMENT,      // Отгрузка
    ADJUSTMENT     // Корректировка (инвентаризация/брак)
}
