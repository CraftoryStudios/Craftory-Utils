package studio.craftory.craftorycalculate.maths;

public enum Operator {

  ADD("+") {
    public double apply(double x, double y) {
      return x + y;
    }
  },

  SUBTRACT("-") {
    public double apply(double x, double y) {
      return x - y;
    }
  },

  MULTIPLY("*") {
    public double apply(double x, double y) {
      return x * y;
    }
  },

  DIVIDE("/") {
    public double apply(double x, double y) {
      return x / y;
    }
  },

  MODULO("%") {
    public double apply(double x, double y) {
      return x % y;
    }
  };

  private final String symbol;

  Operator(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }

  public abstract double apply(double x, double y);
}
