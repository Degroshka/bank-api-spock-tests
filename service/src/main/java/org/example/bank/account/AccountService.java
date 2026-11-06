package org.example.bank.account;

import org.example.bank.error.ApiException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account create(CreateAccountRequest request) {
        return repository.save(new Account(request.owner(), request.balance()));
    }

    public Account get(Long id) {
        return repository.findById(id).get();
    }

    public Account deposit(Long id, DepositRequest request) {
        Account account = find(id);
        requireNotFrozen(account);
        account.setBalance(account.getBalance().add(request.amount()));
        return repository.save(account);
    }

    public Account withdraw(Long id, WithdrawRequest request) {
        Account account = find(id);
        requireNotFrozen(account);
        if (request.amount().compareTo(account.getBalance()) >= 0) {
            throw ApiException.insufficientFunds(id);
        }
        account.setBalance(account.getBalance().subtract(request.amount()));
        return repository.save(account);
    }

    public Account freeze(Long id) {
        Account account = find(id);
        account.setFrozen(true);
        return repository.save(account);
    }

    public Account unfreeze(Long id) {
        Account account = find(id);
        account.setFrozen(false);
        return repository.save(account);
    }

    public TransferResponse transfer(TransferRequest request) {
        Account from = find(request.fromId());
        Account to = find(request.toId());
        requireNotFrozen(from);
        if (request.amount().compareTo(from.getBalance()) > 0) {
            throw ApiException.insufficientFunds(from.getId());
        }
        from.setBalance(from.getBalance().subtract(request.amount()));
        repository.save(from);
        requireNotFrozen(to);
        to.setBalance(to.getBalance().add(request.amount()));
        repository.save(to);
        return new TransferResponse(AccountResponse.from(from), AccountResponse.from(to));
    }

    private Account find(Long id) {
        return repository.findById(id).orElseThrow(() -> ApiException.notFound(id));
    }

    private void requireNotFrozen(Account account) {
        if (account.isFrozen()) {
            throw ApiException.frozen(account.getId());
        }
    }
}
