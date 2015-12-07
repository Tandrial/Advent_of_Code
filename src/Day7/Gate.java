package Day7;

abstract public class Gate {
	private String out;

	abstract public int getValue();

	abstract public boolean canSolve();

	public String getOut() {
		return out;
	}

	public Gate(String out) {
		this.out = out;
	}
}

abstract class UnaryGate extends Gate {
	Gate in;

	public UnaryGate(Gate in, String out) {
		super(out);
		this.in = in;
	}

	public boolean canSolve() {
		return in.canSolve();
	}
}

abstract class BinaryGate extends Gate {
	Gate in1;
	Gate in2;

	public BinaryGate(Gate in1, Gate in2, String out) {
		super(out);
		this.in1 = in1;
		this.in2 = in2;
	}

	public boolean canSolve() {
		return in1.canSolve() && in2.canSolve();
	}
}
