package com.extendedclip.deluxemenus.api;

import com.extendedclip.deluxemenus.DeluxeMenus;
import com.extendedclip.deluxemenus.menu.Menu;
import com.extendedclip.deluxemenus.menu.MenuHolder;
import com.orven.deluxemenus.api.v1.CloseReason;
import com.orven.deluxemenus.api.v1.MenuSession;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public final class MenuSessionImpl implements MenuSession {

    private final DeluxeMenus plugin;
    private final MenuHolder holder;

    public MenuSessionImpl(final DeluxeMenus plugin, final MenuHolder holder) {
        this.plugin = Objects.requireNonNull(plugin, "plugin");
        this.holder = Objects.requireNonNull(holder, "holder");
    }

    @Override
    public Player getPlayer() {
        return holder.getViewer();
    }

    @Override
    public String getMenuId() {
        return holder.getMenuName();
    }

    @Override
    public int getSize() {
        return holder.getInventory().getSize();
    }

    @Override
    public Component getTitle() {
        ensureMainThread();
        ensureOpen();
        return TitleSerializer.deserialize(holder.getViewer().getOpenInventory().getTitle());
    }

    @Override
    public void setTitle(final Component title) {
        ensureMainThread();
        Objects.requireNonNull(title, "title");
        ensureOpen();
        Menu.changeOpenMenuTitleRaw(holder.getViewer(), TitleSerializer.serialize(title));
    }

    @Override
    public void setTitle(final String legacyOrMiniMessageTitle) {
        ensureMainThread();
        Objects.requireNonNull(legacyOrMiniMessageTitle, "legacyOrMiniMessageTitle");
        ensureOpen();
        Menu.changeOpenMenuTitleRaw(holder.getViewer(), TitleSerializer.parse(legacyOrMiniMessageTitle));
    }

    @Override
    public void setItem(final int slot, final ItemStack item) {
        setItem(slot, item, null);
    }

    @Override
    public void setItem(final int slot, final ItemStack item, final String actionId) {
        ensureMainThread();
        Objects.requireNonNull(item, "item");
        validateSlot(slot);
        ensureOpen();
        holder.setApiItem(slot, item, actionId);
    }

    @Override
    public void removeItem(final int slot) {
        ensureMainThread();
        validateSlot(slot);
        ensureOpen();
        holder.removeApiItem(slot);
    }

    @Override
    public void clearSlot(final int slot) {
        removeItem(slot);
    }

    @Override
    public ItemStack getItem(final int slot) {
        ensureMainThread();
        validateSlot(slot);
        ensureOpen();
        if (holder.hasApiSlot(slot)) {
            final MenuHolder.ApiSlot apiSlot = holder.getApiSlot(slot).orElse(null);
            return apiSlot == null ? null : apiSlot.getItem();
        }
        final ItemStack item = holder.getInventory().getItem(slot);
        return item == null ? null : item.clone();
    }

    @Override
    public void refresh() {
        ensureMainThread();
        ensureOpen();
        holder.refreshMenu();
    }

    @Override
    public void close() {
        ensureMainThread();
        if (isOpen()) {
            Menu.closeMenu(plugin, holder.getViewer(), true, false, CloseReason.API);
        }
    }

    @Override
    public boolean isOpen() {
        return holder.getViewer().isOnline() && Menu.getMenuHolder(holder.getViewer()).orElse(null) == holder;
    }

    private void validateSlot(final int slot) {
        if (slot < 0 || slot >= getSize()) {
            throw new IllegalArgumentException("Slot must be between 0 and " + (getSize() - 1) + ": " + slot);
        }
    }

    private void ensureOpen() {
        if (!isOpen()) {
            throw new IllegalStateException("The DeluxeMenus session is no longer open.");
        }
    }

    private void ensureMainThread() {
        if (!Bukkit.isPrimaryThread()) {
            throw new IllegalStateException("DeluxeMenus API must be called from the Bukkit main thread.");
        }
    }
}
