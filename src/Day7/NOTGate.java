package Day7;

public class NOTGate extends UnaryGate {

	public NOTGate(Gate v1, String out) {
		this.in = v1;
		this.setOut(out);
	}

	@Override
	public int getValue() {
		return (~in.getValue()) & 0xFFFF;
	}

	@Override
	public String toString() {
		return "~" + in + " -> " + getOut();
	}
}