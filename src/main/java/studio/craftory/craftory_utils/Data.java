package studio.craftory.craftory_utils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Data class used to load and save information when the plugin starts/stops
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Data {

  private static final CraftoryUtils plugin = JavaPlugin.getPlugin(CraftoryUtils.class);


  private static final File recentLocationsFile = new File(plugin.getDataFolder(),
      "recentLocations.json");

  private static final File savedLocationsFile = new File(plugin.getDataFolder(),
      "savedLocations.json");

  private static void write(File file, String s) {
    try (FileWriter writer = new FileWriter(file)) {
      writer.write(s);
    } catch (IOException ignore) {/**/}
  }

  private static <T> T readGson(File file, Class<T> classOfT) {
    try (FileReader reader = new FileReader(file)) {
      Gson gson = new Gson();
      return gson.fromJson(reader, classOfT);
    } catch (Exception ignore) {/**/}
    return null;
  }

  public static void saveSavedLocations(Map<UUID, Map<String, Location>> savedLocations) {
    Gson gson = new Gson();
    Map<String, Map<String, String>> toSave = new HashMap<>();
    savedLocations.forEach((id, map) -> toSave.put(id.toString(), batchBuildLocations(map)));
    String json = gson.toJson(toSave);
    write(savedLocationsFile, json);
  }

  private static Map<String, String> batchBuildLocations(Map<String, Location> map) {
    Map<String, String> newmap = new HashMap<>();
    map.forEach((s, loc) -> newmap.put(s, getStringFromLocation(loc)));
    return newmap;
  }

  public static void saveRecentLocations(Map<UUID, Location> lastCalculatedLocation) {
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

  public static Map<UUID, Location> loadRecentLocations() {
    Map<UUID, Location> recent = new HashMap<>();
    try {
      Map<String, String> input = new HashMap<>();
      input = readGson(recentLocationsFile, input.getClass());
      if (input != null) {
        input.forEach((id, loc) -> recent.put(UUID.fromString(id), getLocationFromString(loc)));
      }
    } catch (Exception ignore) {/**/}
    return recent;
  }

  private static Map<String, Location> batchDeconstructLocation(
      LinkedTreeMap<String, String> map) {
    HashMap<String, Location> newmap = new HashMap<>();
    map.forEach((s, loc) -> newmap.put(s, getLocationFromString(loc)));
    return newmap;
  }

  private static Location getLocationFromString(String s) {
    String[] parts = s.split(",");
    return new Location(plugin.getServer().getWorld(parts[0]),
        Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
  }

  public static Map<UUID, Map<String, Location>> loadSavedLocations() {
    Map<UUID, Map<String, Location>> saved = new HashMap<>();
    try {
      Map<String, LinkedTreeMap<String, String>> input = new HashMap<>();
      input = readGson(savedLocationsFile,
          input.getClass());
      if (input != null) {
        input.forEach((id, map) -> saved.put(UUID.fromString(id), batchDeconstructLocation(map)));
      }
    } catch (Exception ignore) {/**/}
    return saved;
  }

}
