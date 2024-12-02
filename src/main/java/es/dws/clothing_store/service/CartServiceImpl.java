package es.dws.clothing_store.service;

import es.dws.clothing_store.model.Cart;
import es.dws.clothing_store.model.Product;
import es.dws.clothing_store.model.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** CartServiceImpl */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CSVService csvService;
    private final UserService userService;
    private final ProductService productService;

    private static final Set<Cart> carts = new LinkedHashSet<Cart>();
    private static final String DATA_FILE = "carts.csv";

    @PostConstruct
    private void loadCartsFromCsv() {
        try {
            List<Cart> loadedCarts = csvService.readFromCsv(DATA_FILE, this::parseCartsFromCsv);
            carts.addAll(loadedCarts);
            log.info("Loaded {} users from CSV", loadedCarts.size());
        } catch (Exception e) {
            log.error("Error loading users from CSV", e);
            csvService.createCsvFile(DATA_FILE);
        }

        carts.forEach(u -> log.info(u.toString()));
    }

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

            final Cart cart = Cart.builder()
                    .user(user)
                    .product(product)
                    .amount(1)
                    .build();

            carts.add(cart);

            writeCartsToCsv();

            return product;
        }

        throw new RuntimeException("Product already in Cart");
    }

    @Override
    public boolean removeProductFromUserCartById(int productId, int userId) {
        final Set<Cart> userCart = getCartsByUserId(userId);

        return userCart.removeIf(cart -> cart.getProduct().getId() == productId);
    }

    @Override
    public int updateProductAmountByIs(int amount, int productId, int userId) {
        final Set<Cart> userCart = getCartsByUserId(userId);

        userCart.stream().filter(cart -> cart.getProduct().getId() == productId).map(cart -> {
            cart.setAmount(amount);
            return cart;
        });

        return amount;
    }

    private void writeCartsToCsv() {
        try {
            var cartSummaries = carts.stream()
                    .map(cart -> Arrays.asList(
                            cart.getUser().getId(),
                            cart.getProduct().getId(),
                            cart.getAmount()))
                    .collect(Collectors.toList());

            csvService.writeObjectsToCsv(cartSummaries, DATA_FILE);

            log.info("Users written successfully to {}", DATA_FILE);
        } catch (Exception e) {
            log.error("Error writing users to CSV", e);
            throw new RuntimeException("Failed to write users to CSV", e);
        }
    }

    private Cart parseCartsFromCsv(String line) {
        String[] fields = line.split(",");

        Integer userId = Integer.parseInt(fields[0].trim());
        Integer productId = Integer.parseInt(fields[1].trim());
        Integer amount = Integer.parseInt(fields[2].trim());

        return Cart.builder()
                .user(userService.getUserById(userId))
                .product(productService.getProductById(productId))
                .amount(amount)
                .build();
    }
}
