package studio.craftory.craftorycalculate;

import javax.security.auth.kerberos.KerberosTicket;
import org.bukkit.plugin.java.JavaPlugin;
import studio.craftory.craftorycalculate.command.CommandWrapper;

public final class CraftoryCalculate extends JavaPlugin {

  public static String VERSION;
  public static CraftoryCalculate plugin;

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
}
