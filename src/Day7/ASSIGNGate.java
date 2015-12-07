package Day7;

public class ASSIGNGate extends UnaryGate {

	public ASSIGNGate(Gate v1, String out) {
		this.in = v1;
		this.setOut(out);
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