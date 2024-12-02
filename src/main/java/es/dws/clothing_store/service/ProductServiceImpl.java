package es.dws.clothing_store.service;

import es.dws.clothing_store.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** ProductServiceImpl */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CSVService csvService;

    // private static int counter = 0;

    private static final Set<Product> products = new LinkedHashSet<Product>();

    @Override
    public Set<Product> getProducts() {
        if (products.isEmpty()) {
            List<Product> productList = csvService.readFromCsv("data.csv", this::parseProductFromCsvLine);
            products.addAll(productList);
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
