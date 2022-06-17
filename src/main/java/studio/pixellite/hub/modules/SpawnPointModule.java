package studio.pixellite.hub.modules;

import me.lucko.helper.Commands;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.utils.Players;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.hub.HubPlugin;
import studio.pixellite.hub.SpawnPoint;

public class SpawnPointModule implements TerminableModule {
  private final HubPlugin plugin;

  public SpawnPointModule(HubPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void setup(@NotNull TerminableConsumer terminableConsumer) {
    Commands.create()
            .assertPermission("pixellite.hub.getspawn")
            .handler(c -> {
              SpawnPoint spawnPoint = plugin.getSpawnPoint();

              Players.msg(c.sender(), "&aX: &f" + spawnPoint.getX());
              Players.msg(c.sender(), "&aY: &f" + spawnPoint.getY());
              Players.msg(c.sender(), "&aZ: &f" + spawnPoint.getZ());
              Players.msg(c.sender(), "&aPitch: &f" + spawnPoint.getPitch());
              Players.msg(c.sender(), "&aYaw: &f" + spawnPoint.getYaw());
              Players.msg(c.sender(), "&aWorld: &f" + spawnPoint.getWorld());
            })
            .registerAndBind(terminableConsumer, "getspawnpoint");
  }
}
