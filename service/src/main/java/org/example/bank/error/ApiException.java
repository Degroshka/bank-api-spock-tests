package org.example.bank.error;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String code;

    public ApiException(HttpStatus status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public static ApiException notFound(Long id) {
        return new ApiException(HttpStatus.NOT_FOUND, "ACCOUNT_NOT_FOUND", "Account " + id + " not found");
    }

    public static ApiException frozen(Long id) {
        return new ApiException(HttpStatus.CONFLICT, "ACCOUNT_FROZEN", "Account " + id + " is frozen");
    }

    public static ApiException insufficientFunds(Long id) {
        return new ApiException(HttpStatus.CONFLICT, "INSUFFICIENT_FUNDS", "Insufficient funds on account " + id);
    }
}
