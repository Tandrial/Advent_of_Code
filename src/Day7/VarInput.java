package Day7;

public class VarInput extends Gate {
	String value;

	public VarInput(String value) {
		super("");
		this.value = value;
	}

	@Override
	public boolean canSolve() {
		return Day7.table.containsKey(value);
	}

	@Override
	public int getValue() {
		Integer result = Day7.table.get(value);
		return result == null ? -1 : result;
	}

	@Override
	public String toString() {
		return value;
	}
}