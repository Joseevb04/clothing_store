package es.dws.clothing_store.entity;

import java.util.Set;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProductEntity
 */
@Entity
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @NotBlank
    @Length(max = 50)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotBlank
    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @NotNull
    @Min(0)
    @Column(name = "price", nullable = false)
    private Double price;

    @OneToMany(mappedBy = "id.product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartEntity> cartItems;
}