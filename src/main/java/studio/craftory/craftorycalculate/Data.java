package studio.craftory.craftorycalculate;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;

public class Data {

  private static void saveSavedLocations(HashMap<UUID, HashMap<String, Location>> savedLocations) {

  }

  private static void saveRecentLocations(HashMap<UUID,Location> lastCalculatedLocation) {
    File dataFile = new File(CraftoryCalculate.plugin.getDataFolder(), "recent.json");

  }

  private static HashMap<UUID,Location> loadRecentLocations() {
    return null;
  }

  private static HashMap<UUID, HashMap<String, Location>> loadSavedLocations() {
    return null;
  }

}
