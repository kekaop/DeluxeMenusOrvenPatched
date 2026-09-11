package com.orven.deluxemenus.api.v1.event;

import com.orven.deluxemenus.api.v1.MenuSession;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MenuClickEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final MenuSession session;
    private final int slot;
    private final ClickType clickType;
    private final ItemStack item;
    private final String actionId;
    private boolean cancelled;

    public MenuClickEvent(final @NotNull Player player, final @NotNull MenuSession session, final int slot,
                          final @NotNull ClickType clickType, final @NotNull ItemStack item,
                          final @Nullable String actionId) {
        this.player = player;
        this.session = session;
        this.slot = slot;
        this.clickType = clickType;
        this.item = item.clone();
        this.actionId = actionId;
    }

    public Player getPlayer() {
        return player;
    }

    public MenuSession getSession() {
        return session;
    }

    public int getSlot() {
        return slot;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public @Nullable String getActionId() {
        return actionId;
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
