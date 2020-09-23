package studio.craftory.craftory_utils.command.calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import studio.craftory.craftory_utils.CraftoryUtils;
import studio.craftory.craftory_utils.Utils;

public class Command_Save implements CommandExecutor, TabCompleter {

  private static final String LAST_CALCULATED = "<prev>";

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!sender.hasPermission("craftory-utils.calculate.saveLocations")) {
      return Utils.noPerms(sender);
    }
    boolean hasPlayerLocationPermission = sender
        .hasPermission("craftory-utils.calculate.usePlayerLocations");
    UUID id = Utils.getID(sender);
    Location location = null;
    if (args.length < 2) {
      return showUsage(sender);
    }
    String key = args[1];
    if (key.isEmpty()) {
      return showUsage(sender);
    }
    if (args.length == 2) {
      if (!(sender instanceof Player)) {
        return showUsage(sender);
      }
      location = ((Player) sender).getLocation();
    } else if (args.length == 3) {
      if (args[2].equals(LAST_CALCULATED)) {
        location = CraftoryUtils.plugin.getLastCalculatedLocation(id);
      } else {
        location = Utils
            .getValidLocation(args[2], id, Utils.getWorld(sender), hasPlayerLocationPermission);
      }
    }
    if (location == null) {
      return showUsage(sender);
    }
    CraftoryUtils.plugin.addSavedLocation(id, key, location);
    Utils.msg(sender, "Saved location: " + key + " - " + location.toString());
    return true;
  }

  // Inform the sender of the correct usage
  private boolean showUsage(CommandSender sender) {
    Utils.msg(sender,
        "Incorrect usage! Correct format (* = optional) save [Name] *'<prev>' *x,y,z  , If xyz not specified current location will be used. use '<prev>' to save last calculated location");
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    if (!sender.hasPermission("craftory-utils.calculate.saveLocations")) {
      return null;
    }
    ArrayList<String> tabs = new ArrayList<>();
    if (args.length == 1) {
      tabs.add("Name");
    } else if (args.length == 2) {
      tabs.add("Location");
      tabs.add("<prev>");
      if (sender.hasPermission("craftory-utils.calculate.usePlayerLocations")) {
        tabs.addAll(Utils.getOnlinePlayerNames());
      }
    }
    return Utils.filterTabs(tabs, args);
  }

}
