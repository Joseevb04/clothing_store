package es.dws.clothing_store.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/** Cart */
@Data
@Builder
@AllArgsConstructor
public class Cart {

    @NotNull private User user;

    @NotNull private Product product;

    @Min(0)
    @NotNull
    private Integer amount;
}
