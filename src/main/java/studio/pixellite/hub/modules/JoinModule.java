package studio.pixellite.hub.modules;

import com.google.common.reflect.TypeToken;
import me.lucko.helper.Events;
import me.lucko.helper.config.objectmapping.ObjectMappingException;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.hub.HubPlugin;
import studio.pixellite.hub.util.AdventureDeserializer;

import java.util.List;

public class JoinModule implements TerminableModule {
  private final HubPlugin plugin;

  public JoinModule(HubPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
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

              // send the welcome message
              sendWelcomeMessage(player);
            }).bindWith(consumer);
  }

  /**
   * Gives a server selector compass to the player.
   *
   * @param player the player to give the item to
   */
  private void giveCompass(Player player) {
    ItemStack itemStack = new ItemStack(Material.COMPASS, 1);
    ItemMeta meta = itemStack.getItemMeta();
    PersistentDataContainer container = meta.getPersistentDataContainer();

    meta.displayName(AdventureDeserializer.deserialize(plugin.getConfiguration()
            .getNode("compass", "name").getString("Server Selector")));
    itemStack.setItemMeta(meta);

    player.getInventory().setItem(4, itemStack);
    player.getInventory().setHeldItemSlot(4);
  }

  /**
   * Sends the configured welcome message to the player.
   *
   * @param player the player to send to
   */
  private void sendWelcomeMessage(Player player) {
    List<String> messages;

    try {
      messages = plugin.getConfiguration().getNode("welcome-message")
              .getList(TypeToken.of(String.class));
    } catch (ObjectMappingException e) {
      throw new RuntimeException(e);
    }

    for(String message : messages) {
      player.sendMessage(AdventureDeserializer.deserialize(message));
    }
  }
}
