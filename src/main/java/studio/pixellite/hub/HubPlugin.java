package studio.pixellite.hub;

import me.lucko.helper.config.ConfigurationNode;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import studio.pixellite.hub.modules.InventoryModule;
import studio.pixellite.hub.modules.JoinModule;
import studio.pixellite.hub.modules.SpawnPointModule;

public class HubPlugin extends ExtendedJavaPlugin {
  private ConfigurationNode configuration;
  private SpawnPoint spawnPoint;

  @Override
  protected void enable() {
    // setup configuration
    saveDefaultConfig();
    configuration = loadConfigNode("config.yml");

    // get spawn point
    spawnPoint = calculateSpawnPoint();

    // load modules
    bindModule(new JoinModule(this));
    bindModule(new SpawnPointModule(this));
    bindModule(new InventoryModule());
  }

  /**
   * Calculates the spawn point set in the plugin's configuration file. If a numerical
   * value isn't entered in the fields that require it said value will be replaced with 0.
   *
   * @return the new spawn point
   */
  protected SpawnPoint calculateSpawnPoint() {
    String string = configuration.getNode("spawn-point", "world").getString("world");
    double x = configuration.getNode("spawn-point", "x").getDouble();
    double y = configuration.getNode("spawn-point", "y").getDouble();
    double z = configuration.getNode("spawn-point", "z").getDouble();
    float pitch = configuration.getNode("spawn-point", "pitch").getFloat();
    float yaw = configuration.getNode("spawn-point", "yaw").getFloat();

    return new SpawnPoint(string, x, y, z, pitch, yaw);
  }

  public ConfigurationNode getConfiguration() {
    return configuration;
  }

  public SpawnPoint getSpawnPoint() {
    return spawnPoint;
  }


}
