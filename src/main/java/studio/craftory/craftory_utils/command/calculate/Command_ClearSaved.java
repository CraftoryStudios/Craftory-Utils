package studio.craftory.craftory_utils.command.calculate;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import studio.craftory.craftory_utils.CraftoryUtils;
import studio.craftory.craftory_utils.Utils;

public class Command_ClearSaved implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!sender.hasPermission("craftory-utils.calculate.managePlayersSavedLocations")) {
      return Utils.noPerms(sender);
    }
    if (args.length == 2) {
      if (Utils.getOnlinePlayerNames().contains(args[1])) {
        if (CraftoryUtils.plugin.clearSavedLocations(
            CraftoryUtils.plugin.getServer().getPlayer(args[1]).getUniqueId())) {
          return removed(sender, args[1]);
        } else {
          return nothingToRemove(sender, args[1]);
        }
      }
    }
    return showUsage(sender);
  }

  // Inform the sender the players saved locations have been removed
  private boolean removed(CommandSender sender, String player) {
    Utils.msg(sender, "Cleared " + player + "'s saved locations");
    return true;
  }

  // Inform the sender that the player had no locations to remove
  private boolean nothingToRemove(CommandSender sender, String player) {
    Utils.msg(sender, player + " has no saved locations to clear");
    return true;
  }

  // Inform the sender of the correct usage
  private boolean showUsage(CommandSender sender) {
    Utils.msg(sender,
        "Incorrect usage! Correct format = clearSaved <Player_Name> *Note currently player must be online");
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    if (!sender.hasPermission("craftory-utils.calculate.managePlayersSavedLocations")) {
      return null;
    }
    ArrayList<String> tabs = new ArrayList<>();
    if (args.length == 1) {
      tabs.add("Player");
      return tabs;
    } else if (args.length == 2) {
      tabs.addAll(Utils.getOnlinePlayerNames());
      return Utils.filterTabs(tabs, args);
    }
    return tabs;
  }

}
