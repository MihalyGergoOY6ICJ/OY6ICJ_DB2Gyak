package utils.input.constraints;

import java.util.regex.Pattern;

public class InputConstraintNoSpecialCharacters extends InputConstraint{
	private Pattern pattern = Pattern.compile("[^a-z0-9 öüóőúéáűí]", Pattern.CASE_INSENSITIVE);
	
	public Pattern getPattern() {
		return pattern;
	}
}
