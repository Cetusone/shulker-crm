package com.cetus.shulkercrm.inventory.api;

import com.cetus.shulkercrm.inventory.api.dto.ProductCreateRequest;
import com.cetus.shulkercrm.inventory.api.dto.ProductResponse;

import java.util.List;

public interface ProductServiceInterface {
    ProductResponse createProduct(ProductCreateRequest request);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(long id);
    ProductResponse updateProduct(long id, ProductCreateRequest request);
    void deleteProduct(long id);

}
