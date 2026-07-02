package com.cetus.shulkercrm.inventory.api;

import com.cetus.shulkercrm.inventory.api.dto.StockCreateRequest;
import com.cetus.shulkercrm.inventory.api.dto.StockResponse;
import com.cetus.shulkercrm.inventory.internal.entity.Stock;

import java.util.List;

public interface StockServiceInterface {
    public StockResponse addProductOnWarehouse(Long warehouseId, StockCreateRequest request);
    public List<StockResponse> getAllStocks(Long warehouseId);
}
