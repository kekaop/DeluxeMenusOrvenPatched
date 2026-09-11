package com.orven.deluxemenus.api.v1;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/** A per-player view of one currently open DeluxeMenus menu. */
public interface MenuSession {

    Player getPlayer();

    String getMenuId();

    int getSize();

    Component getTitle();

    void setTitle(Component title);

    void setTitle(String legacyOrMiniMessageTitle);

    void setItem(int slot, ItemStack item);

    void setItem(int slot, ItemStack item, String actionId);

    void removeItem(int slot);

    void clearSlot(int slot);

    ItemStack getItem(int slot);

    void refresh();

    void close();

    boolean isOpen();
}
