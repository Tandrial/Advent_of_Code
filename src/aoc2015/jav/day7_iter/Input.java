package aoc2015.jav.day7_iter;

public class Input extends Gate {
  private final String value;

  public Input(String value) {
    super("");
    this.value = value;
  }

  @Override
  public boolean canSolve() {
    return Day7.isNumeric(value) || Day7.varLookUp.containsKey(value);
  }

  @Override
  public int getValue() {
    if (Day7.isNumeric(value))
      return Integer.valueOf(value);
    else {
      Integer result = Day7.varLookUp.get(value);
      return result == null ? -1 : result;
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
