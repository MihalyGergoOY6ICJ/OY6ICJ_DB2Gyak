package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import log.LogUtils;

public class DbConnection{
	final private static String url = "jdbc:sqlite:C:/sqlite3/taskdb";
	
	final private static Logger logger = LogUtils.getLogger();
	
	private static Connection conn = null;
	
	public static void init() throws ClassNotFoundException, SQLException{
		if(conn == null) {
			register();
			connect();
		}
	}
	
	private static void register() throws ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
		logger.log(Level.INFO, "JDBC driver registration successful!");
	}
	
	private static void connect() throws SQLException{
		conn = DriverManager.getConnection(url);
		logger.log(Level.INFO, "Successful connection to db!");
		
	}

	private void disconnect() {
		if(conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		disconnect();
		super.finalize();
	}
	
	public static Connection getConn() {
		return conn;
	}
}
