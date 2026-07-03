package com.cetus.shulkercrm.api.inventory;

import com.cetus.shulkercrm.inventory.api.ProductServiceInterface;
import com.cetus.shulkercrm.inventory.api.dto.ProductCreateRequest;
import com.cetus.shulkercrm.inventory.api.dto.ProductResponse;
import com.cetus.shulkercrm.api.exception.ProductNotFoundException;
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
        ProductResponse response = productService.getProductById(id);
        if (response == null){
            throw new ProductNotFoundException(id);
        }
        return response;
    }

    @PutMapping("/{id}")
<<<<<<< Updated upstream
    public ProductResponse updateProduct(@PathVariable long id, @RequestBody @Valid ProductCreateRequest request) {
=======
    public ProductResponse updateProduct(@PathVariable long id, @Valid @RequestBody ProductCreateRequest request) {
>>>>>>> Stashed changes
        log.info("updateProduct {}",  request);
        ProductResponse response = productService.updateProduct(id, request);
        if (response == null) {
            throw new ProductNotFoundException(id);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable long id) {
        log.info("deleteProduct {}",  id);
        productService.deleteProduct(id);
    }
}
