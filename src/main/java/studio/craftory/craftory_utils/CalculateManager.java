package studio.craftory.craftory_utils;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;

public class CalculateManager {

  private static HashMap<UUID, HashMap<String, Location>> savedLocations = new HashMap<>();
  private static HashMap<UUID, Location> lastCalculatedLocation = new HashMap<>();

  public CalculateManager() {
    load();
  }

  public void load() {
    lastCalculatedLocation = Data.loadRecentLocations();
    savedLocations = Data.loadSavedLocations();
  }

  public void save() {
    Data.saveRecentLocations(lastCalculatedLocation);
    Data.saveSavedLocations(savedLocations);
  }

  /**
   * Removes the players saved location
   *
   * @param id The UUID of the player
   * @param key The name of the saved location
   */
  public boolean removeSavedLocation(UUID id, String key) {
    return savedLocations.getOrDefault(id, new HashMap<>()).remove(key) != null;
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
   * Gets the saved locations of a player
   *
   * @param id The UUID of the player
   * @return A map of Name to Locations
   */
  public HashMap<String, Location> getSavedLocations(UUID id) {
    return savedLocations.getOrDefault(id, new HashMap<>());
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
}
