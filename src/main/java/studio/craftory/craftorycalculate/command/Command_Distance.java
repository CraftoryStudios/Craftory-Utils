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
    UUID id = null;
    if(sender instanceof Player) id = ((Player) sender).getUniqueId();
    else id = CraftoryCalculate.SERVER_UUID;
    if (args.length==2){
      if(sender instanceof Player){
        Location location = Utils.getValidLocation(args[1], id);
        if(location != null){
          Utils.msg(sender, "Distance = " + location.distance(((Player) sender).getLocation()));
          return true;
        }
      }
    } else if(args.length==3) {
      Location loc1 = Utils.getValidLocation(args[1], id);
      Location loc2 = Utils.getValidLocation(args[2], id);
      if(loc1!=null && loc2!=null) {
        Utils.msg(sender, "Distance = " + loc1.distance(loc2));
        return true;
      }
    } else if(args.length==4) {
      if(sender instanceof Player) {
        Location loc = Utils.getLocationFromXYZ(((Player) sender).getWorld(), args[1],args[2],args[3]);
        if(loc!=null){
          Utils.msg(sender, "Distance = " + ((Player) sender).getLocation().distance(loc));
          return true;
        }
      }
    } else if(args.length==5) {
      Location loc1 = Utils.getValidLocation(args[1], id);
      Location loc2;
      for(int i = 0; i < 2; i++) { //Try with each place
        if(loc1 != null) {
          loc2 = Utils.getLocationFromXYZ(loc1.getWorld(), args[2-i],args[3-i],args[4-i]);
          if(loc2 != null) {
            Utils.msg(sender, "Distance = " + loc1.distance(loc2));
            return true;
          }
        } else {
          loc1 = Utils.getValidLocation(args[4], id);
        }
      }
    } else if(args.length==7) {
      World world = null;
      if(sender instanceof Player) world = ((Player) sender).getWorld();
      Location loc1 = Utils.getLocationFromXYZ(world, args[1], args[2], args[3]);
      Location loc2 = Utils.getLocationFromXYZ(world, args[4], args[5], args[6]);
      if(loc1 != null && loc2 != null) {
        Utils.msg(sender, "Distance = " + loc1.distance(loc2));
        return true;
      }
    }
    return true;
  }


  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    if(args.length == 2 || args.length == 3){
      return Utils.getOnlinePlayerNames();
    }
    ArrayList<String> tabs = new ArrayList<>();
    tabs.add("~<Player>");
    return CommandWrapper.filterTabs(tabs, args);
  }
}
