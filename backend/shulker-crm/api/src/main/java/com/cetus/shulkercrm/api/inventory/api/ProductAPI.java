package com.cetus.shulkercrm.api.inventory.api;


import com.cetus.shulkercrm.api.inventory.dto.ProductCreateRequest;
import com.cetus.shulkercrm.api.inventory.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/products")
public interface ProductAPI {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponse createProduct(@RequestBody @Valid ProductCreateRequest request);

    @GetMapping
    Page<ProductResponse> getAllProducts(Pageable pageable);

    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable long id);

    @PutMapping("/{id}")
    ProductResponse updateProduct(@PathVariable long id, @RequestBody @Valid ProductCreateRequest request);

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProduct(@PathVariable long id);

}
