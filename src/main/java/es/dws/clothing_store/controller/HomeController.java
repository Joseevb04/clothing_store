package es.dws.clothing_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.dws.clothing_store.service.ProductService;
import es.dws.clothing_store.utils.UserSessionUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    // managing of sessions for passing the User object through controllers
    private final HttpSession session;

    @GetMapping({ "/", "/home", "/index" })
    public String showHome(final Model model) {

        model.addAttribute("products", productService.getProducts());
        return UserSessionUtils.getLoggedUser(session).isPresent()
                ? "indexView"
                : "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin() {
        return UserSessionUtils.getLoggedUser(session).isEmpty()
                ? "loginView"
                : "redirect:/home";
    }

    @GetMapping("/register")
    public String showRegister() {
        return UserSessionUtils.getLoggedUser(session).isEmpty()
                ? "registerView"
                : "redirect:/home";
    }

}
