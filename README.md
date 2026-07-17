[logo]: https://github.com/HelpChat/DeluxeMenus/assets/52609756/f24ac57d-98db-4d57-a723-791a2654e73f

[issues]: https://github.com/HelpChat/DeluxeMenus/issues
[licenseImg]: https://img.shields.io/github/license/helpchat/deluxemenus?&logo=github
[license]: https://github.com/HelpChat/DeluxeMenus/blob/master/LICENSE

[bstatsImg]: https://img.shields.io/bstats/servers/445
[bstats]: https://bstats.org/plugin/bukkit/DeluxeMenus/445

[discordImg]: https://img.shields.io/discord/164280494874165248?color=5562e9&logo=discord&logoColor=white
[discord]: https://helpch.at/discord
[spigot]: https://www.spigotmc.org/resources/11734/

[ci]: http://ci.extendedclip.com/job/DeluxeMenus/
[ciImg]: http://ci.extendedclip.com/buildStatus/icon?job=DeluxeMenus

[contributing]: https://github.com/HelpChat/DeluxeMenus/blob/main/CONTRIBUTING.md

[![logo]][spigot]

[![ciImg]][ci] [![bstatsImg]][bstats] [![discordImg]][discord] [![licenseImg]][license] [![GitBook](https://img.shields.io/static/v1?message=Documented%20on%20GitBook&logo=gitbook&logoColor=ffffff&label=%20&labelColor=5c5c5c&color=3F89A1)](https://wiki.helpch.at/helpchat-plugins/deluxemenus)


# Information
[DeluxeMenus][spigot] is the all in one inventory GUI menu plugin!
You can create GUI menus that open with custom commands that will show stats or perform actions specific to the player who opened it. Your menus are fully configurable. You can create menus that show specific items to different players, or perform different actions depending on what javascript requirement they have for the specific slot in a certain GUI.

DeluxeMenus depends on [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/).

## Contribute
If you would like to contribute towards DeluxeMenus should you take a look at our [Contributing file][contributing] for the ins and outs on how you can do that and what you need to keep in mind.

## Support
- [Issue Tracker][issues]
- [Discord Support][discord]

## Quick Links
- [Wiki](https://wiki.helpch.at/clips-plugins/deluxemenus/)
- [CI Server][ci]
- [Spigot Page][spigot]
- [Plugin Statistics][bstats]

# Patched Build Notes

This fork contains the upstream DeluxeMenus code plus custom patches for Orven's build.

## Minecraft / Spigot Target

The patched build targets:

```text
org.spigotmc:spigot-api:26.1.2-R0.1-SNAPSHOT
```

The official Spigot snapshots repository is included in Gradle:

```text
https://hub.spigotmc.org/nexus/content/repositories/snapshots/
```

The code was also checked against `spigot-api:1.21.11-R0.1-SNAPSHOT` and compiled successfully.

## Menu Close Control

Menus support a new optional root setting:

```yml
can-esc-close: true
```

Behavior:

- Default: `true`.
- If omitted, menus keep the original behavior.
- If set to `false`, a player closing the menu manually with ESC or inventory close will have the menu opened again immediately.

Example:

```yml
menu_title: '&6Main Menu'
open_command: menu
can-esc-close: false
size: 27
```

## Close Commands On Manual Close

`close_commands` now run when the player manually closes the menu, not only when a menu item executes `[close]`.

This applies to:

- menu items using `[close]`
- manual player close through ESC / inventory close

## Dynamic Title Action

A new action is available:

```yml
- '[title] &eNew menu title'
```

It changes the title of the currently open DeluxeMenus inventory.

Implementation behavior:

- First attempts to update the title dynamically through `InventoryView#setTitle(...)`.
- Falls back to reopening the inventory with the new title if the server implementation does not support direct title updates for that inventory view.

## Dynamic Item Slots

Menu items can now use a placeholder as their `slot` value when that placeholder returns a number:

```yml
items:
  dynamic_item:
    material: DIAMOND
    slot: '%some_slot_placeholder%'
    slot_fallback: 13
```

Supported fallback keys:

```yml
slot_fallback: 13
slot-fallback: 13
fallback_slot: 13
```

Behavior:

- Static `slot: 10` still works as before.
- Existing `slots:` lists and ranges still work as before.
- Dynamic `slot: '%placeholder%'` is resolved per player when the menu opens or refreshes.
- If the placeholder returns a non-number or a slot outside the menu size, the fallback slot is used.
- Click handling uses the resolved dynamic slot, not only the fallback slot.

## Build Output

The patched jar is built with:

```powershell
.\gradlew.bat clean shadowJar
```

Common local artifact names:

```text
build/libs/DeluxeMenus-1.14.2-DEV-null.jar
build/libs/DeluxeMenus-1.14.2-ORV-patched.jar
build/libs/DeluxeMenus-1.14.2-ORV-patched-MC-26.1.2.jar
```
