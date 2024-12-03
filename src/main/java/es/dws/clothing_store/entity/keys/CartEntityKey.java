package es.dws.clothing_store.entity.keys;

import java.io.Serializable;

import es.dws.clothing_store.entity.ProductEntity;
import es.dws.clothing_store.entity.UserEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CartEntityKey
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CartEntityKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private ProductEntity product;

}
