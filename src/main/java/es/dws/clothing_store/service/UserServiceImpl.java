package es.dws.clothing_store.service;

import es.dws.clothing_store.model.User;

import java.util.LinkedHashSet;
import java.util.Set;

/** UserServiceImpl */
public class UserServiceImpl implements UserService {

    private static final Set<User> users = new LinkedHashSet<User>();

    @Override
    public User getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not get user by that ID"));
    }
}
