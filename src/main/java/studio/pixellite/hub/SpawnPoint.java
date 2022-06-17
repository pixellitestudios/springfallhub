package studio.pixellite.hub;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Represents configurable spawn point in the world.
 */
public class SpawnPoint {
  private final String world;
  private final double x;
  private final double y;
  private final double z;
  private final float pitch;
  private final float yaw;
  private final Location location;

  public SpawnPoint(String world,
                    double x,
                    double y,
                    double z,
                    float pitch,
                    float yaw) {
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
    this.pitch = pitch;
    this.yaw = yaw;

    // calculate location in construction
    this.location = new Location(Bukkit.getWorld(world),
            this.x,
            this.y,
            this.z,
            this.yaw,
            this.pitch);
  }

  public String getWorld() {
    return world;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  public float getPitch() {
    return pitch;
  }

  public float getYaw() {
    return yaw;
  }

  public Location toLocation() {
    return location;
  }
}
