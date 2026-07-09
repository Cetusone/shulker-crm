package com.cetus.shulkercrm.service.inventory.controller;

import com.cetus.shulkercrm.api.inventory.api.ProductAPI;
import com.cetus.shulkercrm.api.inventory.dto.ProductCreateRequest;
import com.cetus.shulkercrm.api.inventory.dto.ProductResponse;
import com.cetus.shulkercrm.service.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController implements ProductAPI {

    private final ProductService productService;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        log.info("createProduct {}",  request);
        return productService.createProduct(request);
    }

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        log.info("getAllProducts");
        return productService.getAllProducts(pageable);
    }

    @Override
    public ProductResponse getProductById(long id) {
        log.info("getProductById {}",  id);
        return productService.getProductById(id);
    }

    @Override
    public ProductResponse updateProduct(long id, ProductCreateRequest request) {
        log.info("updateProduct {}",  request);
        return productService.updateProduct(id, request);
    }

    @Override
    public void deleteProduct(long id) {
        log.info("deleteProduct {}",  id);
        productService.deleteProduct(id);
    }
}
