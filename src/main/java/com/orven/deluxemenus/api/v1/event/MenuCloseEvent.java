package com.orven.deluxemenus.api.v1.event;

import com.orven.deluxemenus.api.v1.CloseReason;
import com.orven.deluxemenus.api.v1.MenuSession;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public final class MenuCloseEvent extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final MenuSession session;
    private final String menuId;
    private final CloseReason reason;

    public MenuCloseEvent(final @NotNull Player player, final @NotNull MenuSession session,
                          final @NotNull String menuId, final @NotNull CloseReason reason) {
        super(player);
        this.session = session;
        this.menuId = menuId;
        this.reason = reason;
    }

    public MenuSession getSession() {
        return session;
    }

    public String getMenuId() {
        return menuId;
    }

    public CloseReason getReason() {
        return reason;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }
}
