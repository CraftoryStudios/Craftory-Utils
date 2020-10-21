package studio.craftory.craftory_utils;

import java.util.UUID;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import studio.craftory.craftory_utils.command.CalculateCommandWrapper;

/**
 * Main plugin class
 */
public final class CraftoryUtils extends JavaPlugin {

  public static final UUID SERVER_UUID = new UUID(0, 0);

  @Getter
  private String version;

  @Getter
  private CalculateManager calculateManager;

  @Override
  public void onEnable() {
    // Plugin startup logic
    if (!getDataFolder().exists()) {
      getDataFolder().mkdir();
    }
    version = this.getDescription().getVersion();
    calculateManager = new CalculateManager();
    setupCommands();
  }

  private void setupCommands() {
    this.getCommand("calculate").setExecutor(new CalculateCommandWrapper(calculateManager));
    this.getCommand("calculate").setTabCompleter(new CalculateCommandWrapper(calculateManager));
    this.getCommand("calc").setExecutor(new CalculateCommandWrapper(calculateManager));
    this.getCommand("calc").setTabCompleter(new CalculateCommandWrapper(calculateManager));
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
    calculateManager.save();
  }

}
