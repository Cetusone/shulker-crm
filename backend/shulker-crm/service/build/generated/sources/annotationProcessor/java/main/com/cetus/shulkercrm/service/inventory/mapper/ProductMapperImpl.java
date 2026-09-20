package com.cetus.shulkercrm.service.inventory.mapper;

import com.cetus.shulkercrm.api.inventory.dto.CharacteristicDto;
import com.cetus.shulkercrm.api.inventory.dto.CharacteristicResponse;
import com.cetus.shulkercrm.api.inventory.dto.ProductCreateRequest;
import com.cetus.shulkercrm.api.inventory.dto.ProductResponse;
import com.cetus.shulkercrm.service.inventory.entity.Product;
import com.cetus.shulkercrm.service.inventory.entity.ProductCharacteristic;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-20T12:51:26+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponse toResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        String sku = null;
        BigDecimal weightKg = null;
        BigDecimal volumeM3 = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;
        List<CharacteristicResponse> characteristics = null;

        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        sku = product.getSku();
        weightKg = product.getWeightKg();
        volumeM3 = product.getVolumeM3();
        createdAt = product.getCreatedAt();
        updatedAt = product.getUpdatedAt();
        characteristics = productCharacteristicListToCharacteristicResponseList( product.getCharacteristics() );

        ProductResponse productResponse = new ProductResponse( id, name, description, sku, weightKg, volumeM3, createdAt, updatedAt, characteristics );

        return productResponse;
    }

    @Override
    public CharacteristicResponse toResponse(ProductCharacteristic characteristic) {
        if ( characteristic == null ) {
            return null;
        }

        Long id = null;
        String attributeName = null;
        String attributeValue = null;

        id = characteristic.getId();
        attributeName = characteristic.getAttributeName();
        attributeValue = characteristic.getAttributeValue();

        CharacteristicResponse characteristicResponse = new CharacteristicResponse( id, attributeName, attributeValue );

        return characteristicResponse;
    }

    @Override
    public Product toEntity(ProductCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( request.name() );
        product.description( request.description() );
        product.sku( request.sku() );
        product.weightKg( request.weightKg() );
        product.volumeM3( request.volumeM3() );

        return product.build();
    }

    @Override
    public ProductCharacteristic toEntity(CharacteristicDto dto) {
        if ( dto == null ) {
            return null;
        }

        ProductCharacteristic.ProductCharacteristicBuilder productCharacteristic = ProductCharacteristic.builder();

        productCharacteristic.attributeName( dto.attributeName() );
        productCharacteristic.attributeValue( dto.attributeValue() );

        return productCharacteristic.build();
    }

    @Override
    public void updateFromRequest(ProductCreateRequest request, Product product) {
        if ( request == null ) {
            return;
        }

        product.setName( request.name() );
        product.setDescription( request.description() );
        product.setSku( request.sku() );
        product.setWeightKg( request.weightKg() );
        product.setVolumeM3( request.volumeM3() );
    }

    protected List<CharacteristicResponse> productCharacteristicListToCharacteristicResponseList(List<ProductCharacteristic> list) {
        if ( list == null ) {
            return null;
        }

        List<CharacteristicResponse> list1 = new ArrayList<CharacteristicResponse>( list.size() );
        for ( ProductCharacteristic productCharacteristic : list ) {
            list1.add( toResponse( productCharacteristic ) );
        }

        return list1;
    }
}
