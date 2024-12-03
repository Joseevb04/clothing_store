package es.dws.clothing_store.entity;

import es.dws.clothing_store.entity.keys.CartEntityKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CartEntity
 */
@Entity
@Table(name = "carts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartEntity {

    @EmbeddedId
    private CartEntityKey id;

    @Column(nullable = false)
    private Integer amount;

}
