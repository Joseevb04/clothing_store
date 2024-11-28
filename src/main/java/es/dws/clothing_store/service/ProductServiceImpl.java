package es.dws.clothing_store.service;

import es.dws.clothing_store.model.Product;

import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** ProductServiceImpl */
@Service
public class ProductServiceImpl implements ProductService {

    private static int counter = 0;

    private static final Set<Product> products = new LinkedHashSet<Product>();

    @Override
    public Set<Product> getProducts() {
        final Random random = new Random();

        if (products.isEmpty()) {
            products.addAll(
                    Stream.generate(
                            () -> Product.builder()
                                    .id(counter++)
                                    .name("Product" + random.nextInt(1000))
                                    .price(random.nextDouble(1000))
                                    .build())
                            .limit(10)
                            .collect(Collectors.toSet()));
        }

        return products;
    }

    @Override
    public Product getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not get product by that ID"));
    }
}
