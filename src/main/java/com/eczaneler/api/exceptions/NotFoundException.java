package com.eczaneler.api.exceptions;

public class NotFoundException extends NobetciEczaneApiException {
    public NotFoundException(String message) {
        super(message, 404);
    }
}
