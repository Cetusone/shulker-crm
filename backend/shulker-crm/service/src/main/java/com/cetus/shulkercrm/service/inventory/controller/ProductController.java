package com.cetus.shulkercrm.service.inventory.controller;

import com.cetus.shulkercrm.api.common.dto.PageResponse;
import com.cetus.shulkercrm.api.inventory.api.ProductAPI;
import com.cetus.shulkercrm.api.inventory.dto.ProductCreateRequest;
import com.cetus.shulkercrm.api.inventory.dto.ProductResponse;
import com.cetus.shulkercrm.service.common.PageMapper;
import com.cetus.shulkercrm.service.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class ProductController implements ProductAPI {

    private final ProductService productService;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        log.info("createProduct {}",  request);
        return productService.createProduct(request);
    }

    @Override
    public PageResponse<ProductResponse> getAllProducts(int page, int size) {
        log.info("getAllProducts page: {}, size: {}", page, size);
        return PageMapper.toResponse(productService.getAllProducts(PageRequest.of(page, size)));
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
