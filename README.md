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

# DeluxeMenus Patched - Краткая документация изменений

## 1) Новый параметр меню: `can-esc-close`

Добавлен новый параметр в конфиг меню:

```yml
can-esc-close: true
```

- По умолчанию: `true` (если параметр не указан).
- Если `false`: при попытке игрока закрыть меню через `ESC` (или закрытие инвентаря игроком) меню сразу открывается снова.

Пример:

```yml
menu_title: '&6Главное меню'
open_command: menu
can-esc-close: false
size: 27
```

## 2) Изменено поведение `close_commands`

Раньше `close_commands` надежно отрабатывали в основном при действиях типа `[close]`.
Теперь `close_commands` также выполняются, когда игрок закрывает меню вручную (ESC/закрытие инвентаря).

Итог:
- `[close]` -> `close_commands` выполняются.
- Ручное закрытие меню игроком -> `close_commands` тоже выполняются.

## 3) Новый action: `[title]`

Добавлен новый action для смены заголовка открытого меню во время работы:

```yml
- '[title] &eНовый заголовок'
```

Что делает:
- Пытается динамически сменить title через `InventoryView#setTitle(...)`.
- Если серверная реализация не поддерживает динамическую смену для конкретного окна, используется fallback с переоткрытием меню с новым title.

## 4) Совместимость

- Старые меню продолжают работать без изменений.
- `can-esc-close` необязателен.
- При отсутствии `can-esc-close` поведение остается прежним (закрытие по ESC разрешено).
