package studio.craftory.craftory_utils.command.calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import studio.craftory.craftory_utils.CalculateManager;
import studio.craftory.craftory_utils.Utils;
import studio.craftory.craftory_utils.command.BaseCommand;

/**
 * Calculated the centre of a list of points
 */
public class CommandCentre extends BaseCommand {

  private final CalculateManager calculateManager;

  public CommandCentre(CalculateManager calculateManager) {
    this.calculateManager = calculateManager;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length < 2) {
      return showUsage(sender); // Need at least one point
    }
    boolean hasPlayerLocationPermission = sender
        .hasPermission("craftory-utils.calculate.usePlayerLocations");
    World world = Utils.getWorld(sender);
    UUID id = Utils.getID(sender);
    Location location = Utils.getValidLocation(args[1], id, world, hasPlayerLocationPermission);
    if (location == null) {
      return showUsage(sender); // If location isn't valid show usage
    }
    try {
      for (int i = 2; i < args.length; i++) { // Get each location and add them together
        Location loc = Utils.getValidLocation(args[i], id, world, hasPlayerLocationPermission);
        if (loc != null) {
          location.add(loc);
        } else {
          return showUsage(sender);
        }
      }
    } catch (Exception ignore) {
      return showUsage(sender); // If location isn't valid show usage
    }
    // Calculate average and round
    double amount = (double) args.length - 1;
    double x = Utils.format(location.getX() / amount);
    double y = Utils.format(location.getY() / amount);
    double z = Utils.format(location.getZ() / amount);
    Location centreLoc = new Location(world, x, y, z);
    // Save it as the last calculated location
    calculateManager.setLastCalculatedLocation(id, centreLoc);
    // Inform the user of the location
    Utils.msg(sender, "Centre of points = " + x + "," + y + "," + z);
    return true;
  }

  // Inform the sender of the correct usage
  private boolean showUsage(CommandSender sender) {
    Utils.msg(sender, "Invalid used of centre please provide a list of locations separated by a space");
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    List<String> tabs = new ArrayList<>();
    if (args.length == 1) {
      tabs.add("Location");
      tabs.add("<prev>");
      tabs.add("Player");
    } else {
      tabs.add("~Location");
      tabs.add("~<prev>");
      tabs.add("~Player");
    }
    tabs.addAll(Utils.getOnlinePlayerNames());
    return tabs;
  }

}
