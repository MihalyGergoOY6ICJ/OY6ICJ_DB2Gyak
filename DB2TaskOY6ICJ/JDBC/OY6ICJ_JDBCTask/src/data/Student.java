package data;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import db.DbUtils;
import log.LogUtils;

public class Student {
	
	final private static String format = "|    %-6s    | %-30s |         %-2d         |      %-10s     | %-30s|%n";
	
	private String neptun; //6 characters, PK
	private String name; //max 30 characters
	private int creditSum; //max 99
	private LocalDate dateOfBirth; //YYYY-MM-DD
	private String email; //max 30 characters
	
	
	public static void createDbTable() {
		String sqlp = "CREATE TABLE Student("
				+ "neptun CHAR(6) PRIMARY KEY, "
				+ "name CHAR(30) NOT NULL, "
				+ "credit_sum INTEGER(2) NOT NULL, "
				+ "date_of_birth DATE NOT NULL, "
				+ "email CHAR(30) NOT NULL"
				+ ");";
		DbUtils.createDbTable(sqlp);
	}
	
	
	public void insertIntoDb() {
		String sqlp = "INSERT INTO Student VALUES("
				+ "'" + neptun + "', "
				+ "'" + name + "', "
				+ creditSum + ", " 
				+ "'" + dateOfBirth + "', "
				+ "'" + email + "'"
				+ ");";
		DbUtils.insertIntoDb(sqlp);
	}
	
	public static void insertIntoDb(List<Student> studentList) {
		String sqlp = "INSERT INTO Student Values(?, ?, ?, ?, ?);";
		
		DbUtils.insertStudentListIntoDb(sqlp, studentList);
	}

	public static void updateRecord(String neptun, String fieldName, String fieldValue) {
		String sqlp = "UPDATE Student "
				+ "SET " + fieldName + " = '" + fieldValue + "' "
				+ "WHERE neptun = '" + neptun + "';";
		DbUtils.updateRecord(sqlp);
	}
	
	public static void deleteRecord(String neptun) {
		String sqlp = "DELETE FROM Student "
				+ "WHERE neptun = '" + neptun + "';";
		DbUtils.deleteRecord(sqlp);
	}
	
	public static void printTable(List<Student> studentList) {
		System.out.println("Student");
		System.out.format("+--------------+--------------------------------+--------------------+---------------------+-------------------------------+%n");
		System.out.format("| neptun(text) |           name(text)           | credit_sum(number) | date_of_birth(date) |          email(text)          |%n"); 
		System.out.format("+--------------+--------------------------------+--------------------+---------------------+-------------------------------+%n");
		for(Student student : studentList) {
			System.out.format(format, student.getNeptun(), student.getName(), student.getCreditSum(), student.getDateOfBirth().toString(), student.getEmail());
		}
		System.out.format("+--------------+--------------------------------+--------------------+---------------------+-------------------------------+%n");
	}
	

	public void setNeptun(String neptun) {
		this.neptun = neptun;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setCreditSum(int creditSum) {
		this.creditSum = creditSum;
	}


	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	

	public String getNeptun() {
		return neptun;
	}


	public String getName() {
		return name;
	}


	public int getCreditSum() {
		return creditSum;
	}


	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}


	public String getEmail() {
		return email;
	}


	@Override
	public String toString() {
		return "Student [neptun=" + neptun + ", name=" + name + ", creditSum=" + creditSum + ", dateOfBirth="
				+ dateOfBirth + ", email=" + email + "]";
	}
}
