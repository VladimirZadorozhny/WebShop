package be.vdab.webshop.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record NewUserRec(
        @NotBlank String firstname,
        @NotBlank String lastname,
        @NotBlank String email,
        @NotBlank String password

) {}
