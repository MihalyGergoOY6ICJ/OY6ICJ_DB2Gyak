package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import data.Course;
import data.Has;
import data.Student;
import log.LogUtils;
import utils.Builder;

public class DbUtils {
	final private static Logger logger = LogUtils.getLogger();
	
	final private static Connection conn = DbConnection.getConn();
	
	public static void commandExec(String command) throws SQLException{
		Statement s = conn.createStatement();
		s.execute(command);
	}

	public static void createDbTable(String sqlp) {
		try {
			commandExec(sqlp);
			logger.log(Level.INFO, "Table succesfully created: " + sqlp);
		}catch(SQLException e) {
			logger.log(Level.WARNING, "Table could not be created: " + e.getMessage() + "\n\tSQL: " + sqlp);
		}
	}
	
	public static void insertIntoDb(String sqlp) {
		try {
			commandExec(sqlp);
			System.out.println("Insert successful!");
			logger.log(Level.INFO, "Insert successful: " + sqlp);
		}catch(SQLException e) {
			logger.log(Level.WARNING, "Insert failed: " + e.getMessage() + "\n\tSQL: " + sqlp);
		}
	}
	
	public static void updateRecord(String sqlp) {
		try {
			commandExec(sqlp);
			System.out.println("Update succesful!");
			logger.log(Level.INFO, "Update successful: " + sqlp);
		}catch(SQLException e) {
			logger.log(Level.WARNING, "Update failed: " + e.getMessage() + "\n\tSQL: " + sqlp);
		}
	}
	
	public static void deleteRecord(String sqlp) {
		try {
			commandExec(sqlp);
			System.out.println("Delete successful!");
			logger.log(Level.INFO, "Delete successful: " + sqlp);
		}catch(SQLException e) {
			logger.log(Level.WARNING, "Delete failed: " + e.getMessage() + "\n\tSQL: " + sqlp);
		}
	}
	
	public static List<Student> selectAllStudent() {
		List<Student> studentList = new ArrayList<Student>();
		
		String sqlp = "SELECT * FROM Student";	
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlp);
			while(resultSet.next()) {
				Student student = Builder.of(Student::new)
						.with(Student::setNeptun, resultSet.getString(1))
						.with(Student::setName, resultSet.getString(2))
						.with(Student::setCreditSum, resultSet.getInt(3))
						.with(Student::setDateOfBirth, Date.valueOf(resultSet.getString(4)).toLocalDate())
						.with(Student::setEmail, resultSet.getString(5))
						.build();
			studentList.add(student);
			}
			logger.log(Level.INFO, "Select successful: " + sqlp);
			resultSet.close();
		}
		catch (SQLException e) {
			logger.log(Level.WARNING, "Select failed: " + e.getMessage() + "\n\tSQL: " + sqlp);
		}
		return studentList;
	}
	
	public static List<Course> selectAllCourse() {
		List<Course> courseList = new ArrayList<Course>();
		
		String sqlp = "SELECT * FROM Course";	
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlp);
			while(resultSet.next()) {
				Course student = Builder.of(Course::new)
						.with(Course::setId, resultSet.getInt(1))
						.with(Course::setName, resultSet.getString(2))
						.with(Course::setCredit, resultSet.getInt(3))
						.with(Course::setExamDate, Date.valueOf(resultSet.getString(4)).toLocalDate())
						.with(Course::setRequired, resultSet.getInt(5))
						.build();
			courseList.add(student);
			}
			resultSet.close();
			logger.log(Level.INFO, "Select successful: " + sqlp);
		}
		catch (SQLException e) {
			logger.log(Level.WARNING, "Select failed: " + e.getMessage() + "\n\tSQL: " + sqlp);
		}
		return courseList;
	}
	
	public static List<Has> selectAllHas() {
		List<Has> hasList = new ArrayList<Has>();
		
		String sqlp = "SELECT * FROM Has";	
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlp);
			while(resultSet.next()) {
				Has has = Builder.of(Has::new)
						.with(Has::setNeptun, resultSet.getString(1))
						.with(Has::setId, resultSet.getInt(2))
						.build();
			hasList.add(has);
			}
			resultSet.close();
			logger.log(Level.INFO, "Select successful: " + sqlp);
		}
		catch (SQLException e) {
			logger.log(Level.WARNING, "Select failed: " + e.getMessage() + "\n\tSQL: " + sqlp);
		}
		return hasList;
	}
	
	public static int selectCount(String tableName, String fieldName, String fieldValue) {
		String sqlp = "SELECT COUNT(*) FROM " + tableName + " WHERE " + fieldName + " = '" + fieldValue + "'";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlp);
			logger.log(Level.INFO, "Select successful: " + sqlp);
			return resultSet.getInt(1);
		}catch (SQLException e) {
			logger.log(Level.WARNING, "Select failed: " + e.getMessage() + "\n\tSQL: " + sqlp);
		}
		return 0;
	}
	
	public static List<String> genericSelect(String[] fields, String table, String conditions) {
		List<String> resultList = new ArrayList<String>();
		String sqlp = "SELECT ";
		for(String field : fields) {
			sqlp += field + ",";
		}
		sqlp = sqlp.substring(0, sqlp.length() - 1);
		sqlp += " FROM " + table + " WHERE " + conditions + ";";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlp);
			while(resultSet.next()) {
				String result = "";
				for(int i = 1; i < fields.length+1; i++) {
					result += resultSet.getString(i) + "\t";
				}
				resultList.add(result);
				logger.log(Level.INFO, "Select successful: " + sqlp);
			}
		}catch(SQLException e) {
			logger.log(Level.WARNING, "Select failed: " + e.getMessage() + "\n\tSQL: " + sqlp);
		}
		return resultList;
	}
	
	public static void insertStudentListIntoDb(String sqlp, List<Student> studentList) {
		try {
			PreparedStatement ps = conn.prepareStatement(sqlp);
			for(Student student : studentList) {
				ps.setString(1, student.getNeptun());
				ps.setString(2, student.getName());
				ps.setInt(3, student.getCreditSum());
				ps.setString(4, student.getDateOfBirth().toString());
				ps.setString(5, student.getEmail());
				ps.execute();
			}
			logger.log(Level.INFO, "Insert successful: " + sqlp);
			System.out.println("Insert successful!");
		}catch(SQLException e) {
			logger.log(Level.WARNING, "Insert failed: " + e.getMessage() + "\n\tSQL: " + sqlp);
		}
	}
	

	public static void insertCourseListIntoDb(String sqlp, List<Course> courseList) {
		try {
			PreparedStatement ps = conn.prepareStatement(sqlp);
			for(Course course : courseList) {
				ps.setInt(1, course.getId());
				ps.setString(2, course.getName());
				ps.setInt(3, course.getCredit());
				ps.setString(4, course.getExamDate().toString());
				ps.setInt(5, course.getRequired());
				ps.execute();
			}
			logger.log(Level.INFO, "Insert successful: " + sqlp);
			System.out.println("Insert successful!");
		}catch(SQLException e) {
			logger.log(Level.WARNING, "Insert failed: " + e.getMessage() + "\n\tSQL: " + sqlp);
		}
	}
	
	
	public static void insertHasListIntoDb(String sqlp, List<Has> hasList) {
		try {
			PreparedStatement ps = conn.prepareStatement(sqlp);
			for(Has has : hasList) {
				ps.setString(1, has.getNeptun());
				ps.setInt(2, has.getId());
				ps.execute();
			}
			logger.log(Level.INFO, "Insert successful: " + sqlp);
			System.out.println("Insert successful!");
		}catch(SQLException e) {
			logger.log(Level.WARNING, "Insert failed: " + e.getMessage() + "\n\tSQL: " + sqlp);
		}
	}
}

