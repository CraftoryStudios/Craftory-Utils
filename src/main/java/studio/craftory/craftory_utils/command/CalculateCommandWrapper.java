package studio.craftory.craftory_utils.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import studio.craftory.craftory_utils.Utils;

/**
 * Wrapper for commands, passes the logic handling to the corresponding command class
 */
public class CommandWrapper implements CommandExecutor, TabCompleter {

  /* Command Class Maps */
  private final HashMap<String, CommandExecutor> commands = new HashMap<>();
  private final HashMap<String, TabCompleter> tabs = new HashMap<>();

  public CommandWrapper() {
    commands.put("", new Command_Main());
    tabs.put("", new Command_Main());
    commands.put("help", new Command_Help());
    tabs.put("help", new Command_Help());
    commands.put("distance", new Command_Distance());
    tabs.put("distance", new Command_Distance());
    commands.put("calc", new Command_Calc());
    tabs.put("calc", new Command_Calc());
    commands.put("save", new Command_Save());
    tabs.put("save", new Command_Save());
    commands.put("centre", new Command_Centre());
    tabs.put("centre", new Command_Centre());
  }

  /* Removes any tab entries that do not start with the typed argument */
  public static ArrayList<String> filterTabs(ArrayList<String> list, String[] origArgs) {
    if (origArgs.length == 0) {
      return list;
    }
    Iterator<String> iter = list.iterator();
    String label = origArgs[origArgs.length - 1].toLowerCase();
    while (iter.hasNext()) {
      String name = iter.next();
      if (name.toLowerCase().startsWith(label)) {
        continue;
      }
      iter.remove();
    }
    return list;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!sender.hasPermission("craftory-calculate")) { // Check for base permission
      return Utils.noPerms(sender);
    }
    if (args.length == 0) {
      return commands.get("").onCommand(sender, command, label, args);
    }
    if (commands.containsKey(args[0])) {
      return commands.get(args[0]).onCommand(sender, command, label, args);
    }
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    if (!sender.hasPermission("craftory-calculate")) { // Check for base permission
      Utils.noPerms(sender);
      return null;
    }
    if (args.length == 1) {
      return new ArrayList<>(commands.keySet()); // Return all commands
    }
    if (commands.containsKey(args[0])) {
      return tabs.get(args[0]).onTabComplete(sender, command, alias, args);
    }
    return null;
  }

}
