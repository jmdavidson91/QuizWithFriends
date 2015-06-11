package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

public class Achievements {
	public static final int NUM_ACHIEVEMENTS = 6;
	public static final int QUIZ_CREATION_ACTION = 0;
	public static final int QUIZ_TAKEN_ACTION = 1;
	public static final int QUIZ_TOP_SCORE_ACTION = 2;
	public static final int QUIZ_PRACTICE_MODE_ACTION = 3;
	
	private static Achievement[] achievements;
	
	static {
		achievements = new Achievement[NUM_ACHIEVEMENTS];
		
		int achievementID = 0;
		String name = "Amateur Author";
		String description = "The user created a quiz.";
		int progressAction = QUIZ_CREATION_ACTION;
		int maxProgress = 1;
		String icon = null; //TODO Implement
		Achievement achievement = new Achievement(achievementID, name, description,
												  progressAction, maxProgress, icon);
		achievements[0] = achievement;
		
		achievementID = 1;
		name = "Prolific Author";
		description = "The user created five quizzes.";
		maxProgress = 5;
		icon = null; //TODO Implement
		achievement = new Achievement(achievementID, name, description,
									  progressAction, maxProgress, icon);
		achievements[1] = achievement;
		
		achievementID = 2;
		name = "Prodigious Author";
		description = "The user created ten quizzes.";
		maxProgress = 10;
		icon = null; //TODO Implement
		achievement = new Achievement(achievementID, name, description,
									  progressAction, maxProgress, icon);
		achievements[2] = achievement;
		
		achievementID = 3;
		name = "Quiz Machiner";
		description = "The user took ten quizzes.";
		progressAction = QUIZ_TAKEN_ACTION;
		maxProgress = 10;
		icon = null; //TODO Implement
		achievement = new Achievement(achievementID, name, description,
									  progressAction, maxProgress, icon);
		achievements[3] = achievement;
		
		achievementID = 4;
		name = "I am the Greatest";
		description = "The user had the highest score on a quiz.";
		progressAction = QUIZ_TOP_SCORE_ACTION;
		maxProgress = 1;
		icon = null; //TODO Implement
		achievement = new Achievement(achievementID, name, description,
									  progressAction, maxProgress, icon);
		achievements[4] = achievement;
		
		achievementID = 5;
		name = "Practice Makes Perfect";
		description = "The user took a quiz in practice mode.";
		progressAction = QUIZ_PRACTICE_MODE_ACTION;
		maxProgress = 1;
		icon = null; //TODO Implement
		achievement = new Achievement(achievementID, name, description,
									  progressAction, maxProgress, icon);
		achievements[5] = achievement;
	}
	
	/**
	 * @return An array of all Achievements ordered by
	 * achievementID
	 */
	public static Achievement[] getAchievements() {
		return achievements;
	}
	
	/**
	 * Updates the User with the given userID's achievement
	 * progress based on the given action
	 * @param userID User's userID
	 * @param action Achievement progress action
	 * @return 0 if the user with the given userID's
	 * achievements were updated correctly, -1 otherwise
	 */
	public static int updateProgress(int userID, int action) {
		try {
			Statement stmt = DBConnection.getStatement();
			for (int i = 0; i < NUM_ACHIEVEMENTS; i++) {
				Achievement achievement = achievements[i];
				if (action == achievement.getProgressAction()) {
					ResultSet rs = stmt.executeQuery("SELECT * FROM achievements WHERE userID = " + userID);
					if (rs.next()) {
						int newProgress = rs.getInt("progress" + i) + 1;
						if (newProgress <= achievement.getMaxProgress()) {
							String sql = "UPDATE achievements SET progress" + i +
							   " = " + newProgress + " WHERE userID = " + userID;
							stmt.executeUpdate(sql);
							if (newProgress == achievement.getMaxProgress()) {
								stmt.executeUpdate("UPDATE achievements SET completionTime" + i + " = " +
												   null + " WHERE userID = " + userID);
							}
						}
					}
				}
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static ArrayList<CompletedAchievement> getCompletedAchievements(int userID) {
		ArrayList<CompletedAchievement> completed =
			new ArrayList<CompletedAchievement>();
		
		try {
			Statement stmt = DBConnection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM achievements WHERE userID = " + userID);
			rs.next();
			
			int[] progress = new int[NUM_ACHIEVEMENTS];
			Timestamp[] timestamps = new Timestamp[NUM_ACHIEVEMENTS];
			for (int i = 0; i < NUM_ACHIEVEMENTS; i++) {
				progress[i] = rs.getInt("progress" + i);
				timestamps[i] = rs.getTimestamp("completionTime" + i);
			}
			
			for (int j = 0; j < NUM_ACHIEVEMENTS; j++) {
				Achievement achievement = achievements[j];
				int maxProgress = achievement.getMaxProgress();
				if (progress[j] == maxProgress) {
					CompletedAchievement compAchieve =
						new CompletedAchievement(achievement, userID, timestamps[j]);
					completed.add(compAchieve);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Collections.sort(completed, Collections.reverseOrder());
		
		return completed;
	}
	
	public static ArrayList<Achievement> getUncompletedAchievements(int userID) {
		ArrayList<Achievement> uncompleted = new ArrayList<Achievement>();
		
		try {
			Statement stmt = DBConnection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM achievements WHERE userID = " + userID);
			rs.next();
			
			int[] progress = new int[NUM_ACHIEVEMENTS];
			for (int i = 0; i < NUM_ACHIEVEMENTS; i++) {
				progress[i] = rs.getInt("progress" + i);
			}
			
			for (int j = 0; j < NUM_ACHIEVEMENTS; j++) {
				Achievement achievement = achievements[j];
				int maxProgress = achievement.getMaxProgress();
				if (progress[j] != maxProgress) {
					uncompleted.add(achievement);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return uncompleted;
	}
	
	/**
	 * Adds a User to the acheivement table
	 * @param userID User's userID
	 * @return 0 if the User with the given userID was
	 * successfully added to the table, -1 otherwise
	 */
	public static int initUserAchievements(int userID) {
		try {
			Statement stmt = DBConnection.getStatement();
			String valuesString = "" + userID;
			for (int i = 0; i < NUM_ACHIEVEMENTS; i++) {
				valuesString += ", " + 0 + ", " + null;
			}
			String sql = "INSERT INTO achievements VALUES(" + valuesString + ")";
			stmt.executeUpdate(sql);
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void buildAchievementTable() {
		String sql;
		Statement statement = DBConnection.getStatement();
		
		try {
			// Comment out these two lines to have a persistent achievement table
			sql = "DROP TABLE IF EXISTS achievements";
			statement.executeUpdate(sql);
			
			sql = "SHOW TABLES LIKE 'achievements'";
			ResultSet rs = statement.executeQuery(sql);
			boolean tableExists = false;
			if (rs.next()) {
				tableExists = true;
			}
			
			if (!tableExists) {
				String columnsString = "(userID INT, ";
				for (int i = 0; i < NUM_ACHIEVEMENTS; i++) {
					columnsString += "progress" + i + " INT, completionTime" + i + " TIMESTAMP, ";
				}
				columnsString += "PRIMARY KEY (userID))";
				
				sql = "CREATE TABLE achievements"
					+ columnsString;
				statement.executeUpdate(sql);
				
				sql = "SELECT userID FROM users";
				rs = statement.executeQuery(sql);
				ArrayList<Integer> userIDs = new ArrayList<Integer>();
				while (rs.next()) {
					int userID = rs.getInt("userID");
					userIDs.add(userID);
				}
				for (Integer userID: userIDs) {
					initUserAchievements(userID);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}