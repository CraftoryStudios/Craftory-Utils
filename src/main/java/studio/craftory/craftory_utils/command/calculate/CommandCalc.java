package studio.craftory.craftory_utils.command.calculate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import studio.craftory.craftory_utils.Utils;
import studio.craftory.craftory_utils.command.IBaseCommand;

public class CommandCalc implements IBaseCommand {

  private static final ScriptEngine engine = new ScriptEngineManager()
      .getEngineByName("nashorn"); // Efficient Java Script engine

  /* Map of functions for the command to the Java Script equivalent */
  private static final Map<String, String> jsFunctions = new HashMap<>();

  static {
    // Properties
    jsFunctions.put("e", "Math.E");
    jsFunctions.put("pi", "Math.PI");
    jsFunctions.put("sqrt2", "Math.SQRT2");
    jsFunctions.put("sqrt1_2", "Math.SQRT1_2");
    jsFunctions.put("ln2", "Math.LN2");
    jsFunctions.put("ln10", "Math.LN10");
    jsFunctions.put("log2e", "Math.LOG2E");
    jsFunctions.put("log10e", "Math.LOG10E");

    // Functions
    jsFunctions.put("abs", "Math.abs");
    jsFunctions.put("acos", "Math.acos");
    jsFunctions.put("acosh", "Math.acosh");
    jsFunctions.put("asin", "Math.asin");
    jsFunctions.put("asinh", "Math.asinh");
    jsFunctions.put("atan", "Math.atan");
    jsFunctions.put("atan2", "Math.atan2");
    jsFunctions.put("atanh", "Math.atanh");
    jsFunctions.put("cbrt", "Math.cbrt");
    jsFunctions.put("ceil", "Math.ceil");
    jsFunctions.put("cos", "Math.cos");
    jsFunctions.put("cosh", "Math.cosh");
    jsFunctions.put("exp", "Math.exp");
    jsFunctions.put("floor", "Math.floor");
    jsFunctions.put("log", "Math.log");
    jsFunctions.put("max", "Math.max");
    jsFunctions.put("min", "Math.min");
    jsFunctions.put("pow", "Math.pow");
    jsFunctions.put("random", "Math.random");
    jsFunctions.put("round", "Math.round");
    jsFunctions.put("sin", "Math.sin");
    jsFunctions.put("sinh", "Math.sinh");
    jsFunctions.put("sqrt", "Math.sqrt");
    jsFunctions.put("tan", "Math.tan");
    jsFunctions.put("tanh", "Math.tanh");
    jsFunctions.put("trunc", "Math.trunc");
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length > 1) {
      StringBuilder expression = new StringBuilder();
      for (int i = 1; i < args.length; i++) {
        expression.append(args[i]);
      }
      if (dangerousExpression(expression.toString())) {
        return showUsage(sender);
      }
      try {
        Double result = Double
            .parseDouble(engine.eval(formatExpression(expression.toString())).toString());
        return valid(sender, expression.toString(), result);
      } catch (Exception exception) {
        return showUsage(sender);
      }

    }
    return true;
  }

  // Sends the result to the user
  private boolean valid(CommandSender sender, String expression, Double result) {
    Utils.msg(sender, expression + " = " + Utils.format(result));
    return true;
  }

  // Inform the sender of the correct usage
  private boolean showUsage(CommandSender sender) {
    Utils.msg(sender, "Invalid expression");
    return true;
  }

  // Checks if the given expression contains any potentially dangerous words
  private boolean dangerousExpression(String expression) {
    return expression.matches(".*(;|var|function|console).*");
  }

  // Formats the given expression into java script code
  private String formatExpression(String expression) {
    return jsFunctions.entrySet()
                      .stream()
                      .reduce(expression.toLowerCase(Locale.ROOT),
                          (s, e) -> s.replace(e.getKey(), e.getValue()),
                          (s1, s2) -> null);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias,
      String[] args) {
    List<String> tabs = new ArrayList<>();
    if (args.length == 2) {
      tabs.add("<Expression>");
    }
    return tabs;
  }

}
