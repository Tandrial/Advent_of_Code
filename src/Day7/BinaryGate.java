package Day7;

abstract public class BinaryGate extends Gate {
	Gate v1;
	Gate v2;

	public boolean canSolve() {
		return v1.canSolve() && v2.canSolve();
	}
}
