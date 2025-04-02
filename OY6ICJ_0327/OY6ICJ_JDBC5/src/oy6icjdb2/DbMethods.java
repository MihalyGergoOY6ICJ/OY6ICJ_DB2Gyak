package oy6icjdb2;

import java.io.IOException;
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
	
	public static int selectCount(String table_name, String condition) {
		int pc = -1;
		String sqlp = "SELECT COUNT(*) FROM " + table_name + " WHERE " + condition;
		Connection conn = Connect();
		ResultSet result_set = ListingMethods.execute(conn, sqlp);
		try {
			if(result_set != null) {
				while(result_set.next()) {
					pc = result_set.getInt(1);
				}
			}
			result_set.close();
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect(conn);
		return pc;
	}
	
	
	public static void checked_update() throws IOException{
		System.out.println("Hallgato módosítása a Dolgozik táblában");
		
		String pkod = Utils.readData("Kérem a Pkód értékét");
		
		int db = selectCount("Dolgozik", "Pkod = " + pkod);
		if(db == 0) {
			System.out.println("Hibás Pkód!");
			return;
		}
		String id = Utils.readData("Kérem a Hallgató ID értékét!");
		db = selectCount("Hallgato", "ID = " + id);
		if(db == 0) {
			System.out.println("Hibás ID!");
			return;
		}
		String sqlp = "UPDATE Dolgozik SET ID = " + id + " WHERE Pkod = " + pkod;
		CommandExec(sqlp);
	}
	
	public static void hallgato_insert(String id, String vnev, String knev, String szuli, String lakcim) {
		Connection conn = Connect();
		String sqlp = "INSERT INTO Hallgato VALUES(" + id + ", '" + vnev + "', + '" + knev + "', '" + szuli + "', '" + lakcim + "')";
		//System.out.println(sqlp);
		
		try {
			Statement s = conn.createStatement();
			s.execute(sqlp);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect(conn);
	}
	
	public static void hallgato_delete(String id) {
		if(selectCount("Dolgozik", "id=" + id) != 0) {
			System.out.println("A Dolgozik táblában van hivatkozás erre az id-re, nem törölhető");
			return;
		}
		Connection conn = Connect();
		String sqlp = "DELETE FROM Hallgato WHERE ID=" + id;
		//System.out.println(sqlp);
		try {
			conn.setAutoCommit(true);
			Statement s = conn.createStatement();
			int db = s.executeUpdate(sqlp);
			if(db == 0) {
				System.out.println("A megadott id-hez nem tartozik rekord!");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect(conn);
	}
	
	public static void hallgato_update(String id, String vnev) {
		Connection conn = Connect();
		String sqlp = "UPDATE Hallgato SET Vnev = '" + vnev + "' WHERE ID = " + id;
		try {
			Statement s = conn.createStatement();
			int db = s.executeUpdate(sqlp);
			if(db == 0) {
				System.out.println("A megadott id-hez nem tartozik rekord");
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
	
	public static void insert_with_ps(Auto[] autos) {
		Connection conn = Connect();
		int db = 0;
		String sqlp = "INSERT INTO Auto VALUES(?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sqlp);
			for(Auto auto : autos) {
				ps.setString(1, auto.getRendszam());
				ps.setString(2, auto.getTipus());
				ps.setString(3, auto.getSzin());
				ps.setInt(4, auto.getKor());
				ps.setInt(5, auto.getAr());
				ps.setInt(6, auto.getTulaj());
				ps.execute();
				db++;
			}
			System.out.println(db + "db beszúrva");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect(conn);
	}
	
	
	public static void insert_with_ps(Hallgato[] hallgatos) {
		Connection conn = Connect();
		int db = 0;
		String sqlp = "INSERT INTO Hallgato VALUES(?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sqlp);
			for(Hallgato hallgato : hallgatos) {
				ps.setInt(1, hallgato.getId());
				ps.setString(2, hallgato.getVnev());
				ps.setString(3, hallgato.getKnev());
				ps.setString(4, hallgato.getSzuli());
				ps.setString(5, hallgato.getLakcim());
				ps.execute();
				db++;
			}
			System.out.println(db + "db beszúrva");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect(conn);
	}
	
	public static void deleteWithPs(int[][] rekords) {
		Connection conn = Connect();
		String sqlp = "DELETE FROM Dolgozik WHERE (ID = ?) AND (Pkod = ?)";
		int db = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sqlp);
			for(int[] rekord : rekords) {
				ps.setInt(1, rekord[0]);
				ps.setInt(2, rekord[1]);
				if(ps.execute()) {
					db++;
				}
			}
			System.out.println(db + "db törölve");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect(conn);
	}

	public static void listHallgatoAndProjekt(int[] ids) {
		Connection conn = Connect();
		String sqlp = "SELECT Hallgato.Vnev, Hallgato.Knev, Projekt.Nev FROM "
				+ "Hallgato INNER JOIN Dolgozik ON Hallgato.id = Dolgozik.id "
				+ "INNER JOIN Projekt ON Projekt.Pkod = Dolgozik.Pkod "
				+ "WHERE Hallgato.id = ?";
		int db = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sqlp);
			for(int id : ids) {
				ps.setInt(1, id);
				ResultSet resultSet = ps.executeQuery();
				while(resultSet.next()) {
					String line = 
							resultSet.getString(1) + "\t" + 
							resultSet.getString(2) + "\t" + 
							resultSet.getString(3);
					System.out.println(line);
				}
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect(conn);
	}
	
	
	
	
	
	public static void ar_lista(String min, String max) {
		String rendszam = "";
		String tipus = "";
		String szin = "";
		int kor = 0;
		int ar = 0;
		int tulaj = 0;
		String sqlp = "SELECT * FROM Auto WHERE ar >= " + min + " AND ar <= " + max + " ORDER BY ar";
		Connection conn = Connect();
		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sqlp);
			System.out.println("Autók " + min + " és " + max + " ár között");
			while(rs.next()) {
				rendszam = rs.getString(1);
				tipus = rs.getString(2);
				szin = rs.getString(3);
				kor = rs.getInt(4);
				ar = rs.getInt(5);
				tulaj = rs.getInt(6);
				System.out.println(rendszam + "\t" + tipus + "\t" + szin + "\t" + kor + "\t" + ar + "\t" + tulaj);
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect(conn);
	}
	
	
	public static void uni_list(String table_name, String cond, String field1, String field2) {
		Connection conn = Connect();
		String sqlp = "SELECT " + field1 + " " + field2 + " FROM " + table_name + " WHERE " + cond;
		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sqlp);
			while(rs.next()) {
				String a = rs.getString(1);
				String b = rs.getString(2);
				System.out.println(a + "\t" + b);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect(conn);
	}
}
