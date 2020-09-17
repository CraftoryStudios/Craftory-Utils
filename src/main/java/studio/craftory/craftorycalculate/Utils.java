package studio.craftory.craftorycalculate;

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

  public static Location getLocationFromXYZ(World world, String xs, String ys, String zs) {
    if(world==null) world = CraftoryCalculate.plugin.getServer().getWorlds().get(0);
    try {
      double x = Double.parseDouble(xs);
      double y = Double.parseDouble(ys);
      double z = Double.parseDouble(zs);
      return new Location(world,x,y,z);
    } catch (Exception e) {
      return null;
    }
  }

  public static Location getValidLocation(String s, UUID id) {
    if(getOnlinePlayerNames().contains(s)) {
      return CraftoryCalculate.plugin.getServer().getPlayer(s).getLocation();
    }
    return CraftoryCalculate.plugin.getSavedLocation(id, s);
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
    List<String> playerNames = Arrays.stream(players).map(Player::getName)
        .collect(Collectors.toList());
    return playerNames;
  }
}
