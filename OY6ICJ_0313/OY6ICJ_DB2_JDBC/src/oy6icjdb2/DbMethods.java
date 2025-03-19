package oy6icjdb2;

import java.sql.*;

public class DbMethods {
	final static String url = "jdbc:sqlite:C:/sqlite3/autodb";
	
	public static void Register() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found exception: " + e.getMessage());
		}
	}
	
	public static Connection Connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	public static void DisConnect(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static void CommandExec(String command) {
		Connection conn = Connect();
		try {
			Statement s = conn.createStatement();
			s.execute(command);
		}
		catch (SQLException e) {
			System.out.println("Command: " + command);
			System.out.println(e.getMessage());
		}
		DisConnect(conn);
	}
	
	public static void ReadAllData() {
		int id = 0;
		String vnev = "";
		String knev = "";
		String szuli = "";
		String lakcim = "";
		
		String sqlp = "SELECT Id, Vnev, Knev, SzülI, Lakcim FROM Hallgato";
		Connection conn = Connect();		
		try {
			Statement statement = conn.createStatement();
			ResultSet resoult_set = statement.executeQuery(sqlp);
			while(resoult_set.next()) {
				id = resoult_set.getInt("ID");
				vnev = resoult_set.getString("Vnev");
				knev = resoult_set.getString("Knev");
				szuli = resoult_set.getString("SzülI");
				lakcim = resoult_set.getString("Lakcim");
				System.out.println(
						id + "\t" + 
						vnev + "\t" +
						knev + "\t" +
						szuli + "\t" +
						lakcim + "\t"
						);
			}
			resoult_set.close();
		}
		catch (SQLException e) {
				System.out.println(e.getMessage());
		}
		DisConnect(conn);
	}
	
	public static void Insert(String rendszam, String tipus, String szin, String kor, String ar, String tulaj) {
		Connection conn = Connect();
		String sqlp = "INSERT INTO Auto VALUES('" + rendszam + " ', '" + tipus + "', + '" + szin + "', " + kor + "," + ar + ", '" + tulaj + "'" + ")";
		
		try {
			Statement s = conn.createStatement();
			s.execute(sqlp);
			System.out.println("OK!");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect(conn);
	}
	
	public static void DeleteData(String rendszam) {
		Connection conn = Connect();
		String sqlp = "DELETE FROM Auto WHERE Rendszam = '" + rendszam + "'";
		try {
			Statement s = conn.createStatement();
			int db = s.executeUpdate(sqlp);
			if(db == 0) {
				System.out.println("A megadott rendszámú autó nem létezik!");
			}
			else {
				System.out.println("Törlődött " + db + " számú adat");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		DbMethods.CommandExec(sqlp);
		DisConnect(conn);
	}
	
	
	public static void UpdateData(String rendszam, String ar) {
		Connection conn = Connect();
		String sqlp = "UPDATE Auto SET ar = " + ar + " WHERE Rendszam = '" + rendszam + "'";
		try {
			Statement s = conn.createStatement();
			int db = s.executeUpdate(sqlp);
			if(db == 0) {
				System.out.println("A megadott rendszámú autó nem létezik!");
			}
			else {
				System.out.println("Módosult " + db + " számú adat");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		DbMethods.CommandExec(sqlp);
		DisConnect(conn);
	
	
	}
}
