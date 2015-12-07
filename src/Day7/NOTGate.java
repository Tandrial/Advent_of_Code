package Day7;

public class NOTGate extends UnaryGate {

	public NOTGate(Gate in, String out) {
		super(in, out);
	}

	@Override
	public int getValue() {
		return (~in.getValue()) & 0xFFFF;
	}

	@Override
	public String toString() {
		return "NOT " + in + " -> " + getOut();
	}
}