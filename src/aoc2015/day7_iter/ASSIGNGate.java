package aoc2015.day7_iter;

public class ASSIGNGate extends UnaryGate {

  public ASSIGNGate(Gate in, String out) {
    super(in, out);
  }

  @Override
  public int getValue() {
    return in.getValue();
  }

  @Override
  public String toString() {
    return in + " -> " + getOut();
  }
}
