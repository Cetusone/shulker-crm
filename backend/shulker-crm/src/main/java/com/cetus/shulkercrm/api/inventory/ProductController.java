package com.cetus.shulkercrm.api.inventory;

import com.cetus.shulkercrm.inventory.api.ProductServiceInterface;
import com.cetus.shulkercrm.inventory.api.dto.ProductCreateRequest;
import com.cetus.shulkercrm.inventory.api.dto.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceInterface productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody @Valid ProductCreateRequest request) {
        log.info("createProduct {}",  request);
        return productService.createProduct(request);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        log.info("getAllProducts");
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable long id) {
        log.info("getProductById {}",  id);
        return productService.getProductById(id);
    }

    @PutMapping
    public ProductResponse updateProduct(@PathVariable long id, @RequestBody ProductCreateRequest request) {
        log.info("updateProduct {}",  request);
        return productService.updateProduct(id, request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@RequestParam long id) {
        log.info("deleteProduct {}",  id);
        productService.deleteProduct(id);
    }
}
