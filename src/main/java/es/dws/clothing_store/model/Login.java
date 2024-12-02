package es.dws.clothing_store.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Login
 */
@Data
@Builder
@AllArgsConstructor
public class Login {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
