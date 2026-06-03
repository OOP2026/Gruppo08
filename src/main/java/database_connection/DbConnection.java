package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
	private DbConnection() {
		try {
			con = DriverManager.getConnection(url, uname, pass);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static DbConnection instance;

	public static DbConnection getInstance() {
		if (instance == null)
			instance = new DbConnection();
		return instance;
	}

	private String url = "jdbc:postgresql://localhost:5432/progetto";
	private String uname = "progetto";
	private String pass = "0000";
	private Connection con;

	public void closeConnection() {
		try {
			if (con != null && !con.isClosed())
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
