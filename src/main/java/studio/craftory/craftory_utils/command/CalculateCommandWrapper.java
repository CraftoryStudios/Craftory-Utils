package studio.craftory.craftory_utils.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import studio.craftory.craftory_utils.CalculateManager;
import studio.craftory.craftory_utils.Constants;
import studio.craftory.craftory_utils.Constants.Commands;
import studio.craftory.craftory_utils.Constants.Permissions;
import studio.craftory.craftory_utils.Utils;
import studio.craftory.craftory_utils.command.calculate.CommandCalc;
import studio.craftory.craftory_utils.command.calculate.CommandCentre;
import studio.craftory.craftory_utils.command.calculate.CommandDistance;
import studio.craftory.craftory_utils.command.calculate.CommandDistanceNoY;
import studio.craftory.craftory_utils.command.calculate.CommandHelp;
import studio.craftory.craftory_utils.command.calculate.CommandListSaved;
import studio.craftory.craftory_utils.command.calculate.CommandMain;
import studio.craftory.craftory_utils.command.calculate.CommandRemoveSaved;
import studio.craftory.craftory_utils.command.calculate.CommandSave;

/**
 * Wrapper for commands, passes the logic handling to the corresponding command class
 */
public class CalculateCommandWrapper implements CommandExecutor, TabCompleter {

  /* Command Class Maps */
  private final HashMap<String, CommandExecutor> commands = new HashMap<>();
  private final HashMap<String, TabCompleter> tabs = new HashMap<>();

  public CalculateCommandWrapper(CalculateManager calculateManager) {
    commands.put(Commands.MAIN, new CommandMain());
    tabs.put(Commands.MAIN, new CommandMain());
    commands.put(Commands.HELP, new CommandHelp());
    tabs.put(Commands.HELP, new CommandHelp());
    commands.put(Commands.DISTANCE, new CommandDistance());
    tabs.put(Commands.DISTANCE, new CommandDistance());
    commands.put(Commands.CALC, new CommandCalc());
    tabs.put(Commands.CALC, new CommandCalc());
    commands.put(Commands.SAVE, new CommandSave(calculateManager));
    tabs.put(Commands.SAVE, new CommandSave(calculateManager));
    commands.put(Commands.CENTRE, new CommandCentre(calculateManager));
    tabs.put(Commands.CENTRE, new CommandCentre(calculateManager));
    commands.put(Commands.DISTANCE_NO_Y, new CommandDistanceNoY());
    tabs.put(Commands.DISTANCE_NO_Y, new CommandDistanceNoY());
    commands.put(Constants.Commands.REMOVE_SAVED, new CommandRemoveSaved(calculateManager));
    tabs.put(Constants.Commands.REMOVE_SAVED, new CommandRemoveSaved(calculateManager));
    commands.put(Commands.LIST_SAVED, new CommandListSaved(calculateManager));
    tabs.put(Commands.LIST_SAVED, new CommandListSaved(calculateManager));
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!sender.hasPermission("craftory-utils.calculate")) { // Check for base permission
      return Utils.noPerms(sender);
    }
    if (args.length == 0) {
      return commands.get(Commands.MAIN).onCommand(sender, command, label, args);
    }
    if (commands.containsKey(args[0])) {
      return commands.get(args[0]).onCommand(sender, command, label, args);
    }
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    if (!sender.hasPermission(Constants.Permissions.BASE)) { // Check for base permission
      return Collections.emptyList();
    }
    if (args.length == 1) {
      ArrayList<String> list = new ArrayList<>();
      list.add(Commands.CALC);
      list.add(Commands.CENTRE);
      list.add(Commands.DISTANCE);
      list.add(Commands.DISTANCE_NO_Y);
      list.add(Commands.HELP);
      if (sender.hasPermission(Permissions.SAVE_LOCATIONS)) {
        list.add(Commands.SAVE);
      }
      if (sender.hasPermission(Permissions.MANAGE_PLAYER_LOCATIONS)) {
        list.add(Commands.LIST_SAVED);
        list.add(Commands.REMOVE_SAVED);
      }
      return Utils.filterTabs(list, args);
    }
    if (commands.containsKey(args[0])) {
      return tabs.get(args[0]).onTabComplete(sender, command, alias, args);
    }
    return Collections.emptyList();
  }

}
