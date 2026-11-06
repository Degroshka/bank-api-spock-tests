package org.example.bank.account;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateAccountRequest(
        @NotBlank String owner,
        @NotNull @PositiveOrZero @Digits(integer = 17, fraction = 2) BigDecimal balance) {
}
