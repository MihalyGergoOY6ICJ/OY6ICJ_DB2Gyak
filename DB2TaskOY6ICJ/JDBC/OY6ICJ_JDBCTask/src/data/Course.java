package data;

import java.time.LocalDate;
import java.util.List;

import db.DbUtils;

public class Course {
	
	final static private String format = "|     %-2d     | %-30s |       %-2d       |    %-10s   |         %-1d         |%n";
	
	private int id; //PK, max 99
	private String name; //max 30 characters
	private int credit; //max 99
	private LocalDate examDate; //YYYY-MM-DD
	private int required; //int literal (1-true, 0-false)
	

	public static void createDbTable() {
		String sqlp = "CREATE TABLE Course("
				+ "id INTEGER(2) PRIMARY KEY, "
				+ "name CHAR(30) NOT NULL, "
				+ "credit INTEGER(2) NOT NULL, "
				+ "exam_date DATE NOT NULL, "
				+ "required INTEGER(1) NOT NULL, "
				+ "CHECK(required = 1 OR required = 0)"
				+ ");";
		DbUtils.createDbTable(sqlp);
	}
	
	
	public void insertIntoDb() {
		String sqlp = "INSERT INTO Course VALUES("
				+ id + ", "
				+ "'" + name + "', "
				+ credit + ","
				+ "'" + examDate + "', "
				+ required
				+ ");";
		DbUtils.insertIntoDb(sqlp);
	}
	
	
	public static void insertIntoDb(List<Course> courseList) {
		String sqlp = "INSERT INTO Course Values(?, ?, ?, ?, ?);";
		
		DbUtils.insertCourseListIntoDb(sqlp, courseList);
	}
	
	
	public static void updateRecord(int id, String fieldName, String fieldValue) {
		String sqlp = "UPDATE Course SET " + fieldName + " = '" + fieldValue + "' WHERE id = " + id + ";";
		DbUtils.updateRecord(sqlp);
	}
	

	public static void deleteRecord(int id) {
		String sqlp = "DELETE FROM Course "
				+ "WHERE id = " + id + ";";
		DbUtils.deleteRecord(sqlp);
	}
	public static void printTable(List<Course> courseList) {
		System.out.println("Course");
		System.out.format("+------------+--------------------------------+----------------+-----------------+-------------------+%n");
		System.out.format("| id(number) |           name(text)           | credit(number) | exam_date(date) | required(boolean) |%n");
		System.out.format("+------------+--------------------------------+----------------+-----------------+-------------------+%n");
		for(Course course : courseList) {
			System.out.format(format, course.getId(), course.getName(), course.getCredit(), course.getExamDate().toString(), course.getRequired());
		}
		System.out.format("+------------+--------------------------------+----------------+-----------------+-------------------+%n");
	}


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public int getCredit() {
		return credit;
	}


	public LocalDate getExamDate() {
		return examDate;
	}


	public int getRequired() {
		return required;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setCredit(int credit) {
		this.credit = credit;
	}


	public void setExamDate(LocalDate examDate) {
		this.examDate = examDate;
	}


	public void setRequired(int required) {
		this.required = required;
	}


	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", credit=" + credit + ", examDate=" + examDate + ", required="
				+ required + "]";
	}
}
