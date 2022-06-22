package studio.pixellite.hub.modules;

import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class InventoryModule implements TerminableModule {
  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Events.subscribe(PlayerDropItemEvent.class)
            .filter(e -> !e.getPlayer().hasPermission("pixellite.hub.bypass"))
            .handler(e -> e.setCancelled(true))
            .bindWith(consumer);

    Events.subscribe(PlayerInteractEvent.class)
            .filter(e -> e.getAction().isRightClick())
            .filter(e -> e.getMaterial().equals(Material.COMPASS))
            .handler(e -> e.getPlayer().performCommand("server"))
            .bindWith(consumer);

    Events.subscribe(InventoryClickEvent.class)
            .filter(e -> !e.getClick().isKeyboardClick() || !e.getClick().isCreativeAction())
            .filter(e -> !e.getWhoClicked().hasPermission("pixellite.hub.bypass"))
            .handler(e -> e.setCancelled(true))
            .bindWith(consumer);
  }
}
