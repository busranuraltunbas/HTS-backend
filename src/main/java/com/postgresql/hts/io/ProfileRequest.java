package com.postgresql.hts.io;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ProfileRequest {
    @NotBlank(message = "Kullanıcı adı boş girilemez")
    private String name;

    @Email(message = "Geçerli bir e-posta girin")
    @NotNull(message = "E-posta boş girilemez")
    private String email;

    @Size(min = 6, message = "Şifre en az 6 karakter olmalı")
    private String password;
}

