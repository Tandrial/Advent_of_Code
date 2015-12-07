package Day7;

public class NOTGate extends UnaryGate {

	public NOTGate(Gate v1, String out) {
		this.v1 = v1;
		this.setOut(out);
	}

	@Override
	public int getValue() {
		return (~v1.getValue()) & 0xFFFF;
	}

	@Override
	public String toString() {
		return "~" + v1 + " -> " + getOut();
	}

}