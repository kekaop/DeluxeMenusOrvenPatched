package com.orven.deluxemenus.api.v1;

import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * Public integration API for DeluxeMenus.
 *
 * <p>All methods must be called from the Bukkit main thread.</p>
 */
public interface DeluxeMenusApi {

    MenuSession openMenu(Player player, String menuId);

    void closeMenu(Player player);

    Optional<MenuSession> getSession(Player player);

    boolean isOpen(Player player, String menuId);
}
