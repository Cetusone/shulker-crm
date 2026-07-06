package com.cetus.shulkercrm.partners.api;

import com.cetus.shulkercrm.partners.api.dto.PartnerWarehouseCreateRequest;
import com.cetus.shulkercrm.partners.api.dto.PartnerWarehouseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartnerWarehouseServiceInterface {
    public PartnerWarehouseResponse createWarehouse(long partnerId, PartnerWarehouseCreateRequest request);
    public Page<PartnerWarehouseResponse> getWarehouses(long partnerId, Pageable pageable);
    public PartnerWarehouseResponse getWarehouseById(long partnerId, long warehouseId);
    public PartnerWarehouseResponse updateWarehouse(long partnerId, long warehouseId, PartnerWarehouseCreateRequest request);
    public void deleteWarehouse(long partnerId, long warehouseId);

}
