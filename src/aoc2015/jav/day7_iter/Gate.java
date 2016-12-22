package aoc2015.jav.day7_iter;

abstract class Gate {
  private final String out;

  Gate(String out) {
    this.out = out;
  }

  abstract public int getValue();

  abstract public boolean canSolve();

  public String getOut() {
    return out;
  }
}

abstract class UnaryGate extends Gate {
  final Gate in;

  UnaryGate(Gate in, String out) {
    super(out);
    this.in = in;
  }

  public boolean canSolve() {
    return in.canSolve();
  }
}

abstract class BinaryGate extends Gate {
  final Gate in1;
  final Gate in2;

  BinaryGate(Gate in1, Gate in2, String out) {
    super(out);
    this.in1 = in1;
    this.in2 = in2;
  }

  public boolean canSolve() {
    return in1.canSolve() && in2.canSolve();
  }
}
