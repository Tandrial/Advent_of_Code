package Day7;

public class RSHIFTGate extends BinaryGate {

	public RSHIFTGate(Gate v1, Gate v2, String out) {
		this.v1 = v1;
		this.v2 = v2;
		this.setOut(out);
	}

	@Override
	public int getValue() {
		return v1.getValue() >> v2.getValue();
	}

	@Override
	public String toString() {
		return v1 + " >> " + v2 + " -> " + getOut();
	}
}