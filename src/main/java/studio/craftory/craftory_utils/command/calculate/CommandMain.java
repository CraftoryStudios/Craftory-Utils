package studio.craftory.craftory_utils.command.calculate;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import studio.craftory.craftory_utils.CraftoryUtils;
import studio.craftory.craftory_utils.Utils;

/**
 * Command for simply typing calculate or any of the aliases
 */
public class CommandMain implements CommandExecutor, TabCompleter {

  @Inject
  private CraftoryUtils plugin;

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Utils.msg(sender, "Currently running Craftory-Calculate version " + plugin.getVersion());
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    return Collections.emptyList();
  }
}
