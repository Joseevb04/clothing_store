package es.dws.clothing_store.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import es.dws.clothing_store.model.Login;
import es.dws.clothing_store.model.RegisterForm;
import es.dws.clothing_store.model.User;
import es.dws.clothing_store.service.ProductService;
import es.dws.clothing_store.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final ProductService productService;

    private static Optional<User> loggedUser = Optional.empty();

    @GetMapping({ "/", "/home", "/index" })
    public String showHome(final Model model) {

        model.addAttribute("products", productService.getProducts());
        return loggedUser.isPresent()
                ? "indexView"
                : "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin() {
        return loggedUser.isEmpty()
                ? "loginView"
                : "redirect:/home";
    }

    @GetMapping("/register")
    public String showRegister() {
        return loggedUser.isEmpty()
                ? "registerView"
                : "redirect:/home";
    }

    @PostMapping("/sec/login")
    public String loginUser(@Valid Login login) {
        loggedUser = Optional.of(userService.loginUser(login));
        return "redirect:/home";
    }

    @PostMapping("/sec/register")
    public String registerUser(@Valid RegisterForm form) {
        loggedUser = Optional.of(userService.registerUser(form));
        return "redirect:/home";
    }

}
