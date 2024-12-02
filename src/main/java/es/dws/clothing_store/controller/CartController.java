package es.dws.clothing_store.controller;

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
 * ProductController
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
            model.addAttribute("products", cartService.getCartsByUserId(u.getId()));
            return "cartView";
        }).orElse("redirect:/login");
    }

    @GetMapping("/add/{userId}/{productId}")
    public String addProductToCart(@PathVariable final Integer userId, @PathVariable final Integer productId) {
        return "";
    }
}
