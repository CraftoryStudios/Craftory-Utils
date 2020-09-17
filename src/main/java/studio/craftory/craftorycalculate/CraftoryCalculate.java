package studio.craftory.craftorycalculate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import javax.security.auth.kerberos.KerberosTicket;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import studio.craftory.craftorycalculate.command.CommandWrapper;

public final class CraftoryCalculate extends JavaPlugin {

  public static String VERSION;
  public static CraftoryCalculate plugin;
  private static HashMap<UUID, HashMap<String, Location>> savedLocations = new HashMap<>();
  public static final UUID SERVER_UUID = new UUID(0,0);

  @Override
  public void onEnable() {
    // Plugin startup logic
    CraftoryCalculate.VERSION = this.getDescription().getVersion();
    CraftoryCalculate.plugin = this;
    this.getCommand("calculate").setExecutor(new CommandWrapper());
    this.getCommand("calculate").setTabCompleter(new CommandWrapper());
    this.getCommand("calc").setExecutor(new CommandWrapper());
    this.getCommand("calc").setTabCompleter(new CommandWrapper());
    this.getCommand("c").setExecutor(new CommandWrapper());
    this.getCommand("c").setTabCompleter(new CommandWrapper());
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

  public HashMap<String, Location> getSavedLocations(UUID id) {
    return savedLocations.getOrDefault(id,new HashMap<>());
  }

  public void addSavedLocation(UUID id, String key, Location location) {
    HashMap<String, Location> map = savedLocations.getOrDefault(id,new HashMap<>());
    map.put(key,location);
    savedLocations.put(id, map);
  }

  public Location getSavedLocation(UUID id, String key) {
    return savedLocations.getOrDefault(id,new HashMap<>()).get(key);
  }

}
