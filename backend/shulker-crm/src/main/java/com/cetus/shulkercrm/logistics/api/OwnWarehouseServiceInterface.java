package com.cetus.shulkercrm.logistics.api;

import com.cetus.shulkercrm.logistics.api.dto.OwnWarehouseCreateRequest;
import com.cetus.shulkercrm.logistics.api.dto.OwnWarehouseResponse;

import java.util.List;

public interface OwnWarehouseServiceInterface {
    OwnWarehouseResponse createWarehouse(OwnWarehouseCreateRequest request);
    List<OwnWarehouseResponse> getAllWarehouse();
    OwnWarehouseResponse getWarehouseById(Long id);
    OwnWarehouseResponse updateWarehouse(Long id, OwnWarehouseCreateRequest request);
    void deleteWareHouseById(Long id);

}
