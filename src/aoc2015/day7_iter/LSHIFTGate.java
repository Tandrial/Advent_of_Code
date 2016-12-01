package aoc2015.day7_iter;

public class LSHIFTGate extends BinaryGate {

	public LSHIFTGate(Gate in1, Gate in2, String out) {
		super(in1, in2, out);
	}

	@Override
	public int getValue() {
		return (in1.getValue() << in2.getValue()) & 0xFFFF;
	}

	@Override
	public String toString() {
		return in1 + " << " + in2 + " -> " + getOut();
	}
}