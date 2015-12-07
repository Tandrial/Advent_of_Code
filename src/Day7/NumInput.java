package Day7;

public class NumInput extends Gate {
	int value;

	public NumInput(int value) {
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value + "";
	}

	@Override
	public boolean canSolve() {
		return true;
	}
}