package studio.craftory.craftorycalculate;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;
import org.bukkit.Location;

public class Data {

  private static final File recentFile = new File(CraftoryCalculate.plugin.getDataFolder(),
      "recent.json");

  public static void saveSavedLocations(HashMap<UUID, HashMap<String, Location>> savedLocations) {

  }

  public static void saveRecentLocations(HashMap<UUID, Location> lastCalculatedLocation) {
    Gson gson = new Gson();
    String json = gson.toJson(lastCalculatedLocation);
    try {
      FileWriter writer = new FileWriter(recentFile);
      writer.write(json);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static HashMap<UUID, Location> loadRecentLocations() {
    try {
      Scanner scanner = new Scanner(recentFile);
      String input = scanner.nextLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new HashMap<>();
  }

  public static HashMap<UUID, HashMap<String, Location>> loadSavedLocations() {
    return null;
  }

}
