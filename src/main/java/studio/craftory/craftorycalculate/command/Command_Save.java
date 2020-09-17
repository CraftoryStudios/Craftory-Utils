package studio.craftory.craftorycalculate.command;

import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import studio.craftory.craftorycalculate.CraftoryCalculate;
import studio.craftory.craftorycalculate.Utils;

public class Command_Save implements CommandExecutor, TabCompleter {

  private static final String LAST_CALCULATED = "<prev>";
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(!sender.hasPermission("craftory-calculate.save")) return CommandWrapper.noPerms(sender);
    UUID id = null;
    if(sender instanceof Player) id = ((Player) sender).getUniqueId();
    else id = CraftoryCalculate.SERVER_UUID;
    Location location = null;
    if(args.length < 2) return false;
    String key = args[1];
    if(key.isEmpty()) return false;
    if(args.length==2){
      if(!(sender instanceof Player)) return false;
      location = ((Player) sender).getLocation();
    } else if(args.length==3) {
      if(args[2].equals(LAST_CALCULATED)){
        //TODO set location to last calculated location
        location = null;
      }
    } else if(args.length==5) {
      location = Utils.getLocationFromXYZ(null, args[2], args[3], args[4]);
    }
    if(location==null) return false;
    CraftoryCalculate.plugin.addSavedLocation(id, key, location);
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    return null;
  }

}
