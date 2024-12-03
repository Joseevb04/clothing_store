package es.dws.clothing_store.mapper;

import es.dws.clothing_store.entity.UserEntity;
import es.dws.clothing_store.model.User;

/**
 * UserMapper
 */
public class UserMapper {

    private UserMapper() {
    }

    public static User mapUser(UserEntity user) {
        return User.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static UserEntity mapUser(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
