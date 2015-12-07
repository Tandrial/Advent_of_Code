package Day7;

public class ORGate extends BinaryGate {

	public ORGate(Gate v1, Gate v2, String out) {
		this.v1 = v1;
		this.v2 = v2;
		this.setOut(out);
	}

	@Override
	public int getValue() {
		return v1.getValue() | v2.getValue();
	}

	@Override
	public String toString() {
		return v1 + " OR " + v2 + " -> " + getOut();
	}
}