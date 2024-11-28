package es.dws.clothing_store.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/** User */
@Data
@Builder
@AllArgsConstructor
public class User {

    private Integer id;

    @NotBlank private String name;

    @Email @NotBlank private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$.,\\-#])[A-Za-z\\d$.,\\-#]{8,12}$")
    private String password;
}
