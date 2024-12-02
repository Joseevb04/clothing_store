package es.dws.clothing_store.service;

import es.dws.clothing_store.model.Login;
import es.dws.clothing_store.model.RegisterForm;
import es.dws.clothing_store.model.User;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

/** UserServiceImpl */
@Service
public class UserServiceImpl implements UserService {

    private static final Set<User> users = new LinkedHashSet<User>();

    private static int counter = 0;

    @Override
    public User getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not get user by that ID"));
    }

    @Override
    public User addUser(User user) {
        user.setId(counter++);

        if (users.add(user)) {
            return user;
        }

        throw new RuntimeException("Failed to add user");
    }

    @Override
    public User loginUser(Login login) {
        return users.stream()
                .filter(u -> u.getUsername().equals(login.getUsername()) &&
                        u.getPassword().equals(login.getPassword()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    @Override
    public User registerUser(RegisterForm data) {
        if (users.stream().anyMatch(user -> user.getUsername().equals(data.getUsername()) ||
                user.getEmail().equals(data.getEmail()))) {
            throw new IllegalArgumentException("A user with the same username or email already exists.");
        }

        return Optional.of(User.builder()
                .id(counter++)
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .username(data.getUsername())
                .email(data.getEmail())
                .password(data.getPassword())
                .build())
                .filter(users::add)
                .orElseThrow(() -> new RuntimeException("Failed to register"));
    }
}
