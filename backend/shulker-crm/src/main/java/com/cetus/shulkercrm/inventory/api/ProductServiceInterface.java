package com.cetus.shulkercrm.inventory.api;

import com.cetus.shulkercrm.inventory.api.dto.ProductCreateRequest;
import com.cetus.shulkercrm.inventory.api.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductServiceInterface {
    ProductResponse createProduct(ProductCreateRequest request);
    Page<ProductResponse> getAllProducts(Pageable pageable);
    ProductResponse getProductById(long id);
    ProductResponse updateProduct(long id, ProductCreateRequest request);
    void deleteProduct(long id);

}
