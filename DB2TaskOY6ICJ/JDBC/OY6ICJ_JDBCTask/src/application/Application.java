package application;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.menu.MainMenu;
import application.menu.Menu;
import application.menu.TableName;
import data.Course;
import data.Has;
import data.Student;
import db.DbConnection;
import db.DbUtils;
import log.LogUtils;
import utils.IO;
import utils.UtilsDate;
import utils.input.InputType;
import utils.input.Read;
import utils.input.constraints.InputConstraint;
import utils.input.constraints.InputConstraintLength;
import utils.input.constraints.InputConstraintMaxLength;
import utils.input.constraints.InputConstraintNoSpecialCharacters;
import utils.input.constraints.IntegerInputConstraintRange;
import utils.input.exception.InvalidInputException;

public class Application {
	public static void main(String[] args) throws SQLException {
		final Logger logger = LogUtils.getLogger();
		if(args.length == 0) {
			LogUtils.initLogLevel(null);
		}
		else if(args[0].equals("debug")){
			LogUtils.initLogLevel(args[0]);
		}
		
		
		try {
			DbConnection.init();
		}catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "JDBC driver could not be found, aborting!");
			System.exit(1);
		}catch (SQLException e) {
			logger.log(Level.SEVERE, "Failed to connect to the db, aborting!");
			System.exit(1);
		}
		
		
		
		boolean exit = false;
		
		while(!exit) {
			try {
				Menu selectedMenu = MainMenu.displayMainMenu();
				switch(selectedMenu) {
				case EXIT:
					exit = true;break;
				case DISPLAY:
					display();break;
				case INSERT:
					insert();break;
				case MODIFY:
					modify();break;
				case DELETE:
					delete();break;
				case GET:
					get();break;
				case INSERT_FROM_FILE:
					insertFromFile();break;
				}
			}catch (InvalidInputException e) {
				System.out.println(e.getMessage());
			}
			catch(IOException e) {
				logger.log(Level.SEVERE, "There was a problem reading the file, aborting!");
				System.exit(1);
			}
		}
	}
	

	private static void display() throws InvalidInputException {
		TableName tableName = MainMenu.displayTableSelectorMenu(false);
		switch (tableName) {
		case STUDENT:
			List<Student> studentList = DbUtils.selectAllStudent();
			Student.printTable(studentList);
			break;
		case COURSE:
			List<Course> courseList = DbUtils.selectAllCourse();
			Course.printTable(courseList);
			break;
		case HAS:	
			List<Has> hasList = DbUtils.selectAllHas();
			Has.printTable(hasList);
			break;
		}
	}
	
	private static void insert() throws InvalidInputException{
		TableName tableName = MainMenu.displayTableSelectorMenu(false);
		switch (tableName) {
		case STUDENT:
			Student student = Read.readStudent();
			student.insertIntoDb();
			break;
		case COURSE:
			Course course = Read.readCourse();
			course.insertIntoDb();
			break;
		case HAS:
			Has has = Read.readHas();
			has.insertIntoDb();
			break;
		}
	}
	
	private static void modify() throws InvalidInputException{
		TableName tableName = MainMenu.displayTableSelectorMenu(true);
		
		int menu;
		String fieldName = "";
		String fieldValue = "";
		
		switch(tableName) {
		case STUDENT:
			String neptun = Read.validatedRead("Neptun:", InputType.STRING, new InputConstraintLength(6), new InputConstraintNoSpecialCharacters());
			if(DbUtils.selectCount("Student", "neptun", neptun) == 0) {
				throw new InvalidInputException("Student table does not have neptun " + neptun);
			}
			menu = MainMenu.displayUpdateFieldSelectorMenu(tableName);
			switch(menu) {
			case 0:
				fieldName =  "name";
				fieldValue = Read.validatedRead("name: ", InputType.STRING, new InputConstraintMaxLength(30));
				break;
			case 1:
				fieldName = "credit_sum";
				fieldValue = Read.validatedRead("credit_sum: ", InputType.INTEGER, new IntegerInputConstraintRange(1,99));
				break;
			case 2:
				fieldName = "day_of_birth";
				fieldValue = Read.validatedRead("day_of_birth:", InputType.DATE, null);
				break;
			case 3:
				fieldName = "email";
				fieldValue = Read.validatedRead("email:", InputType.STRING, new InputConstraintMaxLength(30));
				break;
			}
			Student.updateRecord(neptun, fieldName, fieldValue);
			break;
		case COURSE:
			int id = Integer.valueOf(Read.validatedRead("Id: ", InputType.INTEGER, new IntegerInputConstraintRange(1,99)));
			if(DbUtils.selectCount("Course", "id", "" + id) == 0) {
				throw new InvalidInputException("Course table does not have id " + id);
			}
			menu = MainMenu.displayUpdateFieldSelectorMenu(tableName);
			switch(menu) {
			case 0:
				fieldName = "name";
				fieldValue = Read.validatedRead("name: ", InputType.STRING, new InputConstraintMaxLength(30));
				break;
			case 1:
				fieldName = "credit";
				fieldValue = Read.validatedRead("credit: ", InputType.INTEGER, new IntegerInputConstraintRange(1,99));
				break;
			case 2:
				fieldName = "exam_date";
				fieldValue = Read.validatedRead("exam_date: ", InputType.DATE, null);
				break;
			case 3:
				fieldName = "required";
				fieldValue = Read.validatedRead("required: ", InputType.INTEGER, new IntegerInputConstraintRange(0,1));
				break;
			}
			Course.updateRecord(id, fieldName, fieldValue);
			break;
		}
	}
	
	private static void delete() throws InvalidInputException{
		TableName tableName = MainMenu.displayTableSelectorMenu(false);
		switch(tableName) {
		case STUDENT:
			String neptun = Read.validatedRead("Neptun:", InputType.STRING, new InputConstraintLength(6), new InputConstraintNoSpecialCharacters());
			if(DbUtils.selectCount("Has", "neptun", neptun) != 0) {
				throw new InvalidInputException("Has table has atleast one reference to neptun " + neptun + "!");
			}
			Student.deleteRecord(neptun);
			break;
		case COURSE:
			int id = Integer.valueOf(Read.validatedRead("Id: ", InputType.INTEGER, new IntegerInputConstraintRange(1,99)));
			if(DbUtils.selectCount("Has", "id", "" + id) != 0) {
				throw new InvalidInputException("Has table has atleast one reference to id " + id + "!");
			}
			Course.deleteRecord(id);
			break;
		case HAS:
			String hasNeptun = Read.validatedRead("Neptun:", InputType.STRING, new InputConstraintLength(6), new InputConstraintNoSpecialCharacters());
			int hasId = Integer.valueOf(Read.validatedRead("Id: ", InputType.INTEGER, new IntegerInputConstraintRange(1,99)));
			Has.deleteRecord(hasNeptun, hasId);
			break;
		}
	}
	
	public static void get() throws InvalidInputException{
		int input = MainMenu.displayGetSelectorMenu();
		List<String> resultList = null;
		switch(input) {
		case 0:
			//Student name and email by neptun
			String neptunA = Read.validatedRead("Neptun:", InputType.STRING, new InputConstraintLength(6), new InputConstraintNoSpecialCharacters());
			resultList = DbUtils.genericSelect(new String[] {"name", "email"}, "Student", "neptun = '" + neptunA + "'");
			break;
		case 1:
			//Student names by date of birth (born after)
			LocalDate dateOfBirth = UtilsDate.stringToLocalDate(Read.validatedRead("Date Of Birth:", InputType.DATE, null));
			resultList = DbUtils.genericSelect(new String[] {"name"}, "Student", "date_of_birth > '" + dateOfBirth + "'");
			break;
		case 2:
			//Courses by neptun and if it is required
			String neptunB = Read.validatedRead("Neptun:", InputType.STRING, new InputConstraintLength(6), new InputConstraintNoSpecialCharacters());
			int required = Integer.valueOf(Read.validatedRead("Required:", InputType.INTEGER, new IntegerInputConstraintRange(0, 1)));
			resultList = DbUtils.genericSelect(
					new String[] {"Course.name"}, 
					"Student INNER JOIN Has ON Student.neptun = Has.neptun INNER JOIN Course ON Has.id = Course.id ", 
					"Student.neptun = '"+ neptunB + "' AND Course.required = " + required
			);
			break;
		case 3:
			//Exam date and credit by neptun
			String neptunC = Read.validatedRead("Neptun:", InputType.STRING, new InputConstraintLength(6), new InputConstraintNoSpecialCharacters());
			resultList = DbUtils.genericSelect(
					new String[] {"Course.exam_date", "Course.credit"}, 
					"Student INNER JOIN Has ON Student.neptun = Has.neptun INNER JOIN Course ON Has.id = Course.id", 
					"Student.neptun = '" + neptunC + "'" 
			);
			break;
		case 4:
			//Credit by course name
			String courseName = Read.validatedRead("Name:", InputType.STRING, new InputConstraintMaxLength(30));
			resultList = DbUtils.genericSelect(new String[] {"credit"}, "Course", "name = '" + courseName + "'");
			break;
		}
		if(resultList.isEmpty()) {
			System.out.println("No result found!");
		}
		for(String result : resultList) {
			System.out.println(result);
		}
	}
	
	private static void insertFromFile() throws InvalidInputException, IOException{
		TableName tableName = MainMenu.displayTableSelectorMenu(false);
		String fileName = Read.validatedRead("File name:", InputType.STRING, null);
		switch(tableName) {
		case STUDENT:
			List<Student> studentList = IO.readStudentsFromFile(fileName);
			Student.insertIntoDb(studentList);
			break;
		case COURSE:
			List<Course> courseList = IO.readCoursesFromFile(fileName);
			Course.insertIntoDb(courseList);
			break;
		case HAS:
			List<Has> hasList = IO.readHasesFromFile(fileName);
			Has.insertIntoDb(hasList);
			break;
		}
	}
}
