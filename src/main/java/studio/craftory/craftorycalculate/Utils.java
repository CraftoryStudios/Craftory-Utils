package studio.craftory.craftorycalculate;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Helpful Util methods class
 */
public class Utils {

  private static final DecimalFormat decimalFormat = new DecimalFormat("#.###");

  /**
   * Gets a location from a string in 3 possible ways: - From coordinates in the form x,y,z - From a
   * player's saved location - From another players current position
   *
   * @param s The string
   * @param id The UUID Of the sender
   * @param world The world the sender is in
   * @return The location created or null if invalid
   */
  public static Location getValidLocation(String s, UUID id, World world) {
    Location location;
    location = CraftoryCalculate.plugin.getSavedLocation(id, s);
    if (location != null) {
      return location;
    }
    location = getLocationFromXYZ(s, world);
    if (location != null) {
      return location;
    }
    if (getOnlinePlayerNames().contains(s)) {
      return CraftoryCalculate.plugin.getServer().getPlayer(s).getLocation().clone();
    }
    return null;
  }

  /**
   * Gets the location from a string in the form x,y,z
   *
   * @param s The string
   * @param world The world the coordinates are in
   * @return The created location or null if invalid
   */
  public static Location getLocationFromXYZ(String s, World world) {
    String[] parts = s.split(",");
    try {
      if (parts.length == 3) {
        return new Location(world, Double.parseDouble(parts[0]), Double.parseDouble(parts[1]),
            Double.parseDouble(parts[2]));
      }
    } catch (Exception ignore) {
    }
    return null;
  }

  /**
   * Gets the world of the sender (The default world if the sender is the server
   *
   * @param sender The sender of the command
   * @return The world
   */
  public static World getWorld(CommandSender sender) {
    if (sender instanceof Player) {
      return ((Player) sender).getWorld();
    } else {
      return CraftoryCalculate.plugin.getServer().getWorlds().get(0);
    }
  }

  /**
   * Sends a message to the sender
   *
   * @param s The sender
   * @param msg The message
   */
  public static void msg(final CommandSender s, String msg) {
    if (s instanceof Player) {
      msg = ChatColor.translateAlternateColorCodes('&', msg);
    } else {
      msg = ChatColor.translateAlternateColorCodes('&', "[Craftory]" + msg);
    }
    s.sendMessage(msg);
  }

  /**
   * Gets all online player's names
   *
   * @return A list of the names of all online players
   */
  public static List<String> getOnlinePlayerNames() {
    Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
    Bukkit.getServer().getOnlinePlayers().toArray(players);
    return Arrays.stream(players).map(Player::getName)
        .collect(Collectors.toList());
  }

  /**
   * Gets the UUID of the sender (All zeros if the sender is the server)
   *
   * @param sender The sender
   * @return The UUID
   */
  public static UUID getID(CommandSender sender) {
    if (sender instanceof Player) {
      return ((Player) sender).getUniqueId();
    } else {
      return CraftoryCalculate.SERVER_UUID;
    }
  }

  /**
   * Rounds the given double correctly
   *
   * @param d The un-rounded double
   * @return The rounded double
   */
  public static Double format(Double d) {
    return Double.parseDouble(decimalFormat.format(d));
  }

  /* Standard message for a lack of permissions */
  public static boolean noPerms(CommandSender s) {
    msg(s, "You do not have permission to do this");
    return true;
  }
}
