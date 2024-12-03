package es.dws.clothing_store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.dws.clothing_store.entity.UserEntity;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsernameAndPassword(String username, String password);

    Optional<UserEntity> findByUsernameAndEmail(String username, String email);

}
