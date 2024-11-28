package es.dws.clothing_store.service;

import es.dws.clothing_store.model.Cart;
import es.dws.clothing_store.model.Product;
import es.dws.clothing_store.model.User;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/** CartServiceImpl */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final ProductService productService;

    private static final Set<Cart> carts = new LinkedHashSet<Cart>();

    @Override
    public Set<Cart> getCartsByUserId(int id) {
        return carts.stream()
                .filter(cart -> cart.getUser().getId() == id)
                .collect(Collectors.toSet());
    }

    @Override
    public Product addProductToUserCartById(int productId, int userId) {
        final Set<Cart> userCart = getCartsByUserId(userId);

        if (userCart.stream().noneMatch(cart -> cart.getProduct().getId() == productId)) {

            final User user = userService.getUserById(userId);
            final Product product = productService.getProductById(productId);

            carts.add(Cart.builder()
                    .user(user)
                    .product(product)
                    .amount(1)
                    .build());

            return product;
        }

        return null;
    }

    @Override
    public boolean removeProductFromUserCartById(int productId, int userId) {
        final Set<Cart> userCart = getCartsByUserId(userId);

        return userCart.removeIf(cart -> cart.getProduct().getId() == productId);
    }

    @Override
    public int updateProductAmountByIs(int amount, int productId, int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProductAmountByIs'");
    }
}
