package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	private static Connection connection;
	private static Statement statement;		// The static 'statement' will be used by the rest
											// of the program to pass queries to the database
	
	public static void openConnection() {
		connection = MyDB.getConnection();
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	public static void createStatement() {		
		try {
			statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Statement getStatement() {
		return statement;
	}
	
	public static void close() {
		MyDB.close();
	}
	
}