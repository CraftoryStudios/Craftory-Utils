package studio.craftory.craftorycalculate;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {

  private static final DecimalFormat decimalFormat = new DecimalFormat("#.###");

  public static Location getValidLocation(String s, UUID id, World world) {
    Location location;
    location = CraftoryCalculate.plugin.getSavedLocation(id, s);
    if(location!=null) return location;
    location = getLocationFromXYZ(s, world);
    if(location!=null) return location;
    if(getOnlinePlayerNames().contains(s)) {
      return CraftoryCalculate.plugin.getServer().getPlayer(s).getLocation().clone();
    }
    return null;
  }

  public static Location getLocationFromXYZ(String s, World world) {
    String[] parts = s.split(",");
    try {
      if(parts.length==3){
        return new Location(world,Double.parseDouble(parts[0]),Double.parseDouble(parts[1]) ,Double.parseDouble(parts[2]));
      }
    } catch (Exception ignore) {}
    return null;
  }
  public static World getWorld(CommandSender sender) {
    if(sender instanceof Player) return ((Player) sender).getWorld();
    else return CraftoryCalculate.plugin.getServer().getWorlds().get(0);
  }

  public static void msg(final CommandSender s, String msg) {
    if (s instanceof Player) {
      msg = ChatColor.translateAlternateColorCodes('&', msg);
    } else {
      msg = ChatColor.translateAlternateColorCodes('&', "[Craftory]" + msg);
    }
    s.sendMessage(msg);
  }

  public static List<String> getOnlinePlayerNames() {
    Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
    Bukkit.getServer().getOnlinePlayers().toArray(players);
    return Arrays.stream(players).map(Player::getName)
        .collect(Collectors.toList());
  }

  public static UUID getID(CommandSender sender) {
    if(sender instanceof Player) return((Player) sender).getUniqueId();
    else return CraftoryCalculate.SERVER_UUID;
  }

  public static Double format(Double d) {
    return Double.parseDouble(decimalFormat.format(d));
  }
}
