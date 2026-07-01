package com.cetus.shulkersrm.inventory.api;

import com.cetus.shulkersrm.inventory.api.dto.ProductCreateRequest;
import com.cetus.shulkersrm.inventory.api.dto.ProductResponse;

public interface ProductServiceInterface {
    ProductResponse createProduct(ProductCreateRequest request);
}
