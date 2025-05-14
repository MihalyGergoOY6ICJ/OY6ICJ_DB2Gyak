package utils.input;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import data.Course;
import data.Has;
import data.Student;
import utils.Builder;
import utils.UtilsDate;
import utils.input.constraints.InputConstraint;
import utils.input.constraints.InputConstraintLength;
import utils.input.constraints.InputConstraintMaxLength;
import utils.input.constraints.InputConstraintNoSpecialCharacters;
import utils.input.constraints.IntegerInputConstraintRange;
import utils.input.exception.InvalidInputException;

public class Read {
	final static private Scanner scanner = new Scanner(System.in);
	
	public static Student readStudent() throws InvalidInputException{
		System.out.println("Student");
		String neptun = validatedRead("Neptun:",InputType.STRING, new InputConstraintLength(6), new InputConstraintNoSpecialCharacters());
		String name = validatedRead("Name:", InputType.STRING, new InputConstraintMaxLength(30), new InputConstraintNoSpecialCharacters());
		int creditSum = Integer.valueOf(validatedRead("Credit Sum:", InputType.INTEGER, new IntegerInputConstraintRange(1,99)));
		LocalDate dateOfBirth = UtilsDate.stringToLocalDate(validatedRead("Date Of Birth:", InputType.DATE, null));
		String email = validatedRead("Email:", InputType.STRING, new InputConstraintMaxLength(30));
		
		Student student = Builder.of(Student::new)
				.with(Student::setNeptun, neptun)
				.with(Student::setName, name)
				.with(Student::setCreditSum, creditSum)
				.with(Student::setDateOfBirth, dateOfBirth)
				.with(Student::setEmail, email)
				.build();
		return student;
	}
	
	public static Course readCourse() throws InvalidInputException{
		System.out.println("Course");
		int id = Integer.valueOf(validatedRead("Id:", InputType.INTEGER, new IntegerInputConstraintRange(1, 99)));
		String name = validatedRead("Name:", InputType.STRING, new InputConstraintMaxLength(30));
		int credit = Integer.valueOf(validatedRead("Credit:", InputType.INTEGER, new IntegerInputConstraintRange(1, 99)));
		LocalDate examDate = UtilsDate.stringToLocalDate(validatedRead("Exam Date:", InputType.DATE, null));
		int required = Integer.valueOf(validatedRead("Required: ", InputType.INTEGER, new IntegerInputConstraintRange(0,1)));
		
		Course course = Builder.of(Course::new)
				.with(Course::setId, id)
				.with(Course::setName, name)
				.with(Course::setCredit, credit)
				.with(Course::setExamDate, examDate)
				.with(Course::setRequired, required)
				.build();
		return course;
	}
	
	public static Has readHas() throws InvalidInputException{
		System.out.println("Has");
		String neptun = validatedRead("Neptun:",InputType.STRING, new InputConstraintLength(6), new InputConstraintNoSpecialCharacters());
		int id = Integer.valueOf(validatedRead("Id:", InputType.INTEGER, new IntegerInputConstraintRange(1, 99)));
		
		Has has = Builder.of(Has::new)
				.with(Has::setNeptun, neptun)
				.with(Has::setId, id)
				.build();
		
		return has;
	}
	
	public static String validatedRead(String prompt, InputType inputType, InputConstraint...constraints) throws InvalidInputException{
		String input = read(prompt);
		InputValidator.validate(input, inputType, constraints);
		return input;
	}
	
	private static String read(String prompt) {
		if(prompt != null) {
			System.out.println(prompt);
		}
		String input = scanner.nextLine();
		return input;
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		scanner.close();
		super.finalize();
	}
}
