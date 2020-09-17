package studio.craftory.craftorycalculate.maths;

public enum SingleFunction {

  ABS("abs") {
    public double apply(double x) {
      return Math.abs(x);
    }
  };

  private final String code;

  SingleFunction(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }

  public abstract double apply(double x);
}
