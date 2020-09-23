package studio.craftory.craftory_utils;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import studio.craftory.craftory_utils.command.CalculateCommandWrapper;

public final class CraftoryUtils extends JavaPlugin {

  public static final UUID SERVER_UUID = new UUID(0, 0);
  public static String VERSION;
  public static CraftoryUtils plugin;
  private static HashMap<UUID, HashMap<String, Location>> savedLocations = new HashMap<>();
  private static HashMap<UUID, Location> lastCalculatedLocation = new HashMap<>();

  @Override
  public void onEnable() {
    // Plugin startup logic
    if (!getDataFolder().exists()) {
      getDataFolder().mkdir();
    }
    CraftoryUtils.VERSION = this.getDescription().getVersion();
    CraftoryUtils.plugin = this;
    this.getCommand("calculate").setExecutor(new CalculateCommandWrapper());
    this.getCommand("calculate").setTabCompleter(new CalculateCommandWrapper());
    this.getCommand("calc").setExecutor(new CalculateCommandWrapper());
    this.getCommand("calc").setTabCompleter(new CalculateCommandWrapper());

    CraftoryUtils.lastCalculatedLocation = Data.loadRecentLocations();
    CraftoryUtils.savedLocations = Data.loadSavedLocations();
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
    Data.saveRecentLocations(lastCalculatedLocation);
    Data.saveSavedLocations(savedLocations);
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
   * Clears a players saved locations
   *
   * @param id The UUID of the player
   * @return True if there was something to remove
   */
  public boolean clearSavedLocations(UUID id) {
    return savedLocations.remove(id) != null;
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
