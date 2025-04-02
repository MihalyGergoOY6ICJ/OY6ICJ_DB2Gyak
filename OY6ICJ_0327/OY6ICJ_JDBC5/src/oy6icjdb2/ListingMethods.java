package oy6icjdb2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ListingMethods {
	public static void task_a(String table_name) {
		String sqlp = "SELECT COUNT(*) FROM " + table_name;
		Connection conn = DbMethods.Connect();
		ResultSet result_set = execute(conn, sqlp);
		if(result_set != null) {
			try {
				int count = result_set.getInt("COUNT(*)");
				System.out.println(table_name + " tábla rekordjainak száma: " + count);
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		DbMethods.DisConnect(conn);
	}
	
	public static void task_b(String table_name, String column) {
		String sqlp = "SELECT SUM(" + column + "), AVG(" + column + ") FROM " + table_name;
		Connection conn = DbMethods.Connect();
		ResultSet result_set = execute(conn, sqlp);
		if(result_set != null) {
			try {
				int sum = result_set.getInt("SUM(" + column + ")");
				int avg = result_set.getInt("AVG(" + column + ")");
				System.out.println(table_name + " tábla " + column + " oszlopának összege: " + sum + ", átlaga: " + avg);
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		DbMethods.DisConnect(conn);
	}
	
	public static void task_c(String table_name, String column, boolean ascending) {
		String sqlp = "SELECT * FROM " + table_name + " ORDER BY " + column + " ";
		sqlp += (ascending) ? "ASC" : "DESC";
		Connection conn = DbMethods.Connect();
		ResultSet result_set = execute(conn, sqlp);
		if(result_set != null) {
			try {
				ResultSetMetaData result_set_meta_data = result_set.getMetaData(); 
				int column_count = result_set_meta_data.getColumnCount();
				while(result_set.next()) {
					String row = "";
					for(int i = 1; i < column_count + 1; i++) {
						row += result_set.getString(i) + "\t";
					}
					System.out.println(row);
				}
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		DbMethods.DisConnect(conn);
	}
	
	public static void task_d() {
		String sqlp = "SELECT Vnev, Knev, COUNT(*) FROM "
				+ "Hallgato INNER JOIN Dolgozik ON Hallgato.Id = Dolgozik.Id "
				+ "INNER JOIN Projekt ON Dolgozik.Pkod = Projekt.Pkod GROUP BY Vnev, Knev";
		Connection conn = DbMethods.Connect();
		ResultSet result_set = execute(conn, sqlp);
		if(result_set != null) {
			try {
				while(result_set.next()) {
					String vnev = result_set.getString(1);
					String knev = result_set.getString(2);
					int count = result_set.getInt(3);
					System.out.println("Név: " + vnev + " " + knev + "\t Projektek száma: " + count);
				}
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		else {
			System.out.println("asd");
		}
		DbMethods.DisConnect(conn);
	}
	
	public static void listTable(String table_name) {
		System.out.println("\n" + table_name);
		String sqlp = "SELECT * FROM " + table_name;
		Connection conn = DbMethods.Connect();
		ResultSet result_set = execute(conn, sqlp);
		if(result_set != null) {
			try {
				ResultSetMetaData result_set_meta_data = result_set.getMetaData();
				int column_count = result_set_meta_data.getColumnCount();
				String str = "";
				for(int i = 0; i < column_count; i++) {
					str += result_set_meta_data.getColumnName(i + 1) + "(" + result_set_meta_data.getColumnTypeName(i + 1) + ")" +  "\t";
				}
				System.out.println(str);
				while(result_set.next()) {
					str = "";
					for(int i = 0; i < column_count; i++) {
						str += result_set.getString(i + 1) + "\t";
					}
					System.out.println(str);
				}
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		
		DbMethods.DisConnect(conn);
	}
	
	public static void uniList(String tableName, String condition, String field1, String field2) {
		Connection conn = DbMethods.Connect();
		String sqlp = "SELECT " + field1 + ", " + field2 + " FROM " + tableName + " WHERE " + condition;
		ResultSet resultSet = execute(conn, sqlp);
		try {
			while(resultSet.next()) {
				String line = resultSet.getString(1) + "\t" + resultSet.getString(2);
				System.out.println(line);
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		DbMethods.DisConnect(conn);
	}
	
	public static ResultSet execute(Connection conn, String sqlp) {
		ResultSet resoult_set = null;
		try {
			Statement statement = conn.createStatement();
			resoult_set = statement.executeQuery(sqlp);
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return resoult_set;
	}
}
