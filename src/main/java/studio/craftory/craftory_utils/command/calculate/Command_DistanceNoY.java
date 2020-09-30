package studio.craftory.craftory_utils.command.calculate;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import studio.craftory.craftory_utils.Utils;

/**
 * Calculates the distance, in the x,z plane between two points
 */
public class Command_DistanceNoY implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    return Command_Distance.calculateDistance(sender, args, true);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    ArrayList<String> tabs = new ArrayList<>();
    if (args.length >= 1 && args.length <= 3) {
      tabs.addAll(
          Utils.filterTabs((ArrayList<String>) Utils.getOnlinePlayerNames(), args));
    }
    tabs.add("Location");
    tabs.add("<prev>");
    tabs.add("Player");
    return tabs;
  }
}
