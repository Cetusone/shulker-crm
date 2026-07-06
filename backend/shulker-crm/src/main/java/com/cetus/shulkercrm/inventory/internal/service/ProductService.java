package com.cetus.shulkercrm.inventory.internal.service;

import com.cetus.shulkercrm.inventory.api.ProductServiceInterface;
import com.cetus.shulkercrm.inventory.api.dto.ProductCreateRequest;
import com.cetus.shulkercrm.inventory.api.dto.ProductResponse;
import com.cetus.shulkercrm.inventory.internal.entity.Product;
import com.cetus.shulkercrm.inventory.internal.entity.ProductCharacteristic;
import com.cetus.shulkercrm.inventory.internal.repository.ProductRepository;
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
public class ProductService implements ProductServiceInterface {

    private final ProductRepository productRepository;

    @Override
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
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        log.info("getAllProducts");
        Page<Product> products = productRepository.findAll(pageable);

        return products.map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(long id) {
        log.info("getProductById {}", id);
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Товар с id: " + id + " не найден"));
        return mapToResponse(product);
    }

    @Override
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

    @Override
    public void deleteProduct(long id)
    {
        //БЛЯ Я ХЗ КАК УДАЛЯТЬ
        //ПУСТЬ ПОКА ТАК ОСТАНЕТСЯ
        log.info("deleteProduct {}", id);

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
