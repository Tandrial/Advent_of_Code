package Day7;

public class ASSIGNGate extends UnaryGate {

	public ASSIGNGate(Gate v1, String out) {
		this.v1 = v1;
		this.setOut(out);
	}

	@Override
	public int getValue() {
		return v1.getValue();
	}

	@Override
	public String toString() {
		return v1 + " -> " + getOut();
	}
}