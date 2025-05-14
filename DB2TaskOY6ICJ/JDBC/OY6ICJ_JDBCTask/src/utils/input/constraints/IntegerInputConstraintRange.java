package utils.input.constraints;

public class IntegerInputConstraintRange extends InputConstraint{
	private int min;
	private int max;
	
	
	public IntegerInputConstraintRange(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}	
	
}
