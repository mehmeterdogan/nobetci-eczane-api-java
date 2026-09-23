package com.eczaneler.api.exceptions;

public class RateLimitException extends NobetciEczaneApiException {
    public RateLimitException(String message) {
        super(message, 429);
    }
}
