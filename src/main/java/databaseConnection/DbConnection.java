package databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	private static final String URL = "jdbc:postgresql://localhost:5432/exam";
	private static final String UNAME = "exam";
	private static final String PASS = "exam";

	private DbConnection() {
	}

	public static Connection getCon() throws SQLException {
		return DriverManager.getConnection(URL, UNAME, PASS);
	}
}
