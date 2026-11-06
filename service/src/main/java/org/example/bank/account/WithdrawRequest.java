package org.example.bank.account;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record WithdrawRequest(@NotNull @Positive @Digits(integer = 17, fraction = 2) BigDecimal amount) {
}
