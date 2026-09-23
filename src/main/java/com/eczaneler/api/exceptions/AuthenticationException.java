package com.eczaneler.api.exceptions;

public class AuthenticationException extends NobetciEczaneApiException {
    public AuthenticationException(String message) {
        super(message, 401);
    }
}
