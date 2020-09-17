package studio.craftory.craftorycalculate.command;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import studio.craftory.craftorycalculate.CraftoryCalculate;

public class Command_Main implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    CommandWrapper.msg(sender,"Currently running Craftory-Calculate version " + CraftoryCalculate.VERSION);
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    return null;
  }
}
