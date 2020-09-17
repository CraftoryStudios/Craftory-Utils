package studio.craftory.craftorycalculate.command;

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
import studio.craftory.craftorycalculate.CraftoryCalculate;
import studio.craftory.craftorycalculate.Utils;

public class Command_Distance implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    World world = Utils.getWorld(sender);
    UUID id = Utils.getID(sender);
    if (args.length==2){
      if(sender instanceof Player){
        Location location = Utils.getValidLocation(args[1], id, ((Player) sender).getWorld());
        if(location != null){
          Utils.msg(sender, "Distance = " + location.distance(((Player) sender).getLocation()));
          return true;
        }
      }
    } else if(args.length==3) {
      Location loc1 = Utils.getValidLocation(args[1], id, world);
      Location loc2 = Utils.getValidLocation(args[2], id, world);
      if(loc1!=null && loc2!=null) {
        Utils.msg(sender, "Distance = " + Utils.format(loc1.distance(loc2)));
        return true;
      }
    }
    return true;
  }


  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    if(args.length == 3 || args.length == 4){
      return Utils.getOnlinePlayerNames();
    }
    ArrayList<String> tabs = new ArrayList<>();
    tabs.add("~<Player>");
    return CommandWrapper.filterTabs(tabs, args);
  }
}
