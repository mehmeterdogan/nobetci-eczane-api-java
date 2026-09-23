package com.eczaneler.api.exceptions;

public class ForbiddenException extends NobetciEczaneApiException {
    public ForbiddenException(String message) {
        super(message, 403);
    }
}
