package org.example.bank.account;

public record TransferResponse(AccountResponse from, AccountResponse to) {
}
