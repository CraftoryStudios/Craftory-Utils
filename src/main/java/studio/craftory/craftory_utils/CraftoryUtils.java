package studio.craftory.craftory_utils;

import java.util.UUID;
import org.bukkit.plugin.java.JavaPlugin;
import studio.craftory.craftory_utils.command.CalculateCommandWrapper;

/**
 * Main plugin class
 */
public final class CraftoryUtils extends JavaPlugin {

  public static final UUID SERVER_UUID = new UUID(0, 0);
  public static String VERSION;
  public static CraftoryUtils plugin;
  public static CalculateManager calculateManager;

  @Override
  public void onEnable() {
    // Plugin startup logic
    if (!getDataFolder().exists()) {
      getDataFolder().mkdir();
    }
    CraftoryUtils.VERSION = this.getDescription().getVersion();
    CraftoryUtils.plugin = this;
    CraftoryUtils.calculateManager = new CalculateManager();
    setupCommands();
  }

  private void setupCommands() {
    this.getCommand("calculate").setExecutor(new CalculateCommandWrapper());
    this.getCommand("calculate").setTabCompleter(new CalculateCommandWrapper());
    this.getCommand("calc").setExecutor(new CalculateCommandWrapper());
    this.getCommand("calc").setTabCompleter(new CalculateCommandWrapper());
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
    CraftoryUtils.calculateManager.save();
  }

}
