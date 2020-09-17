package studio.craftory.craftorycalculate.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import studio.craftory.craftorycalculate.Utils;

public class Command_Calc implements CommandExecutor, TabCompleter {

  private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
  private final HashMap<String, String> jsFunctions = new HashMap<String, String>(){
    {
      // Properties
      put("e","Math.E");
      put("pi","Math.PI");
      put("sqrt2","Math.SQRT2");
      put("sqrt1_2","Math.SQRT1_2");
      put("ln2","Math.LN2");
      put("ln10","Math.LN10");
      put("log2e","Math.LOG2E");
      put("log10e","Math.LOG10E");

      // Functions
      put("abs","Math.abs");
      put("acos","Math.acos");
      put("acosh","Math.acosh");
      put("asin","Math.asin");
      put("asinh","Math.asinh");
      put("atan","Math.atan");
      put("atan2","Math.atan2");
      put("atanh","Math.atanh");
      put("cbrt","Math.cbrt");
      put("ceil","Math.ceil");
      put("cos","Math.cos");
      put("cosh","Math.cosh");
      put("exp","Math.exp");
      put("floor","Math.floor");
      put("log", "Math.log");
      put("max","Math.max");
      put("min","Math.min");
      put("pow","Math.pow");
      put("random","Math.random");
      put("round","Math.round");
      put("sin","Math.sin");
      put("sinh","Math.sinh");
      put("sqrt","Math.sqrt");
      put("tan","Math.tan");
      put("tanh","Math.tanh");
      put("trunc","Math.trunc");
    }
  };

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(args.length>1){
      String expression = "";
      for(int i=1; i < args.length; i++) {
        expression+=args[i];
      }
      System.out.println(expression);
      if(dangerousExpression(expression)) {
        System.out.println("DANGEROUS");
        return invalid(sender);
      }
      try {
        Double result = Double.parseDouble(engine.eval(formatExpression(expression)).toString());
        return valid(sender, expression ,result);
      } catch (Exception exception) {
        System.out.println("error in eval");
        exception.printStackTrace();
        return invalid(sender);
      }

    }
    return true;
  }

  private boolean valid(CommandSender sender, String expression, Double result) {
    Utils.msg(sender, expression + " = " + result);
    return true;
  }

  private boolean invalid(CommandSender sender) {
    Utils.msg(sender, "Invalid expression");
    return false;
  }

  private boolean dangerousExpression(String expression) {
    return expression.matches(".*(;|var|function|console).*");
  }

  private String formatExpression(String expression) {
    return jsFunctions.entrySet()
        .stream()
        .reduce(expression.toLowerCase(),
            (s, e) -> s.replace(e.getKey(), e.getValue()),
            (s1, s2) -> null);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    ArrayList<String> tabs = new ArrayList<>();
    if(args.length==1) tabs.add("<Expression>");
      return CommandWrapper.filterTabs(tabs, args);
  }
}
