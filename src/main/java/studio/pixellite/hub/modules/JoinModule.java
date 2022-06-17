package studio.pixellite.hub.modules;

import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.hub.HubPlugin;
import studio.pixellite.hub.util.AdventureDeserializer;

public class JoinModule implements TerminableModule {
  private final HubPlugin plugin;

  public JoinModule(HubPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void setup(@NotNull TerminableConsumer terminableConsumer) {
    Events.subscribe(PlayerJoinEvent.class)
            .handler(e -> {
              Player player = e.getPlayer();

              // teleport player to spawn on join
              player.teleport(plugin.getSpawnPoint().toLocation());

              // set player's gamemode if not admin
              if(!player.hasPermission("pixellite.hub.bypass")) {
                player.setGameMode(GameMode.ADVENTURE);
              }

              // give player compass
              giveCompass(player);
            });
  }

  private void giveCompass(Player player) {
    ItemStack itemStack = new ItemStack(Material.COMPASS, 1);
    ItemMeta meta = itemStack.getItemMeta();
    PersistentDataContainer container = meta.getPersistentDataContainer();

    meta.displayName(AdventureDeserializer.deserialize(plugin.getConfiguration()
            .getNode("compass", "name").getString("Server Selector")));
    itemStack.setItemMeta(meta);

    player.getInventory().setItem(4, itemStack);
  }
}
