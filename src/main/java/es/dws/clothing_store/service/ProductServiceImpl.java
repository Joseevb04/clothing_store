package es.dws.clothing_store.service;

import es.dws.clothing_store.model.Product;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** ProductServiceImpl */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    // private static int counter = 0;

    private static final Set<Product> products = new LinkedHashSet<Product>();

    @Override
    public Set<Product> getProducts() {
        if (products.isEmpty()) {
            try {
                ClassPathResource resource = new ClassPathResource("data.csv");
                List<String> lines = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8);

                products.addAll(lines.stream()
                        .skip(1)
                        .map(this::parseProductFromCsvLine)
                        .collect(Collectors.toSet()));

            } catch (IOException e) {
                throw new RuntimeException("Failed to load products from CSV", e);
            }
        }

        products.forEach(p -> log.info(p.toString()));
        return products;
    }

    @Override
    public Product getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not get product by that ID"));
    }

    private Product parseProductFromCsvLine(String line) {
        String[] fields = line.split(",");
        return Product.builder()
                .id(Integer.parseInt(fields[0].trim()))
                .name(fields[1].trim())
                .description(fields[2].trim())
                .price(Double.parseDouble(fields[3].trim()))
                .build();
    }
}
