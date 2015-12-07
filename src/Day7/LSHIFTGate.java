package Day7;

public class LSHIFTGate extends BinaryGate {

	public LSHIFTGate(Gate v1, Gate v2, String out) {
		this.in1 = v1;
		this.in2 = v2;
		this.setOut(out);
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