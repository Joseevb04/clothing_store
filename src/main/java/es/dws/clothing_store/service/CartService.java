package es.dws.clothing_store.service;

import es.dws.clothing_store.model.Cart;
import es.dws.clothing_store.model.Product;

import java.util.Set;

/** CartService */
public interface CartService {

    Set<Cart> getCartsByUserId(int id);

    Product addProductToUserCartById(int productId, int userId);

    boolean removeProductFromUserCartById(int productId, int userId);

    int updateProductAmountByIs(int amount, int productId, int userId);

    double getUserCartTotal(int userId);
}
