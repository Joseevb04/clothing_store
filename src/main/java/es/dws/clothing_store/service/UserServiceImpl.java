package es.dws.clothing_store.service;

import es.dws.clothing_store.model.Login;
import es.dws.clothing_store.model.RegisterForm;
import es.dws.clothing_store.model.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

/** UserServiceImpl */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final CSVService csvService;

    private static final Set<User> users = new LinkedHashSet<User>();

    private static final String DATA_FILE = "users.csv";

    private static int counter = 1;

    @PostConstruct
    private void loadUsersFromCsv() {
        try {
            List<User> loadedUsers = csvService.readFromCsv(DATA_FILE, this::parseUserFromCsvLine);
            users.addAll(loadedUsers);
            counter = users.stream().mapToInt(User::getId).max().orElse(0) + 1;
            log.info("Loaded {} users from CSV", loadedUsers.size());
        } catch (Exception e) {
            log.error("Error loading users from CSV", e);
            csvService.createCsvFile(DATA_FILE);
        }

        users.forEach(u -> log.info(u.toString()));
    }

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
        log.info("User name {}", login.getUsername());
        log.info("User password {}", login.getPassword());
        return users.stream()
                .filter(u -> u.getUsername().equals(login.getUsername()) &&
                        u.getPassword().equals(login.getPassword()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    @Override
    public User registerUser(RegisterForm data) {
        users.stream()
                .filter(user -> user.getUsername().equals(data.getUsername())
                        || user.getEmail().equals(data.getEmail()))
                .findFirst()
                .ifPresent(_ -> {
                    throw new IllegalArgumentException("A user with the same username or email already exists.");
                });

        User newUser = User.builder()
                .id(counter++)
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .username(data.getUsername())
                .email(data.getEmail())
                .password(data.getPassword())
                .build();

        if (!users.add(newUser)) {
            throw new RuntimeException("Failed to register user.");
        }

        writeUsersToCsv();

        return newUser;
    }

    private void writeUsersToCsv() {
        try {
            csvService.writeObjectsToCsv(users.stream().toList(), DATA_FILE);

            log.info("Users written successfully to {}", DATA_FILE);
        } catch (Exception e) {
            log.error("Error writing users to CSV", e);
            throw new RuntimeException("Failed to write users to CSV", e);
        }
    }

    private User parseUserFromCsvLine(String line) {
        String[] fields = line.split(",");
        return User.builder()
                .id(Integer.parseInt(fields[0].trim()))
                .firstName(fields[1].trim())
                .lastName(fields[2].trim())
                .username(fields[3].trim())
                .email(fields[4].trim())
                .password(fields[5].trim())
                .build();
    }
}
