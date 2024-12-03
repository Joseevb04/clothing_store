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

    @NotBlank(message = "Product name cannot be empty")
    @Length(max = 50, message = "Product name cannot be longer than 50 characters")
    private String name;

    @NotBlank(message = "Product description cannot be empty")
    private String description;

    @NotNull(message = "Product price cannot be null")
    @Min(value = 0, message = "Product price cannot be less than 0")
    private Double price;
}
