package studio.craftory.craftory_utils.command.calculate;

import studio.craftory.craftory_utils.CalculateManager;
import studio.craftory.craftory_utils.command.BaseCommand;

public abstract class ManagedCommand extends BaseCommand {

  protected final CalculateManager calculateManager;

  public ManagedCommand(CalculateManager calculateManager) {
    this.calculateManager = calculateManager;
  }

}
