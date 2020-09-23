package studio.craftory.craftorycalculate;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;

public class Data {

  private static final File recentFile = new File(CraftoryCalculate.plugin.getDataFolder(),
      "recent.json");

  private static final File savedLocationsFile = new File(CraftoryCalculate.plugin.getDataFolder(),
      "savedLocations.json");

  public static void saveSavedLocations(HashMap<UUID, HashMap<String, Location>> savedLocations) {
    Gson gson = new Gson();
    HashMap<String, HashMap<String, String>> toSave = new HashMap<>();
    savedLocations.forEach((id, map) -> toSave.put(id.toString(), batchLocationToString(map)));
    String json = gson.toJson(toSave);
    write(savedLocationsFile, json);
  }

  private static HashMap<String, String> batchLocationToString(HashMap<String, Location> map) {
    HashMap<String, String> newmap = new HashMap<>();
    map.forEach((s, loc) -> newmap.put(s, locationToString(loc)));
    return newmap;
  }

  private static HashMap<String, Location> batchStringToLocation(
      LinkedTreeMap<String, String> map) {
    HashMap<String, Location> newmap = new HashMap<>();
    map.forEach((s, loc) -> newmap.put(s, stringToLocation(loc)));
    return newmap;
  }

  private static void write(File file, String s) {
    FileWriter writer = null;
    try {
      writer = new FileWriter(file);
      writer.write(s);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (writer != null) {
        try {
          writer.flush();
          writer.close();
        } catch (Exception ignore) {
        }
      }
    }
  }

  public static void saveRecentLocations(HashMap<UUID, Location> lastCalculatedLocation) {
    Gson gson = new Gson();
    HashMap<String, String> toSave = new HashMap<>();
    lastCalculatedLocation.forEach((id, loc) -> toSave.put(id.toString(), locationToString(loc)));
    String json = gson.toJson(toSave);
    write(recentFile, json);
  }

  private static String locationToString(Location location) {
    return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + ","
        + location.getZ();
  }

  private static Location stringToLocation(String s) {
    String[] parts = s.split(",");
    return new Location(CraftoryCalculate.plugin.getServer().getWorld(parts[0]),
        Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
  }


  public static HashMap<UUID, Location> loadRecentLocations() {
    HashMap<UUID, Location> recent = new HashMap<>();
    try {
      HashMap<String, String> input = (HashMap<String, String>) readGson(recentFile, HashMap.class);
      if (input != null) {
        input.forEach((id, loc) -> recent.put(UUID.fromString(id), stringToLocation(loc)));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(recent.toString());
    return recent;
  }

  private static <T> T readGson(File file, Class<T> classOfT) {
    try (FileReader reader = new FileReader(file)) {
      Gson gson = new Gson();
      return gson.fromJson(reader, classOfT);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static HashMap<UUID, HashMap<String, Location>> loadSavedLocations() {
    HashMap<UUID, HashMap<String, Location>> saved = new HashMap<>();
    try {
      HashMap<String, LinkedTreeMap<String, String>> input = (HashMap<String, LinkedTreeMap<String, String>>) readGson(
          savedLocationsFile, saved.getClass());
      if (input != null) {
        input.forEach((id, map) -> saved.put(UUID.fromString(id), batchStringToLocation(map)));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(saved.toString());
    return saved;
  }

}
