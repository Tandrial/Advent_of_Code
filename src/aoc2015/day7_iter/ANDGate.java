package aoc2015.day7_iter;

public class ANDGate extends BinaryGate {

	public ANDGate(Gate in1, Gate in2, String out) {
		super(in1, in2, out);
	}

	@Override
	public int getValue() {
		return in1.getValue() & in2.getValue();
	}

	@Override
	public String toString() {
		return in1 + " AND " + in2 + " -> " + getOut();
	}
}