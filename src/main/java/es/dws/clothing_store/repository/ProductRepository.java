package es.dws.clothing_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.dws.clothing_store.entity.ProductEntity;

/**
 * ProductRepository
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

}
