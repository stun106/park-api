package com.stun106.parkapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioCreateDTO {
    @NotBlank
    @Email(message = "Formato do e-mail invalido.")
    private String username;
    @NotBlank
    @Size(min = 6, max = 6)
    private String password;
}
