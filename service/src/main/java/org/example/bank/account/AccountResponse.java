package org.example.bank.account;

import java.math.BigDecimal;

public record AccountResponse(Long id, String owner, BigDecimal balance, boolean frozen) {

    public static AccountResponse from(Account account) {
        return new AccountResponse(account.getId(), account.getOwner(), account.getBalance(), account.isFrozen());
    }
}
