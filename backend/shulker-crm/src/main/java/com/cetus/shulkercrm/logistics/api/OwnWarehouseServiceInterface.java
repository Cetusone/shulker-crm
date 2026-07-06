package com.cetus.shulkercrm.logistics.api;

import com.cetus.shulkercrm.logistics.api.dto.OwnWarehouseCreateRequest;
import com.cetus.shulkercrm.logistics.api.dto.OwnWarehouseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OwnWarehouseServiceInterface {
    OwnWarehouseResponse createWarehouse(OwnWarehouseCreateRequest request);
    Page<OwnWarehouseResponse> getAllWarehouse(Pageable pageable);
    OwnWarehouseResponse getWarehouseById(Long id);
    OwnWarehouseResponse updateWarehouse(Long id, OwnWarehouseCreateRequest request);
    void deleteWareHouseById(Long id);

}
