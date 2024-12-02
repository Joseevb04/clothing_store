package es.dws.clothing_store.utils;

import java.util.Optional;

import es.dws.clothing_store.model.User;
import jakarta.servlet.http.HttpSession;

/**
 * UserSessionUtils
 */
public class UserSessionUtils {

    private UserSessionUtils() {
    }

    /**
     * Retrieves the logged-in user from the session.
     *
     * @param session The current HTTP session
     * @return An Optional containing the User if present, otherwise empty
     */
    public static Optional<User> getLoggedUser(HttpSession session) {
        return Optional.ofNullable((User) session.getAttribute("loggedUser"));
    }

}
