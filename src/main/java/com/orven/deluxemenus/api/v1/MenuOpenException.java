package com.orven.deluxemenus.api.v1;

/** Thrown when a menu cannot be opened through the public API. */
public class MenuOpenException extends RuntimeException {

    public MenuOpenException(final String message) {
        super(message);
    }
}
