package com.cetus.shulkersrm.api.inventory;

import com.cetus.shulkersrm.api.inventory.dto.ProductCreateRequest;
import com.cetus.shulkersrm.api.inventory.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    public ProductResponse createProduct(@RequestBody ProductCreateRequest request) {
        log.info("createProduct");
        return null;
    }
}
