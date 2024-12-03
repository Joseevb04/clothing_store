package es.dws.clothing_store.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.dws.clothing_store.service.CartService;
import es.dws.clothing_store.utils.UserSessionUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * CartController
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final HttpSession session;

    @GetMapping
    public String showCart(final Model model) {
        return UserSessionUtils.getLoggedUser(session).map(u -> {
            Map<String, Object> attributes = Map.of(
                    "products", cartService.getCartsByUserId(u.getId()),
                    "total", String.format("%.2f", cartService.getUserCartTotal(u.getId())));

            model.addAllAttributes(attributes);
            return "cartView";
        }).orElse("redirect:/login");
    }

    @GetMapping("/add/{productId}")
    public String addProductToCart(@PathVariable final Integer productId) {
        return UserSessionUtils.getLoggedUser(session).map(u -> {
            cartService.addProductToUserCartById(productId, u.getId());
            return "redirect:/?success=1";
        }).orElse("rediect:/login");
    }
}
