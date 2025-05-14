package utils.input.constraints;

public class InputConstraintLength extends InputConstraint{
	private int length;
	
	public InputConstraintLength(int length) {
		this.length = length;
	}

	public int getLength() {
		return length;
	}
}
