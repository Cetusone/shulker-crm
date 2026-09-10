package com.cetus.shulkercrm.api.inventory.api;


import com.cetus.shulkercrm.api.common.dto.PageResponse;
import com.cetus.shulkercrm.api.inventory.dto.ProductCreateRequest;
import com.cetus.shulkercrm.api.inventory.dto.ProductResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/products")
public interface ProductAPI {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponse createProduct(@RequestBody @Valid ProductCreateRequest request);

    @GetMapping
    PageResponse<ProductResponse> getAllProducts(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size);

    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable long id);

    @PutMapping("/{id}")
    ProductResponse updateProduct(@PathVariable long id, @RequestBody @Valid ProductCreateRequest request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProduct(@PathVariable long id);

}
