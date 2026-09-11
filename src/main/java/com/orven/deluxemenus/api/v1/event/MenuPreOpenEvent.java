package com.orven.deluxemenus.api.v1.event;

import com.orven.deluxemenus.api.v1.OpenReason;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public final class MenuPreOpenEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final String menuId;
    private final OpenReason reason;
    private boolean cancelled;

    public MenuPreOpenEvent(final @NotNull Player player, final @NotNull String menuId, final @NotNull OpenReason reason) {
        super(player);
        this.menuId = menuId;
        this.reason = reason;
    }

    public String getMenuId() {
        return menuId;
    }

    public OpenReason getReason() {
        return reason;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }
}
