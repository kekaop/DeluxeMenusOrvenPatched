package com.extendedclip.deluxemenus.api;

import com.extendedclip.deluxemenus.DeluxeMenus;
import com.extendedclip.deluxemenus.menu.Menu;
import com.extendedclip.deluxemenus.menu.MenuHolder;
import com.orven.deluxemenus.api.v1.DeluxeMenusApi;
import com.orven.deluxemenus.api.v1.MenuNotFoundException;
import com.orven.deluxemenus.api.v1.MenuSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public final class DeluxeMenusApiImpl implements DeluxeMenusApi {

    private final DeluxeMenus plugin;

    public DeluxeMenusApiImpl(final DeluxeMenus plugin) {
        this.plugin = plugin;
    }

    @Override
    public MenuSession openMenu(final Player player, final String menuId) {
        ensureMainThread();
        if (player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }
        if (menuId == null || menuId.trim().isEmpty()) {
            throw new IllegalArgumentException("menuId cannot be null or empty");
        }

        final Menu menu = Menu.getMenuByName(menuId).orElseThrow(() -> new MenuNotFoundException(menuId));
        return new MenuSessionImpl(plugin, menu.openMenuForApi(player));
    }

    @Override
    public void closeMenu(final Player player) {
        ensureMainThread();
        if (player != null) {
            Menu.getMenuHolder(player).ifPresent(holder -> Menu.closeMenu(plugin, player, true, false, com.orven.deluxemenus.api.v1.CloseReason.API));
        }
    }

    @Override
    public Optional<MenuSession> getSession(final Player player) {
        ensureMainThread();
        if (player == null) {
            return Optional.empty();
        }
        return Menu.getMenuHolder(player).map(holder -> new MenuSessionImpl(plugin, holder));
    }

    @Override
    public boolean isOpen(final Player player, final String menuId) {
        ensureMainThread();
        return player != null && menuId != null && Menu.isInMenu(player, menuId);
    }

    private void ensureMainThread() {
        if (!Bukkit.isPrimaryThread()) {
            throw new IllegalStateException("DeluxeMenus API must be called from the Bukkit main server thread.");
        }
    }
}
