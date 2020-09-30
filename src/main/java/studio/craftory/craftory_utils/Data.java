package studio.craftory.craftory_utils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;

/**
 * Data class used to load and save information when the plugin starts/stops
 */
public class Data {

  private static final File recentLocationsFile = new File(CraftoryUtils.plugin.getDataFolder(),
      "recentLocations.json");

  private static final File savedLocationsFile = new File(CraftoryUtils.plugin.getDataFolder(),
      "savedLocations.json");

  private static void write(File file, String s) {
    try (FileWriter writer = new FileWriter(file)) {
      writer.write(s);
    } catch (IOException ignore) {
    }
  }

  private static <T> T readGson(File file, Class<T> classOfT) {
    try (FileReader reader = new FileReader(file)) {
      Gson gson = new Gson();
      return gson.fromJson(reader, classOfT);
    } catch (Exception ignore) {
    }
    return null;
  }

  public static void saveSavedLocations(HashMap<UUID, HashMap<String, Location>> savedLocations) {
    Gson gson = new Gson();
    HashMap<String, HashMap<String, String>> toSave = new HashMap<>();
    savedLocations.forEach((id, map) -> toSave.put(id.toString(), batchBuildLocations(map)));
    String json = gson.toJson(toSave);
    write(savedLocationsFile, json);
  }

  private static HashMap<String, String> batchBuildLocations(HashMap<String, Location> map) {
    HashMap<String, String> newmap = new HashMap<>();
    map.forEach((s, loc) -> newmap.put(s, getStringFromLocation(loc)));
    return newmap;
  }

  public static void saveRecentLocations(HashMap<UUID, Location> lastCalculatedLocation) {
    Gson gson = new Gson();
    HashMap<String, String> toSave = new HashMap<>();
    lastCalculatedLocation
        .forEach((id, loc) -> toSave.put(id.toString(), getStringFromLocation(loc)));
    String json = gson.toJson(toSave);
    write(recentLocationsFile, json);
  }

  private static String getStringFromLocation(Location location) {
    return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + ","
        + location.getZ();
  }

  public static HashMap<UUID, Location> loadRecentLocations() {
    HashMap<UUID, Location> recent = new HashMap<>();
    try {
      HashMap<String, String> input = new HashMap<>();
      input = (HashMap<String, String>) readGson(recentLocationsFile, input.getClass());
      if (input != null) {
        input.forEach((id, loc) -> recent.put(UUID.fromString(id), getLocationFromString(loc)));
      }
    } catch (Exception ignore) {
    }
    return recent;
  }

  private static HashMap<String, Location> batchDeconstructLocation(
      LinkedTreeMap<String, String> map) {
    HashMap<String, Location> newmap = new HashMap<>();
    map.forEach((s, loc) -> newmap.put(s, getLocationFromString(loc)));
    return newmap;
  }

  private static Location getLocationFromString(String s) {
    String[] parts = s.split(",");
    return new Location(CraftoryUtils.plugin.getServer().getWorld(parts[0]),
        Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
  }

  public static HashMap<UUID, HashMap<String, Location>> loadSavedLocations() {
    HashMap<UUID, HashMap<String, Location>> saved = new HashMap<>();
    try {
      HashMap<String, LinkedTreeMap<String, String>> input = new HashMap<>();
      input = (HashMap<String, LinkedTreeMap<String, String>>) readGson(savedLocationsFile,
          input.getClass());
      if (input != null) {
        input.forEach((id, map) -> saved.put(UUID.fromString(id), batchDeconstructLocation(map)));
      }
    } catch (Exception ignore) {
    }
    return saved;
  }

}
