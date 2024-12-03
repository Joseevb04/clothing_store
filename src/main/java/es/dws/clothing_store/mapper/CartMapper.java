package es.dws.clothing_store.mapper;

import es.dws.clothing_store.entity.CartEntity;
import es.dws.clothing_store.model.Cart;

/**
 * CartMapper
 */
public class CartMapper {

    private CartMapper() {
    }

    public static Cart mapCart(CartEntity cart) {
        return Cart.builder()
                .user(UserMapper.mapUser(cart.getId().getUser()))
                .product(ProductMapper.mapProduct(cart.getId().getProduct()))
                .amount(cart.getAmount())
                .build();
    }
}
