# DeluxeMenus Integration API

This fork provides a stable, versioned API for plugins that need to use DeluxeMenus as a GUI layer. The public API is in `com.orven.deluxemenus.api.v1` and does not expose DeluxeMenus internal classes or require PlaceholderAPI in the consuming plugin.

The examples below target Minecraft 1.21.11 and Java 11+.

## Getting the API

Declare DeluxeMenus as a soft dependency if the integration is optional:

```yml
softdepend: [DeluxeMenus]
```

Resolve the service at runtime. If DeluxeMenus is not installed or is disabled, the registration is absent and the integration should keep working without menus:

```java
RegisteredServiceProvider<DeluxeMenusApi> registration =
        Bukkit.getServicesManager().getRegistration(DeluxeMenusApi.class);
if (registration == null) {
    return;
}

DeluxeMenusApi api = registration.getProvider();
```

Every API method must be called on the Bukkit main thread. Calling it asynchronously throws `IllegalStateException`; schedule the operation with `BukkitScheduler` first.

## Open, close, and inspect

```java
MenuSession session = api.openMenu(player, "ashfall_shop");

if (api.isOpen(player, "ashfall_shop")) {
    api.closeMenu(player);
}

Optional<MenuSession> current = api.getSession(player);
```

`openMenu` throws `MenuNotFoundException` if the menu is not loaded and `MenuOpenException` if an opening requirement, pre-open event, or menu state prevents opening. Opening another menu closes the previous DeluxeMenus session with reason `REPLACING_MENU`.

## MenuSession

`MenuSession` is scoped to one player and one open menu. All `ItemStack` values are copied when entering or leaving the API, so callers cannot mutate the inventory by retaining a reference.

```java
session.setItem(13, item);
session.setItem(14, item, "shop.buy.fox_ears");
session.removeItem(15);
session.clearSlot(16);

ItemStack current = session.getItem(13);
session.refresh();
session.close();
```

Slots must satisfy `0 <= slot < session.getSize()`, otherwise `IllegalArgumentException` is thrown. `setItem` rejects `null`; use `removeItem` or `clearSlot` to empty a slot. API slots are per-player overrides and survive `refresh()`. They can contain ordinary Bukkit items or items created by another plugin, including heads, PDC data, custom model data, components, localized text, and custom item implementations.

## Titles

Adventure components, legacy color codes, and MiniMessage are supported:

```java
session.setTitle(Component.text("Ashfall Shop"));
session.setTitle("&6Ashfall Shop");
session.setTitle("<gold>Ashfall Shop</gold>");
```

The implementation first tries `InventoryView#setTitle`. If the server implementation rejects a live title change, DeluxeMenus safely rebuilds the same inventory view and copies its contents. The current cursor and server-specific inventory state may not be fully preservable on that fallback, and open commands are not executed again.

## Action IDs and clicks

An API item can carry an integration-defined action ID without registering a temporary Bukkit command:

```java
session.setItem(13, foxEarsItem, "shop.buy.fox_ears");
```

Listen for `MenuClickEvent`:

```java
@EventHandler
public void onMenuClick(MenuClickEvent event) {
    if (!"shop.buy.fox_ears".equals(event.getActionId())) {
        return;
    }

    event.setCancelled(true);
    buyFoxEars(event.getPlayer());
}
```

The event exposes `getPlayer()`, `getSession()`, `getSlot()`, `getClickType()`, `getItem()`, and `getActionId()`. It is fired for API items and ordinary YAML menu items; the latter have a `null` action ID. Left, right, shift-left, shift-right, and middle clicks are supported. DeluxeMenus always cancels the underlying Bukkit inventory operation so menu items cannot be moved into or out of the player's inventory.

## Public events

The following events are available in `com.orven.deluxemenus.api.v1.event`:

- `MenuPreOpenEvent`: cancellable; contains player, menu id, and `OpenReason`.
- `MenuOpenEvent`: contains player, menu id, and `MenuSession`.
- `MenuClickEvent`: cancellable; contains click details and optional action ID.
- `MenuCloseEvent`: contains player, menu id, session, and `CloseReason`.

`OpenReason` values include `API`, `COMMAND`, `MENU_ACTION`, and `UNKNOWN`. `CloseReason` includes `MANUAL`, `COMMAND`, `REPLACING_MENU`, `PLAYER_QUIT`, `SERVER_SHUTDOWN`, `API`, and `UNKNOWN`.

The existing `DeluxeMenusPreOpenMenuEvent` and `DeluxeMenusOpenMenuEvent` remain available for compatibility.
