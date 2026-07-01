package com.cetus.shulkersrm.inventory.internal.service;

import com.cetus.shulkersrm.inventory.api.ProductServiceInterface;
import com.cetus.shulkersrm.inventory.api.dto.ProductCreateRequest;
import com.cetus.shulkersrm.inventory.api.dto.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements ProductServiceInterface {
    public ProductResponse createProduct(ProductCreateRequest request) {
        return null;
    }
}
