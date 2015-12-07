package Day7;

abstract public class UnaryGate extends Gate {
	Gate v1;

	public boolean canSolve() {
		return v1.canSolve();
	}

	public abstract int getValue();

}
