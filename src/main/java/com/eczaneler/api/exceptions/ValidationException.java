package com.eczaneler.api.exceptions;

public class ValidationException extends NobetciEczaneApiException {
    public ValidationException(String message) {
        super(message, 400);
    }
}
