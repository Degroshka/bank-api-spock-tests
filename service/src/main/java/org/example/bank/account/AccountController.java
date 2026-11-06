package org.example.bank.account;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
        Account account = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountResponse.from(account));
    }

    @GetMapping("/accounts/{id}")
    public AccountResponse get(@PathVariable Long id) {
        return AccountResponse.from(service.get(id));
    }

    @PostMapping("/accounts/{id}/deposit")
    public AccountResponse deposit(@PathVariable Long id, @Valid @RequestBody DepositRequest request) {
        return AccountResponse.from(service.deposit(id, request));
    }

    @PostMapping("/accounts/{id}/withdraw")
    public AccountResponse withdraw(@PathVariable Long id, @Valid @RequestBody WithdrawRequest request) {
        return AccountResponse.from(service.withdraw(id, request));
    }

    @PostMapping("/accounts/{id}/freeze")
    public AccountResponse freeze(@PathVariable Long id) {
        return AccountResponse.from(service.freeze(id));
    }

    @PostMapping("/accounts/{id}/unfreeze")
    public AccountResponse unfreeze(@PathVariable Long id) {
        return AccountResponse.from(service.unfreeze(id));
    }

    @PostMapping("/transfers")
    public TransferResponse transfer(@Valid @RequestBody TransferRequest request) {
        return service.transfer(request);
    }
}
