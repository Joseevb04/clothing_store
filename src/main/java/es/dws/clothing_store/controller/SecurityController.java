package es.dws.clothing_store.controller;

import es.dws.clothing_store.model.Login;
import es.dws.clothing_store.model.RegisterForm;
import es.dws.clothing_store.model.User;
import es.dws.clothing_store.service.UserService;
import es.dws.clothing_store.utils.UserSessionUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SecurityController manages the user authentication and registration
 * processes,
 * including login, logout, and user registration.
 * It also handles session management for the logged-in user.
 */
@Controller
@RequestMapping("/sec")
@RequiredArgsConstructor
public class SecurityController {

    private final HttpSession session;
    private final UserService userService;

    /**
     * Logs out the user by removing the "loggedUser" attribute from the session.
     * 
     * @return a redirect to the home page ("/").
     */
    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("loggedUser");
        return "redirect:/";
    }

    /**
     * Logs in the user by validating the provided login credentials and
     * storing the logged-in user in the session.
     * 
     * @param login the login credentials provided by the user.
     * @return a redirect to the home page ("/home").
     */
    @PostMapping("/login")
    public String loginUser(@Valid Login login) {
        User loggedInUser = userService.loginUser(login);
        session.setAttribute("loggedUser", loggedInUser);
        return "redirect:/home";
    }

    /**
     * Registers a new user by processing the registration form and storing
     * the new user in the session.
     * 
     * @param form the registration form provided by the user.
     * @return a redirect to the home page ("/home").
     */
    @PostMapping("/register")
    public String registerUser(@Valid RegisterForm form) {
        User newUser = userService.registerUser(form);
        session.setAttribute("loggedUser", newUser);
        return "redirect:/home";
    }
}
