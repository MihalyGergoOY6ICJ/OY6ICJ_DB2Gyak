package utils.input;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;

import utils.UtilsDate;
import utils.input.constraints.InputConstraint;
import utils.input.constraints.InputConstraintLength;
import utils.input.constraints.InputConstraintMaxLength;
import utils.input.constraints.InputConstraintNoSpecialCharacters;
import utils.input.constraints.IntegerInputConstraintRange;
import utils.input.exception.InvalidInputException;

public class InputValidator {
	public static void validate(String input, InputType inputType, InputConstraint... constraints) throws InvalidInputException{
		if(input == null || input == "") {
			throw new InvalidInputException("Input cannot be empty!");
		}
		
		typeValidate(input, inputType);
		
		constraintsValidate(input, constraints);
	}
	
	private static void typeValidate(String input, InputType inputType) throws InvalidInputException{
		switch(inputType) {
		case STRING:
			//STRING does not require type checking
			break;
		case INTEGER:
			integerValidate(input);break;
		case DATE:
			dateValidate(input);break;
		}
	}
	
	private static void integerListValidate(String input) throws InvalidInputException{
		String[] fields = input.split(",");
		for(String field : fields) {
			integerValidate(field);
		}
	}
	
	private static void dateValidate(String input) throws InvalidInputException{
		try {
			UtilsDate.stringToLocalDate(input);
		}catch (DateTimeParseException e) {
			throw new InvalidInputException("Input is not a valid date (YYYY-MM-DD)!");
		}
	}
	
	private static void integerValidate(String input) throws InvalidInputException{
		try {
			Integer.parseInt(input);
		}catch(NumberFormatException e) {
			throw new InvalidInputException("Input is not a valid integer!");
		}
	}
	
	private static void constraintsValidate(String input, InputConstraint...constraints) throws InvalidInputException{
		if(constraints != null) {
			for(InputConstraint constraint : constraints) {
				if(constraint instanceof IntegerInputConstraintRange) {
					constraintRangeValidate(Integer.parseInt(input), (IntegerInputConstraintRange)constraint);
				}
				if(constraint instanceof InputConstraintLength) {
					constraintLengthValidate(input, (InputConstraintLength)constraint);
				}
				if(constraint instanceof InputConstraintMaxLength) {
					constraintMaxLengthValidate(input, (InputConstraintMaxLength)constraint);
				}
				if(constraint instanceof InputConstraintNoSpecialCharacters) {
					constraintNoSpecialCharacters(input, (InputConstraintNoSpecialCharacters)constraint);
				}
			}
		}
	}
	
	private static void constraintNoSpecialCharacters(String input, InputConstraintNoSpecialCharacters constraint) throws InvalidInputException{
		Matcher m = constraint.getPattern().matcher(input);
		if(m.find()) {
			throw new InvalidInputException("Input cannot contain special characters!");
		}
	}
	
	private static void constraintLengthValidate(String input, InputConstraintLength constraint) throws InvalidInputException{
		if(input.length() != constraint.getLength()) {
			throw new InvalidInputException("Input is not the correct length (" + constraint.getLength() + ")!");
		}
	}
	
	private static void constraintRangeValidate(int input, IntegerInputConstraintRange constraint) throws InvalidInputException{
		if(input < constraint.getMin() || input > constraint.getMax()) {
			throw new InvalidInputException("Input is out of range [" + constraint.getMin() + ", " + constraint.getMax() + "] inclusive!"); 
		}
	}
	
	private static void constraintMaxLengthValidate(String input, InputConstraintMaxLength constraint) throws InvalidInputException{
		if(input.length() > constraint.getMaxLength()) {
			throw new InvalidInputException("Input length is too long (max " + constraint.getMaxLength() + "characters)!");
		}
	}
}

