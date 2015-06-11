package quiz;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
	private int userID;
	private String username;
	private String hashedPassword;
	// private boolean isAdministrator; // administrator status
	// private boolean isDeleted; // whether user has been deleted
	
	/**
	 * Creates a new User and adds it to the User table.
	 * @param username new User's username
	 * @param password new User's unhashed password
	 * @param isAdministrator new User's administrator status
	 */
	public User(String username, String password, boolean isAdministrator) {
		this.username = username;
		this.hashedPassword = getHashedPassword(password);
		// this.isAdministrator = isAdministrator;
		
		try {
			Statement stmt = DBConnection.getStatement();
			stmt.executeUpdate("INSERT INTO users (username, hashedPassword, administratorStatus, deletedStatus) VALUES (\"" + username + "\",\"" +
											 	   hashedPassword + "\"," + isAdministrator + "," + false + ")");
			ResultSet rs = stmt.executeQuery("SELECT userID FROM users WHERE username LIKE \"" + username + "\"");
			if (rs.next()) {
				this.userID = rs.getInt("userID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Achievements.initUserAchievements(userID);
	}
	
	/**
	 * Creates a User but doesn't add it to the user table
	 * @param userID User's userID
	 * @param username User's username
	 * @param hashedPassword User's hashed password
	 */
	private User(int userID, String username, String hashedPassword) {
		this.userID = userID;
		this.username = username;
		this.hashedPassword = hashedPassword;
		// this.isAdministrator = isAdministrator;
		// this.isDeleted = false;
	}

	
	/**
	 * Returns the User with the given userID if they exist in
	 * the User table or null if they don't exist in the User
	 * table
	 * @param userID userID of the User to be returned
	 */
	public static User getUser(int userID) {
		try {
			Statement stmt = DBConnection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE userID LIKE " + userID);
			
			if (rs.next()) {
				String usernameResult =
					rs.getString("username");
				String hashedPasswordResult =
					rs.getString("hashedPassword");
				// boolean isAdministratorResult =
				//	rs.getBoolean("administratorStatus");
				return new User(userID, usernameResult, hashedPasswordResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Returns the User with the given username if they exist in
	 * the User table or null if they do not exist in the User
	 * table
	 * @param username username of the User to be returned
	 */
	public static User getUser(String username) {
		try {
			Statement stmt = DBConnection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username LIKE \"" + username + "\"");
			
			if (rs.next()) {
				int userIDResult = rs.getInt("userID");
				String hashedPasswordResult = rs.getString("hashedPassword");
				// boolean isAdministratorResult = rs.getBoolean("administratorStatus");
				return new User(userIDResult, username, hashedPasswordResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Returns the User with the given username and password if
	 * they exist in the User table or null if they do not exist
	 * in the User table
	 * @param username username of the User to be returned
	 * @param password password of the User to be returned
	 */
	public static User getUser(String username, String password) {
		String hashedPassword = getHashedPassword(password);
		
		try {
			Statement stmt = DBConnection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE (username LIKE \"" + username +
											 "\" AND hashedPassword LIKE \"" + hashedPassword + "\")");
			
			if (rs.next()) {
				int userIDResult = rs.getInt("userID");
				// boolean isAdministratorResult = rs.getBoolean("administratorStatus");
				return new User(userIDResult, username, hashedPassword);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * @return an ArrayList of every non-deleted User in the database
	 */
	public static ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			Statement stmt = DBConnection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE deletedStatus = FALSE");
			
			while (rs.next()) {
				int userIDResult = rs.getInt("userID");
				String usernameResult = rs.getString("username");
				String hashedPasswordResult = rs.getString("hashedPassword");
				// boolean isAdministratorResult = rs.getBoolean("administratorStatus");
				User user = new User(userIDResult, usernameResult, hashedPasswordResult);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	/**
	 * @return an ArrayList of the usernames of every non-deleted User in the database
	 */
	public static ArrayList<String> getUsernames() {
		ArrayList<String> usernames = new ArrayList<String>();
		
		try {
			Statement stmt = DBConnection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT username FROM users WHERE deletedStatus = FALSE");
			
			while (rs.next()) {
				String curUsername = rs.getString("username");
				usernames.add(curUsername);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usernames;
	}
	
	/**
	 * Upgrades the User to be an administrator
	 */
	public void makeAdministrator() {
		// isAdministrator = true;
		
		try {
			Statement stmt = DBConnection.getStatement();
			stmt.executeUpdate("UPDATE users SET administratorStatus = true WHERE userID = " + userID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Upgrades the User with the given userID to be an administrator.
	 * Assumes a User with the given userID exists
	 */
	public static void makeAdministrator(int userID) {
		User user = getUser(userID);
		user.makeAdministrator();
	}
	
	/**
	 * Deletes the User from the database
	 */
	public void deleteUser() {
		try {
			Statement stmt = DBConnection.getStatement();
			stmt.executeUpdate("UPDATE users SET deletedStatus = " + true + " WHERE userID = " + userID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes the User with the given userID from the database.
	 * Assumes a User with the given userID exists
	 */
	public static void deleteUser(int userID) {
		User user = getUser(userID);
		user.deleteUser();
	}
	
	/**
	 * @return number of Users in the database
	 */
	public static int getNumUsers() {
		int numUsers = 0;
		
		try {
			Statement stmt = DBConnection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT userID FROM users WHERE deletedStatus = FALSE");
			
			while (rs.next()) {
				numUsers++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numUsers;
	}
	
	/**
	 * @return User's userID
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * @return User's username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @return User's administrator status
	 */
	public boolean isAdministrator() {
		Statement statement = DBConnection.getStatement();
		String sql;
		ResultSet rs;
		
		try {
			sql = "SELECT administratorStatus FROM users WHERE userID = "
				+ userID;
			rs = statement.executeQuery(sql);
			rs.first();
			return rs.getBoolean("administratorStatus");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Returns whether the given password matches the User's password
	 * @param password password being checked
	 */
	public boolean hasMatchingPassword(String password) {
		return (getHashedPassword(password).equals(hashedPassword));
	}
	
	public boolean isDeleted() {
		Statement statement = DBConnection.getStatement();
		String sql;
		ResultSet rs;
		
		try {
			sql = "SELECT deletedStatus FROM users WHERE userID = "
				+ userID;
			rs = statement.executeQuery(sql);
			rs.first();
			return rs.getBoolean("deletedStatus");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean isDeleted(int userID) {
		User user = User.getUser(userID);
		return user.isDeleted();
	}
	
	/**
	 * Adds 2 Friendships (1 with this user in the 1st position
	 * and 1 with the provided user in the 1st position) to the
	 * Friendship table
	 * @param userID userID of this User's new friend
	 * @return 0 if the friendships were successfully added to
	 * the table, -1 otherwise
	 */
	public int addFriendship(int userID) {
		try {
			Statement stmt = DBConnection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM friendships WHERE (userID1 LIKE " + this.userID +
											 " AND userID2 LIKE " + userID + ")");
			if (!rs.next()) {
				stmt.executeUpdate("INSERT INTO friendships VALUES(" + this.userID + "," + userID +")");
				stmt.executeUpdate("INSERT INTO friendships VALUES(" + userID + "," + this.userID +")");
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Removes 2 existing Friendships (1 with this user in the
	 * 1st position and 1 with the provided user in the 1st
	 * position) from the Friendship table
	 * @param userID userID of this User's previous friend
	 * @return 0 if the friendships were successfully removed
	 * from the table, -1 otherwise
	 */
	public int removeFriendship(int userID) {
		try {
			Statement stmt = DBConnection.getStatement();
			stmt.executeUpdate("DELETE FROM friendships WHERE userID1 LIKE " + this.userID +
							   " AND userID2 LIKE " + userID);
			stmt.executeUpdate("DELETE FROM friendships WHERE userID1 LIKE " + userID +
					   		   " AND userID2 LIKE " + this.userID);
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Returns whether this User is friends with the User with
	 * the given userID
	 * @param userID userID of this User's potential friend
	 */
	public boolean isFriendsWith(int userID) {
		try {
			Statement stmt = DBConnection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM friendships WHERE (userID1 LIKE " + this.userID +
											 " AND userID2 LIKE " + userID + ")");
			if (rs.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Returns the userIDs of this User's friends
	 * @return ArrayList of the userIDs of this User's friends
	 */
	public ArrayList<Integer> getFriendships() {
		ArrayList<Integer> friends = new ArrayList<Integer>();
		
		try {
			Statement stmt = DBConnection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM friendships WHERE userID1 LIKE " + this.userID);
			while (rs.next()) {
				friends.add(rs.getInt("userID2"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friends;
	}
	
	public static void buildUserTable() {
		String sql;
		Statement statement = DBConnection.getStatement();
		
		try {
			// Comment out these two lines to have a persistent user table
			sql = "DROP TABLE IF EXISTS users";
			statement.executeUpdate(sql);
			
			sql = "SHOW TABLES LIKE 'users'";
			ResultSet rs = statement.executeQuery(sql);
			boolean tableExists = false;
			if (rs.next()) {
				tableExists = true;
			}
			
			if (!tableExists) {
				sql = "CREATE TABLE users"
					+ "(userID INTEGER AUTO_INCREMENT, "
					+ "username CHAR(64), "
					+ "hashedPassword CHAR(64), "
					+ "administratorStatus BOOL, "
					+ "deletedStatus BOOL, "
					+ "PRIMARY KEY (userID))";
				statement.executeUpdate(sql);
				
				sql = "INSERT INTO users "
					+ "(username, hashedPassword, administratorStatus, deletedStatus) "
					+ "VALUES(\"Abby\", "
					+ "\"5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8\", "
					+ true + ", "
					+ false
					+ ")";
				statement.executeUpdate(sql);
				
				sql = "INSERT INTO users "
					+ "(username, hashedPassword, administratorStatus, deletedStatus) "
					+ "VALUES(\"Billy\", "
					+ "\"5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8\", "
					+ false + ", "
					+ false
					+ ")";
				statement.executeUpdate(sql);
				
				sql = "INSERT INTO users "
					+ "(username, hashedPassword, administratorStatus, deletedStatus) "
					+ "VALUES(\"Charlie\", "
					+ "\"5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8\", "
					+ false + ", "
					+ false
					+ ")";
				statement.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void buildFriendshipTable() {
		String sql;
		Statement statement = DBConnection.getStatement();
		
		try {
			// Comment out these two lines to have a persistent friendship table
			sql = "DROP TABLE IF EXISTS friendships";
			statement.executeUpdate(sql);
			
			sql = "SHOW TABLES LIKE 'friendships'";
			ResultSet rs = statement.executeQuery(sql);
			boolean tableExists = false;
			if (rs.next()) {
				tableExists = true;
			}
			
			if (!tableExists) {
				sql = "CREATE TABLE friendships"
					+ "(userID1 INT, "
					+ "userID2 INT)";
				statement.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static String getHashedPassword(String password) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA");
			byte[]  passwordBytes = password.getBytes();
			byte[] hashBytes = messageDigest.digest(passwordBytes);
			return hexToString(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.err.printf("Error creating user.");
			System.exit(1);
		}
		
		return null;
	}
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	private static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
}