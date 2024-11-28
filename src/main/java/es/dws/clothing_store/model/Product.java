package es.dws.clothing_store.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.hibernate.validator.constraints.Length;

/** Product */
@Data
@Builder
@AllArgsConstructor
public class Product {

    private Integer id;

    @NotBlank
    @Length(max = 50)
    private String name;

    @NotBlank private String description;

    @NotNull
    @Min(0)
    private Double price;
}
