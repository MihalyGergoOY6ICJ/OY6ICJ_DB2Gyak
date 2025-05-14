package utils.input.constraints;

public class InputConstraintMaxLength extends InputConstraint{
	private int maxLength;
	
	public InputConstraintMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getMaxLength() {
		return maxLength;
	}
	
	
}
