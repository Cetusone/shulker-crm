package com.cetus.shulkercrm.service.inventory.service;

import com.cetus.shulkercrm.api.inventory.dto.ProductCreateRequest;
import com.cetus.shulkercrm.api.inventory.dto.ProductResponse;
import com.cetus.shulkercrm.service.inventory.entity.Product;
import com.cetus.shulkercrm.service.inventory.entity.ProductCharacteristic;
import com.cetus.shulkercrm.service.inventory.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        log.info("createProduct, {}", request);

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .sku(request.sku())
                .weightKg(request.weightKg())
                .volumeM3(request.volumeM3())
                .build();

        if (request.characteristics() != null) {
            request.characteristics().forEach(charDto -> {
                ProductCharacteristic characteristic = ProductCharacteristic.builder()
                        .attributeName(charDto.attributeName())
                        .attributeValue(charDto.attributeValue())
                        .build();

                product.addCharacteristic(characteristic);
            });
        }

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        log.info("getAllProducts");
        Page<Product> products = productRepository.findAll(pageable);

        return products.map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(long id) {
        log.info("getProductById {}", id);
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Товар с id: " + id + " не найден"));
        return mapToResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(long id, ProductCreateRequest request) {
        log.info("updateProduct id {}, request {}", id, request);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Товар с id: " + id + " не найден"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setSku(request.sku());
        product.setWeightKg(request.weightKg());
        product.setVolumeM3(request.volumeM3());

        new ArrayList<>(product.getCharacteristics()).forEach(product::removeCharacteristic);

        if (request.characteristics() != null) {
            request.characteristics().forEach(charDto -> {
                ProductCharacteristic characteristic = ProductCharacteristic.builder()
                        .attributeName(charDto.attributeName())
                        .attributeValue(charDto.attributeValue())
                        .build();

                product.addCharacteristic(characteristic);
            });
        }
        productRepository.save(product);

        return mapToResponse(product);
    }

    @Transactional
    public void deleteProduct(long id)
    {
        log.info("deleteProduct {}", id);
        productRepository.deleteById(id);

    }


    private ProductResponse mapToResponse(Product product) {
        List<ProductResponse.CharacteristicResponse> charResponses = product.getCharacteristics()
                .stream().map(c -> new ProductResponse.CharacteristicResponse(
                        c.getId(),
                        c.getAttributeName(),
                        c.getAttributeValue()
                ))
                .collect(Collectors.toList());

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getSku(),
                product.getWeightKg(),
                product.getVolumeM3(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                charResponses
        );
    }
}
