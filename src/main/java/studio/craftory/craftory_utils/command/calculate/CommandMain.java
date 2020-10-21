package studio.craftory.craftory_utils.command.calculate;

import java.util.Collections;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import studio.craftory.craftory_utils.CraftoryUtils;
import studio.craftory.craftory_utils.Utils;
import studio.craftory.craftory_utils.command.BaseCommand;

/**
 * Command for simply typing calculate or any of the aliases
 */
public class CommandMain extends BaseCommand {


  private static final String VERSION = JavaPlugin.getPlugin(CraftoryUtils.class).getVersion();

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Utils.msg(sender, "Currently running Craftory-Calculate version " + VERSION);
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    return Collections.emptyList();
  }

}
