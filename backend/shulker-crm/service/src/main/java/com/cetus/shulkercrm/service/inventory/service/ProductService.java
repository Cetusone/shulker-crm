package com.cetus.shulkercrm.service.inventory.service;

import com.cetus.shulkercrm.api.inventory.dto.ProductCreateRequest;
import com.cetus.shulkercrm.api.inventory.dto.ProductResponse;
import com.cetus.shulkercrm.service.inventory.entity.Product;
import com.cetus.shulkercrm.service.inventory.mapper.ProductMapper;
import com.cetus.shulkercrm.service.inventory.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        log.info("createProduct, {}", request);

        Product product = productMapper.toEntity(request);

        if (request.characteristics() != null) {
            request.characteristics()
                    .forEach(charDto -> product.addCharacteristic(productMapper.toEntity(charDto)));
        }

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        log.info("getAllProducts");
        Page<Product> products = productRepository.findAll(pageable);

        return products.map(productMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(long id) {
        log.info("getProductById {}", id);
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Товар с id: " + id + " не найден"));
        return productMapper.toResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(long id, ProductCreateRequest request) {
        log.info("updateProduct id {}, request {}", id, request);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Товар с id: " + id + " не найден"));

        productMapper.updateFromRequest(request, product);

        new ArrayList<>(product.getCharacteristics()).forEach(product::removeCharacteristic);

        if (request.characteristics() != null) {
            request.characteristics()
                    .forEach(charDto -> product.addCharacteristic(productMapper.toEntity(charDto)));
        }
        productRepository.save(product);

        return productMapper.toResponse(product);
    }

    @Transactional
    public void deleteProduct(long id)
    {
        log.info("deleteProduct {}", id);
        productRepository.deleteById(id);

    }

}
