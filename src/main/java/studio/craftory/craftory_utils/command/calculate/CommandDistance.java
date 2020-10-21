package studio.craftory.craftory_utils.command.calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import studio.craftory.craftory_utils.Utils;

/**
 * Calculates the distance between two points in x,y,z space
 */
public class CommandDistance implements CommandExecutor, TabCompleter {

  protected static boolean calculateDistance(CommandSender sender, String[] args, boolean ignoreY) {
    World world = Utils.getWorld(sender);
    UUID id = Utils.getID(sender);
    boolean hasPlayerLocationPermission = sender
        .hasPermission("craftory-utils.calculate.usePlayerLocations");
    Location loc1 = null;
    Location loc2 = null;
    if (args.length >= 2) {
      return showUsage(sender);
    }
    loc1 = Utils.getValidLocation(args[1], id, world, hasPlayerLocationPermission);
    if (args.length == 2) { // If Single location use senders position as other
      if (sender instanceof Player) {
        loc2 = ((Player) sender).getLocation();
      }
    } else if (args.length == 3) {
      loc2 = Utils.getValidLocation(args[2], id, world, hasPlayerLocationPermission);
    }
    if (loc1 != null && loc2 != null) {
      if (ignoreY) {
        loc1.setY(0);
        loc2.setY(0);
      }
      Utils.msg(sender, "Distance = " + Utils.format(loc1.distance(loc2)));
      return true;
    }
    return showUsage(sender);
  }

  // Inform the sender of the correct usage
  private static boolean showUsage(CommandSender sender) {
    Utils.msg(sender,
        "Please provide 1 or 2 locations in the form of a Player, Saved Location or coordinates (x,y,z)");
    return true;
  }

  public static List<String> tabComplete(String[] args) {

    List<String> tabs = new ArrayList<>();
    if (args.length >= 1 && args.length <= 3) {
      tabs.addAll(
          Utils.filterTabs(Utils.getOnlinePlayerNames(), args));
    }
    tabs.add("Location");
    tabs.add("<prev>");
    tabs.add("Player");
    return tabs;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    return calculateDistance(sender, args, false);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    return tabComplete(args);
  }

}
