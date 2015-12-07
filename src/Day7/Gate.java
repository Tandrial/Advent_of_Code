package Day7;

abstract public class Gate {
	private String out;

	public abstract int getValue();

	public boolean canSolve() {
		return true;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
}
