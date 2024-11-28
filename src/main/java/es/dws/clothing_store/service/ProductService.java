package es.dws.clothing_store.service;

import es.dws.clothing_store.model.Product;

import java.util.Set;

/** ProductService */
public interface ProductService {

    Set<Product> getProducts();

    Product getProductById(int id);
}
