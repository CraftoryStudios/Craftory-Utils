package studio.craftory.craftorycalculate.command;

import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import studio.craftory.craftorycalculate.CraftoryCalculate;
import studio.craftory.craftorycalculate.Utils;

public class Command_Centre implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(args.length < 2) return showUsage(sender);
    World world = Utils.getWorld(sender);
    UUID id = Utils.getID(sender);
    Location location = Utils.getValidLocation(args[1], id, world);
    if(location==null) return showUsage(sender);
    try {
      for(int i = 2; i < args.length; i++) {
        location.add(Utils.getValidLocation(args[i], id, world));
      }
    } catch (Exception ignore) {
      return showUsage(sender);
    }
    double amount = (double) args.length-1;
    double x = Utils.format(location.getX() / amount);
    double y = Utils.format(location.getY() / amount);
    double z = Utils.format(location.getZ() / amount);
    Location centreLoc = new Location(world, x, y, z);
    CraftoryCalculate.plugin.setLastCalculatedLocation(id, centreLoc);
    Utils.msg(sender, "Centre of points = " + x + "," + y + "," + z);
    return true;
  }


  private boolean showUsage(CommandSender sender) {
    Utils.msg(sender, "Invalid used of centre please provide a list of locations separated by a space");
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    return null;
  }
}
