package es.dws.clothing_store.service;

import org.springframework.stereotype.Service;

import es.dws.clothing_store.entity.UserEntity;
import es.dws.clothing_store.mapper.UserMapper;
import es.dws.clothing_store.model.Login;
import es.dws.clothing_store.model.RegisterForm;
import es.dws.clothing_store.model.User;
import es.dws.clothing_store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** UserServiceImpl */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .map(UserMapper::mapUser)
                .orElseThrow(() -> new RuntimeException("Could not get user by that ID"));
    }

    @Override
    public User addUser(User user) {
        return UserMapper.mapUser(userRepository.save(UserMapper.mapUser(user)));
    }

    @Override
    public User loginUser(Login login) {
        log.info(login.getUsername());
        log.info(login.getPassword());
        return userRepository.findByUsernameAndPassword(login.getUsername(), login.getPassword())
                .map(UserMapper::mapUser)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    @Override
    public User registerUser(RegisterForm data) {
        userRepository.findByUsernameAndEmail(data.getUsername(), data.getEmail())
                .ifPresent(_ -> {
                    throw new IllegalArgumentException("A user with the same username or email already exists.");
                });

        return UserMapper.mapUser(userRepository.save(UserEntity.builder()
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .username(data.getUsername())
                .email(data.getEmail())
                .password(data.getPassword())
                .build()));
    }

}
