package studio.craftory.craftory_utils.command.calculate;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import studio.craftory.craftory_utils.command.BaseCommand;

/**
 * Calculates the distance, in the x,z plane between two points
 */
public class CommandDistanceNoY extends BaseCommand {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    return CommandDistance.calculateDistance(sender, args, true);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    return CommandDistance.tabComplete(args);
  }

}
