package com.eczaneler.api.exceptions;

/**
 * Nöbetçi Eczane API temel istisna sınıfı.
 */
public class NobetciEczaneApiException extends RuntimeException {
    private final int statusCode;

    public NobetciEczaneApiException(String message) {
        this(message, 0, null);
    }

    public NobetciEczaneApiException(String message, int statusCode) {
        this(message, statusCode, null);
    }

    public NobetciEczaneApiException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
