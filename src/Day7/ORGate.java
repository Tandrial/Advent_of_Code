package Day7;

public class ORGate extends BinaryGate {

	public ORGate(Gate in1, Gate in2, String out) {
		super(in1, in2, out);
	}

	@Override
	public int getValue() {
		return in1.getValue() | in2.getValue();
	}

	@Override
	public String toString() {
		return in1 + " OR " + in2 + " -> " + getOut();
	}
}