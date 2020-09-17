package studio.craftory.craftorycalculate.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import studio.craftory.craftorycalculate.maths.Operator;
import studio.craftory.craftorycalculate.maths.SingleFunction;

public class Command_Calc implements CommandExecutor, TabCompleter {

  private static Pattern pattern = Pattern.compile("([a-z]+)\\((-?[0-9]+.?[0-9]*)\\)");

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(args.length==4) {
      try {
        double x = parseParam(args[1]);
        double y = parseParam(args[3]);
        Operator operator = Operator.valueOf(args[2]);
        CommandWrapper.msg(sender, "=" + operator.apply(x,y));
        return true;
      } catch (Exception ignored) {}
    }
    return true;
  }


  private Double parseParam(String s) {
    try {
      Double d = Double.parseDouble(s);
      return d;
    } catch (Exception ignored){}
    try {
      Matcher matcher = pattern.matcher(s);
      if(!matcher.find()) return null;
      if(matcher.groupCount()==3) {
        return SingleFunction.valueOf(matcher.group(1)).apply(Double.parseDouble(matcher.group(2)));
      }
    } catch (Exception ignored){}
    return null;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    ArrayList<String> tabs = new ArrayList<>();
    return CommandWrapper.filterTabs(tabs, args);
  }
}
