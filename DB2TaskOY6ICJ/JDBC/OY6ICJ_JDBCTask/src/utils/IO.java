package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.Course;
import data.Has;
import data.Student;
import utils.input.InputType;
import utils.input.InputValidator;
import utils.input.constraints.InputConstraintLength;
import utils.input.constraints.InputConstraintMaxLength;
import utils.input.constraints.InputConstraintNoSpecialCharacters;
import utils.input.constraints.IntegerInputConstraintRange;
import utils.input.exception.InvalidInputException;

public class IO {
	public static List<Student> readStudentsFromFile(String fileName) throws InvalidInputException, IOException{
		List<Student> studentList = new ArrayList<Student>();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))){
			String line = "";
			while((line = reader.readLine()) != null) {
				String[] fields = line.split(";");
				if(fields.length != 5) {
					throw new InvalidInputException("Invalid file content!");
				}
				InputValidator.validate(fields[0], InputType.STRING, new InputConstraintLength(6), new InputConstraintNoSpecialCharacters());
				InputValidator.validate(fields[1], InputType.STRING, new InputConstraintMaxLength(30));
				InputValidator.validate(fields[2], InputType.INTEGER, new IntegerInputConstraintRange(0, 99));
				InputValidator.validate(fields[3], InputType.DATE, null);
				InputValidator.validate(fields[4], InputType.STRING, new InputConstraintMaxLength(30));
				
				Student student = Builder.of(Student::new)
						.with(Student::setNeptun, fields[0])
						.with(Student::setName, fields[1])
						.with(Student::setCreditSum, Integer.valueOf(fields[2]))
						.with(Student::setDateOfBirth, UtilsDate.stringToLocalDate(fields[3]))
						.with(Student::setEmail, fields[4])
						.build();
				studentList.add(student);
			}
		}catch(FileNotFoundException e) {
			throw new InvalidInputException("The file does not exist!");
		}
		return studentList;
	}
	
	public static List<Course> readCoursesFromFile(String fileName) throws InvalidInputException, IOException{
		List<Course> courseList = new ArrayList<Course>();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))){
			String line = "";
			while((line = reader.readLine()) != null) {
				String[] fields = line.split(";");
				if(fields.length != 5) {
					throw new InvalidInputException("Invalid file content!");
				}
				InputValidator.validate(fields[0], InputType.INTEGER, new IntegerInputConstraintRange(0,99));
				InputValidator.validate(fields[1], InputType.STRING, new InputConstraintMaxLength(30));
				InputValidator.validate(fields[2], InputType.INTEGER, new IntegerInputConstraintRange(0, 99));
				InputValidator.validate(fields[3], InputType.DATE, null);
				InputValidator.validate(fields[4], InputType.INTEGER, new IntegerInputConstraintRange(0,1));
				
				Course course = Builder.of(Course::new)
						.with(Course::setId, Integer.valueOf(fields[0]))
						.with(Course::setName, fields[1])
						.with(Course::setCredit, Integer.valueOf(fields[2]))
						.with(Course::setExamDate, UtilsDate.stringToLocalDate(fields[3]))
						.with(Course::setRequired, Integer.valueOf(fields[4]))
						.build();
				courseList.add(course);
			}
		}catch(FileNotFoundException e) {
			throw new InvalidInputException("The file does not exist!");
		}
		return courseList;
	}
	
	public static List<Has> readHasesFromFile(String fileName) throws InvalidInputException, IOException{
		List<Has> hasList = new ArrayList<Has>();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))){
			String line = "";
			while((line = reader.readLine()) != null) {
				String[] fields = line.split(";");
				if(fields.length != 2) {
					throw new InvalidInputException("Invalid file content!");
				}
				InputValidator.validate(fields[0], InputType.STRING, new InputConstraintLength(6), new InputConstraintNoSpecialCharacters());
				InputValidator.validate(fields[1], InputType.INTEGER, new IntegerInputConstraintRange(0,99));
				
				Has has = Builder.of(Has::new)
						.with(Has::setNeptun, fields[0])
						.with(Has::setId, Integer.valueOf(fields[1]))
						.build();
				hasList.add(has);
			}
		}catch(FileNotFoundException e) {
			throw new InvalidInputException("The file does not exist!");
		}
		return hasList;
	}
}
