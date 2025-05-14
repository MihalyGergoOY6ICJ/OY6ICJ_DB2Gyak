package data;

import java.util.List;

import db.DbUtils;
import utils.UtilsDate;
import utils.input.exception.InvalidInputException;

public class Has {
	final private static String format = "|    %-6s    |     %-2d     |%n";
	
	private String neptun; //FK - Student table 
	private int id; //FK - Course table
	

	public static void createDbTable() {
		String sqlp = "CREATE TABLE Has("
				+ "neptun CHAR(6), "
				+ "id INTEGER(2), "
				+ "FOREIGN KEY(neptun) REFERENCES Student(neptun), "
				+ "FOREIGN KEY(id) REFERENCES Course(id)"
				+ ");";
		DbUtils.createDbTable(sqlp);
	}
	
	public void insertIntoDb() throws InvalidInputException{
		int neptunCount = DbUtils.selectCount("Student", "neptun", neptun);
		if(neptunCount == 0) {
			throw new InvalidInputException("Student table does not have neptun " + neptun);
		}
		int idCount = DbUtils.selectCount("Course", "id", Integer.toString(id));
		if(idCount == 0) {
			throw new InvalidInputException("Course table does not have id " + id);
		}
		String sqlp = "INSERT INTO Has VALUES("
				+ "'" + neptun + "', "
				+ id
				+ ");";
		DbUtils.insertIntoDb(sqlp);
	}
	
	public static void insertIntoDb(List<Has> hasList) throws InvalidInputException{
		for(Has has : hasList) {
			int neptunCount = DbUtils.selectCount("Student", "neptun", has.getNeptun());
			if(neptunCount == 0) {
				throw new InvalidInputException("Student table does not have neptun " + has.getNeptun());
			}
			int idCount = DbUtils.selectCount("Course", "id", Integer.toString(has.getId()));
			if(idCount == 0) {
				throw new InvalidInputException("Course table does not have id " + has.getId());
			}
			
		}
		String sqlp = "INSERT INTO Has Values(?, ?);";
		
		DbUtils.insertHasListIntoDb(sqlp, hasList);
	}
	

	public static void deleteRecord(String neptun, int id) {
		String sqlp = "DELETE FROM Has "
				+ "WHERE neptun = '" + neptun + "' AND id = " + id + ";";
		DbUtils.deleteRecord(sqlp);
	}
	
	public static void printTable(List<Has> hasList) {
		System.out.println("Has");
		System.out.format("+--------------+------------+%n");
		System.out.format("| neptun(text) | id(number) |%n"); 
		System.out.format("+--------------+------------+%n");
		for(Has has : hasList) {
			System.out.format(format, has.getNeptun(), has.getId());		
		}
		System.out.format("+--------------+------------+%n");
	}
	

	public String getNeptun() {
		return neptun;
	}


	public int getId() {
		return id;
	}


	public void setNeptun(String neptun) {
		this.neptun = neptun;
	}


	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Has [neptun=" + neptun + ", id=" + id + "]";
	}	
	
	
}
