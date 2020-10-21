package studio.craftory.craftory_utils.command.calculate;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import studio.craftory.craftory_utils.Utils;

/**
 * Help command to inform user how to use the plugin
 */
public class CommandHelp implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length == 1) {
      Utils.msg(sender, "Help function is still a WIP, best of luck!");
    }
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    ArrayList<String> tabs = new ArrayList<>();
    return Utils.filterTabs(tabs, args);
  }

}
