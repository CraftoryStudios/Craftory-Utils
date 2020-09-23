package studio.craftory.craftory_utils.command.calculate;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import studio.craftory.craftory_utils.CraftoryUtils;
import studio.craftory.craftory_utils.Utils;

public class Command_RemoveSaved implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!sender.hasPermission("craftory-utils.calculate.managePlayersSavedLocations")) {
      return Utils.noPerms(sender);
    }
    if (args.length == 2) {
      if (Utils.getOnlinePlayerNames().contains(args[1])) {
        if (CraftoryUtils.plugin.clearSavedLocations(
            CraftoryUtils.plugin.getServer().getPlayer(args[1]).getUniqueId())) {
          Utils.msg(sender, "Cleared " + args[1] + "'s saved locations");
        } else {
          Utils.msg(sender, args[1] + " has no saved locations to clear");
        }
        return true;
      }
    } else if (args.length == 3) {
      if (Utils.getOnlinePlayerNames().contains(args[1])) {
        if (CraftoryUtils.plugin.removeSavedLocation(
            CraftoryUtils.plugin.getServer().getPlayer(args[1]).getUniqueId(), args[2])) {
          Utils.msg(sender, "Removed " + args[1] + "'s saved location " + args[2]);
        } else {
          Utils.msg(sender, args[1] + " does not have a saved location named '" + args[2] + "'");
        }
        return true;
      }
    }
    return showUsage(sender);
  }



  // Inform the sender of the correct usage
  private boolean showUsage(CommandSender sender) {
    Utils.msg(sender,
        "Incorrect usage! Correct format = clearSaved <Player_Name> <Location name> (Not giving a location name will remove all) *Note currently player must be online");
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
    } else  if (args.length == 3  && args[2].isEmpty()) {
      tabs.add("<Location Name>");
    }
    return tabs;
  }

}
