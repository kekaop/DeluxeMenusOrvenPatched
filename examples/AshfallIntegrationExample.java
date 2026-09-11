package example.ashfall;

import com.orven.deluxemenus.api.v1.DeluxeMenusApi;
import com.orven.deluxemenus.api.v1.MenuSession;
import com.orven.deluxemenus.api.v1.event.MenuClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

/** Minimal integration example; copy the relevant parts into the consumer plugin. */
public final class AshfallIntegrationExample implements Listener {

    private DeluxeMenusApi api;

    public void hook() {
        final RegisteredServiceProvider<DeluxeMenusApi> registration =
                Bukkit.getServicesManager().getRegistration(DeluxeMenusApi.class);
        api = registration == null ? null : registration.getProvider();
    }

    public void openShop(final Player player, final ItemStack item) {
        if (api == null) return;
        final MenuSession session = api.openMenu(player, "ashfall_shop");
        session.setItem(13, item, "shop.buy.fox_ears");
    }

    @EventHandler
    public void onMenuClick(final MenuClickEvent event) {
        if ("shop.buy.fox_ears".equals(event.getActionId())) {
            event.setCancelled(true);
            // Execute Ashfall business logic here.
        }
    }
}
