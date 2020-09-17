package studio.craftory.craftorycalculate.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import studio.craftory.craftorycalculate.Utils;

public class CommandWrapper implements CommandExecutor, TabCompleter {
  private final HashMap<String, CommandExecutor> commands = new HashMap<>();
  private final HashMap<String, TabCompleter> tabs = new HashMap<>();

  public CommandWrapper() {
    commands.put("",new Command_Main());
    tabs.put("", new Command_Main());
    commands.put("help", new Command_Help());
    tabs.put("help", new Command_Help());
    commands.put("distance", new Command_Distance());
    tabs.put("distance", new Command_Distance());
    commands.put("calc", new Command_Calc());
    tabs.put("calc", new Command_Calc());
    commands.put("save", new Command_Save());
    tabs.put("save", new Command_Save());
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(!sender.hasPermission("craftory-calculate")) {
      return noPerms(sender);
    }
    if(args.length==0) return commands.get("").onCommand(sender, command, label, args);
    if(commands.containsKey(args[0])){
      return commands.get(args[0]).onCommand(sender, command, label, args);
    }
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    if(args.length==0) return (List<String>) commands.keySet();
    if(commands.containsKey(args[0])){
      if(sender.hasPermission("craftory-calculate")){
        return tabs.get(args[0]).onTabComplete(sender, command, alias, args);
      }
    }
    return null;
  }

  public static ArrayList<String> filterTabs(ArrayList<String> list, String[] origArgs) {
    if (origArgs.length == 0) {
      return list;
    }
    Iterator<String> itel = list.iterator();
    String label = origArgs[origArgs.length - 1].toLowerCase();
    while (itel.hasNext()) {
      String name = itel.next();
      if (name.toLowerCase().startsWith(label)) {
        continue;
      }
      itel.remove();
    }
    return list;
  }

  public static boolean noPerms(CommandSender s) {
    Utils.msg(s, "You do not have permission to do this");
    return true;
  }

}
