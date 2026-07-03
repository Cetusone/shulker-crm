package com.cetus.shulkercrm.partners.api;

import com.cetus.shulkercrm.partners.api.dto.PartnerWarehouseCreateRequest;
import com.cetus.shulkercrm.partners.api.dto.PartnerWarehouseResponse;

import java.util.List;

public interface PartnerWarehouseServiceInterface {
    public PartnerWarehouseResponse createWarehouse(long partnerId, PartnerWarehouseCreateRequest request);
    public List<PartnerWarehouseResponse> getWarehouses(long partnerId);
    public PartnerWarehouseResponse getWarehouseById(long partnerId, long warehouseId);
    public PartnerWarehouseResponse updateWarehouse(long partnerId, long warehouseId, PartnerWarehouseCreateRequest request);
    public void deleteWarehouse(long partnerId, long warehouseId);

}
