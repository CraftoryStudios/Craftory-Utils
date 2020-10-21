package studio.craftory.craftory_utils.command.calculate;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import studio.craftory.craftory_utils.CalculateManager;
import studio.craftory.craftory_utils.Constants.Permissions;
import studio.craftory.craftory_utils.CraftoryUtils;
import studio.craftory.craftory_utils.Utils;

public class CommandRemoveSaved implements CommandExecutor, TabCompleter {

  @Inject
  private CalculateManager calculateManager;

  @Inject
  private CraftoryUtils plugin;

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length == 2) {
      if (!sender.hasPermission(Permissions.SAVE_LOCATIONS)) {
        return Utils.noPerms(sender);
      }
      if (calculateManager.removeSavedLocation(
          Utils.getID(sender), args[1])) {
        Utils.msg(sender, "Removed location " + args[1]);
      } else {
        Utils.msg(sender, args[1] + " is not a saved location");
      }
      return true;
    } else if (args.length == 3) {
      if (!sender.hasPermission(Permissions.MANAGE_PLAYER_LOCATIONS)) {
        return Utils.noPerms(sender);
      }
      if (Utils.getOnlinePlayerNames().contains(args[1])) {
        if (calculateManager.removeSavedLocation(plugin.getServer().getPlayer(args[1]).getUniqueId(), args[2])) {
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
        "Incorrect usage! Correct format = removeSaved *<Player_Name> <Location name> (Not giving a location name will remove all) *Note currently player must be online");
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    ArrayList<String> tabs = new ArrayList<>();
    if (!sender.hasPermission(Permissions.MANAGE_PLAYER_LOCATIONS)) {
      tabs.add("<Location Name>");
    } else {
      if (args.length == 1) {
        tabs.add("Player");
        return tabs;
      } else if (args.length == 2) {
        tabs.addAll(Utils.getOnlinePlayerNames());
        return Utils.filterTabs(tabs, args);
      } else if (args.length == 3 && args[2].isEmpty()) {
        tabs.add("<Location Name>");
      }
    }
    return tabs;
  }

}
