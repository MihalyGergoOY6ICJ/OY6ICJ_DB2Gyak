package jdbc;

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
		String rendszam = "";
		String tipus = "";
		String szin = "";
		String tulaj = "";
		int kor = 0;
		int ar = 0;
		String sqlp = "SELECT Rendszam, Tipus, Szin, Kor, Ar, Tulaj FROM Auto";
		Connection conn = Connect();		
		try {
			Statement statement = conn.createStatement();
			ResultSet resoult_set = statement.executeQuery(sqlp);
			while(resoult_set.next()) {
				rendszam = resoult_set.getString("Rendszam");
				tipus = resoult_set.getString("Tipus");
				szin = resoult_set.getString("Szin");
				kor = resoult_set.getInt("Kor");
				ar = resoult_set.getInt("Ar");
				tulaj = resoult_set.getString("Tulaj");
				System.out.println(
						rendszam + "\t" + 
						tipus + "\t" +
						szin + "\t" +
						kor + "\t" +
						ar + "\t" +
						tulaj
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
