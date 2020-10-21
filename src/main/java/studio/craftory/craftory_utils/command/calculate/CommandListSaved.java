package studio.craftory.craftory_utils.command.calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import studio.craftory.craftory_utils.CalculateManager;
import studio.craftory.craftory_utils.Utils;

/**
 * Lists a players saved locations
 */
public class CommandListSaved implements CommandExecutor, TabCompleter {

  private final CalculateManager calculateManager;

  public CommandListSaved(CalculateManager calculateManager) {
    this.calculateManager = calculateManager;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    String name = null;
    if (args.length == 1 && sender instanceof Player) {
      name = sender.getName();
    }
    if (args.length == 2 && Utils.getOnlinePlayerNames().contains(args[1])) {
      if (!sender.hasPermission("craftory-utils.calculate.managePlayersSavedLocations")) {
        return Utils.noPerms(sender);
      }
      name = args[1];
    }
    if (name != null) {
      UUID id = Utils.getID(name);
      if (id == null) {
        return showUsage(sender);
      }
      Map<String, Location> locations = calculateManager.getSavedLocations(id);
      if (locations == null || locations.keySet().isEmpty()) {
        return noLocations(sender, name);
      } else {
        return listSaved(sender, name, locations.keySet());
      }
    }
    return showUsage(sender);
  }

  // Inform the sender of the players saved locations
  private boolean listSaved(CommandSender sender, String player, Set<String> locations) {
    Utils.msg(sender, player + "'s saved locations: " + locations.toString());
    return true;
  }

  // Inform the sender that the player had no locations to remove
  private boolean noLocations(CommandSender sender, String player) {
    Utils.msg(sender, player + " has no saved locations to clear");
    return true;
  }

  // Inform the sender of the correct usage
  private boolean showUsage(CommandSender sender) {
    Utils.msg(sender,
        "Incorrect usage! Correct format = listSaved <Player_Name> *Note currently player must be online");
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    ArrayList<String> tabs = new ArrayList<>();
    if (!sender.hasPermission("craftory-utils.calculate.managePlayersSavedLocations")) {
      return tabs;
    }
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
