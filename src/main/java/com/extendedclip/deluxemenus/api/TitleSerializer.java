package com.extendedclip.deluxemenus.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class TitleSerializer {

    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacySection();
    private static final LegacyComponentSerializer AMPERSAND = LegacyComponentSerializer.legacyAmpersand();

    private TitleSerializer() {
    }

    public static String serialize(final Component component) {
        return LEGACY.serialize(component);
    }

    public static Component deserialize(final String title) {
        return LEGACY.deserialize(title);
    }

    public static String parse(final String title) {
        if (title.indexOf('<') >= 0 && title.indexOf('>') > title.indexOf('<')) {
            return serialize(MiniMessage.miniMessage().deserialize(title));
        }
        return serialize(AMPERSAND.deserialize(title));
    }
}
