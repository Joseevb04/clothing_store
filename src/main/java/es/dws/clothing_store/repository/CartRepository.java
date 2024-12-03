package es.dws.clothing_store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.dws.clothing_store.entity.CartEntity;
import es.dws.clothing_store.entity.keys.CartEntityKey;

/**
 * CartRepository
 */
@Repository
public interface CartRepository extends JpaRepository<CartEntity, CartEntityKey> {

    List<CartEntity> findAllById_User_Id(Integer userId);

}
