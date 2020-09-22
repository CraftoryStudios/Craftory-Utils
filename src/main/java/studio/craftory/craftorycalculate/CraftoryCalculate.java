package studio.craftory.craftorycalculate;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import studio.craftory.craftorycalculate.command.CommandWrapper;

public final class CraftoryCalculate extends JavaPlugin {

  public static final UUID SERVER_UUID = new UUID(0, 0);
  private static final HashMap<UUID, HashMap<String, Location>> savedLocations = new HashMap<>();
  private static final HashMap<UUID, Location> lastCalculatedLocation = new HashMap<>();
  public static String VERSION;
  public static CraftoryCalculate plugin;

  @Override
  public void onEnable() {
    // Plugin startup logic
    CraftoryCalculate.VERSION = this.getDescription().getVersion();
    CraftoryCalculate.plugin = this;
    this.getCommand("calculate").setExecutor(new CommandWrapper());
    this.getCommand("calculate").setTabCompleter(new CommandWrapper());
    this.getCommand("calc").setExecutor(new CommandWrapper());
    this.getCommand("calc").setTabCompleter(new CommandWrapper());
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
    Data.saveRecentLocations(lastCalculatedLocation);
  }

  /**
   * Gets the saved locations of a player
   *
   * @param id The UUID of the player
   * @return A map of Name to Locations
   */
  public HashMap<String, Location> getSavedLocations(UUID id) {
    return savedLocations.getOrDefault(id, new HashMap<>());
  }

  /**
   * Adds a location to the players saved locations
   *
   * @param id The UUID of the player
   * @param key The name of the location
   * @param location The location
   */
  public void addSavedLocation(UUID id, String key, Location location) {
    HashMap<String, Location> map = savedLocations.getOrDefault(id, new HashMap<>());
    map.put(key, location.clone());
    savedLocations.put(id, map);
  }

  /**
   * Gets the saved location of a player
   *
   * @param id The UUID of the player
   * @param key The name of the Location
   * @return The Location or null
   */
  public Location getSavedLocation(UUID id, String key) {
    return savedLocations.getOrDefault(id, new HashMap<>()).get(key);
  }

  /**
   * Sets the players last calculated location (used to save latest location)
   *
   * @param id The UUID of the player
   * @param location The calculated location
   */
  public void setLastCalculatedLocation(UUID id, Location location) {
    lastCalculatedLocation.put(id, location);
  }

  /**
   * Gets the players last calculated location
   *
   * @param id The UUID of the player
   * @return The Location
   */
  public Location getLastCalculatedLocation(UUID id) {
    return lastCalculatedLocation.get(id);
  }
}
