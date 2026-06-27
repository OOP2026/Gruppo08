package databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
	private final String URL = "jdbc:postgresql://localhost:5432/exam";
	private final String UNAME = "exam";
	private final String PASS = "exam";
	private Connection con;

	private DbConnection() {
	}

	private static DbConnection instance;

	public static DbConnection getInstance() {
		if (instance == null)
			instance = new DbConnection();
		return instance;
	}

	public Connection getCon() {
		try {
			if (con == null || con.isClosed())
				con = DriverManager.getConnection(URL, UNAME, PASS);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return con;
	}

	public void closeConnection() {
		try {
			if (con != null && !con.isClosed())
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
