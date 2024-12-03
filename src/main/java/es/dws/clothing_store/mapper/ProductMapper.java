package es.dws.clothing_store.mapper;

import es.dws.clothing_store.entity.ProductEntity;
import es.dws.clothing_store.model.Product;

/**
 * ProductMapper
 */
public class ProductMapper {

    private ProductMapper() {
    }

    public static Product mapProduct(ProductEntity product) {
        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public static ProductEntity mapProduct(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
