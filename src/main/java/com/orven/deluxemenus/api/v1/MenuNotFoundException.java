package com.orven.deluxemenus.api.v1;

/** Thrown when the requested menu id is not loaded. */
public final class MenuNotFoundException extends MenuOpenException {

    public MenuNotFoundException(final String menuId) {
        super("DeluxeMenus menu is not loaded: " + menuId);
    }
}
