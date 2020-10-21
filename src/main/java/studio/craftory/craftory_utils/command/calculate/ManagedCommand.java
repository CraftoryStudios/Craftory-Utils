package studio.craftory.craftory_utils.command.calculate;

import studio.craftory.craftory_utils.CalculateManager;
import studio.craftory.craftory_utils.command.IBaseCommand;

public abstract class ManagedCommand implements IBaseCommand {

  protected final CalculateManager calculateManager;

  protected ManagedCommand(CalculateManager calculateManager) {
    this.calculateManager = calculateManager;
  }

}
