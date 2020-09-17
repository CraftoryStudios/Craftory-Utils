package studio.craftory.craftorycalculate.command;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import studio.craftory.craftorycalculate.CraftoryCalculate;

public class Command_Distance implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length==2){
      if(sender instanceof Player){
        Location location = getValidLocation(args[1]);
        if(location != null){
          CommandWrapper.msg(sender, "Distance = " + location.distance(((Player) sender).getLocation()));
          return true;
        }
      }
    } else if(args.length==3) {
      Location loc1 = getValidLocation(args[1]);
      Location loc2 = getValidLocation(args[2]);
      if(loc1!=null && loc2!=null) {
        CommandWrapper.msg(sender, "Distance = " + loc1.distance(loc2));
        return true;
      }
    } else if(args.length==4) {
      if(sender instanceof Player) {
        Location loc = getLocationFromXYZ(((Player) sender).getWorld(), args[1],args[2],args[3]);
        if(loc!=null){
          CommandWrapper.msg(sender, "Distance = " + ((Player) sender).getLocation().distance(loc));
          return true;
        }
      }
    } else if(args.length==5) {
      Location loc1 = getValidLocation(args[1]);
      Location loc2;
      for(int i = 0; i < 2; i++) { //Try with each place
        if(loc1 != null) {
          loc2 = getLocationFromXYZ(loc1.getWorld(), args[2-i],args[3-i],args[4-i]);
          if(loc2 != null) {
            CommandWrapper.msg(sender, "Distance = " + loc1.distance(loc2));
            return true;
          }
        } else {
          loc1 = getValidLocation(args[4]);
        }
      }
    } else if(args.length==7) {
      World world;
      if(sender instanceof Player) world = ((Player) sender).getWorld();
      else world = CraftoryCalculate.plugin.getServer().getWorlds().get(0);
      Location loc1 = getLocationFromXYZ(world, args[1], args[2], args[3]);
      Location loc2 = getLocationFromXYZ(world, args[4], args[5], args[6]);
      if(loc1 != null && loc2 != null) {
        CommandWrapper.msg(sender, "Distance = " + loc1.distance(loc2));
        return true;
      }
    }
    return true;
  }

  private Location getLocationFromXYZ(World world ,String xs, String ys, String zs) {
    try {
      double x = Double.parseDouble(xs);
      double y = Double.parseDouble(ys);
      double z = Double.parseDouble(zs);
      return new Location(world,x,y,z);
    } catch (Exception e) {
      return null;
    }
  }
  private Location getValidLocation(String s) {
    if(CommandWrapper.getOnlinePlayerNames().contains(s)) {
      return CraftoryCalculate.plugin.getServer().getPlayer(s).getLocation();
    }
    return null;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    if(args.length == 2 || args.length == 3){
      return CommandWrapper.getOnlinePlayerNames();
    }
    ArrayList<String> tabs = new ArrayList<>();
    tabs.add("~<Player>");
    return CommandWrapper.filterTabs(tabs, args);
  }
}
