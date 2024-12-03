package es.dws.clothing_store.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import es.dws.clothing_store.entity.CartEntity;
import es.dws.clothing_store.entity.ProductEntity;
import es.dws.clothing_store.entity.UserEntity;
import es.dws.clothing_store.entity.keys.CartEntityKey;
import es.dws.clothing_store.mapper.CartMapper;
import es.dws.clothing_store.mapper.ProductMapper;
import es.dws.clothing_store.mapper.UserMapper;
import es.dws.clothing_store.model.Cart;
import es.dws.clothing_store.model.Product;
import es.dws.clothing_store.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** CartServiceImpl */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final UserService userService;
    private final ProductService productService;

    @Override
    public Set<Cart> getCartsByUserId(int id) {
        return cartRepository.findAllById_User_Id(id)
                .stream()
                .map(CartMapper::mapCart)
                .collect(Collectors.toSet());
    }

    @Override
    public Product addProductToUserCartById(int productId, int userId) {
        final Set<Cart> userCart = getCartsByUserId(userId);

        if (userCart.stream().noneMatch(cart -> cart.getProduct().getId() == productId)) {

            final UserEntity user = UserMapper.mapUser(userService.getUserById(userId));
            final ProductEntity product = ProductMapper.mapProduct(productService.getProductById(productId));

            final CartEntityKey key = new CartEntityKey(user, product);

            final CartEntity cart = CartEntity.builder()
                    .id(key)
                    .amount(1)
                    .build();

            cartRepository.save(cart);

            return ProductMapper.mapProduct(product);
        }

        throw new RuntimeException("Product already in Cart");
    }

    @Override
    public boolean removeProductFromUserCartById(int productId, int userId) {
        final UserEntity user = UserMapper.mapUser(userService.getUserById(userId));
        final ProductEntity product = ProductMapper.mapProduct(productService.getProductById(productId));

        final CartEntityKey key = new CartEntityKey(user, product);

        return cartRepository.findById(key).map(c -> {
            cartRepository.delete(c);
            return true;
        }).orElse(false);
    }

    @Transactional
    @Override
    public int updateProductAmountByIs(int amount, int productId, int userId) {
        final UserEntity user = UserMapper.mapUser(userService.getUserById(userId));
        final ProductEntity product = ProductMapper.mapProduct(productService.getProductById(productId));

        final CartEntityKey key = new CartEntityKey(user, product);

        return cartRepository.findById(key).map(cart -> {
            cart.setAmount(amount);
            cartRepository.save(cart);
            return amount;
        }).orElseThrow(() -> new RuntimeException("Cart entry not found for the given user and product"));
    }

    @Override
    public double getUserCartTotal(int userId) {
        return getCartsByUserId(userId).stream()
                .mapToDouble(cart -> cart.getProduct().getPrice() * cart.getAmount())
                .sum();
    }
}
