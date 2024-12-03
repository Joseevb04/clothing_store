package es.dws.clothing_store.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import es.dws.clothing_store.mapper.ProductMapper;
import es.dws.clothing_store.model.Product;
import es.dws.clothing_store.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** ProductServiceImpl */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Set<Product> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::mapProduct)
                .collect(Collectors.toSet());
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id)
                .map(ProductMapper::mapProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

}
