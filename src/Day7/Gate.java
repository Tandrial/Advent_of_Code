package Day7;

abstract public class Gate {
	private String out;

	public abstract int getValue();

	abstract public boolean canSolve();

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
}

abstract class UnaryGate extends Gate {
	Gate in;

	public boolean canSolve() {
		return in.canSolve();
	}
}

abstract class BinaryGate extends Gate {
	Gate in1;
	Gate in2;

	public boolean canSolve() {
		return in1.canSolve() && in2.canSolve();
	}
}
