package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Achievement {
	private int achievementID;
	private String name;
	private String description;
	private int progressAction; // specifies what action results
								// in progress towards completing
								// the achievement
	private int maxProgress; // progress needed to complete the
							 // the Achievement
	private String icon; // URL for the Achievement's icon
	
	/**
	 * Creates an Achievement
	 * @param achievementID Achievement's achievementID
	 * @param name Achievement's name
	 * @param description Achievement's description
	 * @param progressAction action needed for Achievement progress
	 * @param maxProgress progress needed to complete the Achievement
	 * @param icon URL for the Achievement's icon
	 */
	public Achievement(int achievementID, String name, String description, int progressAction, int maxProgress, String icon) {
		this.achievementID = achievementID;
		this.name = name;
		this.description = description;
		this.progressAction = progressAction;
		this.maxProgress = maxProgress;
		this.icon = icon;
	}
	
	/**
	 * @return Achievement's achievementID
	 */
	public int getAchievementID() {
		return achievementID;
	}
	
	/**
	 * @return Achievement's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return Achievement's description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @return action needed for Achievement progress
	 */
	public int getProgressAction() {
		return progressAction;
	}
	
	/**
	 * @return progress needed to complete the Achievement
	 */
	public int getMaxProgress() {
		return maxProgress;
	}
	
	/**
	 * @return URL of the achievement's icon
	 */
	public String getIcon() {
		return icon;
	}
	
	/**
	 * Returns the User with the given userID's progress towards
	 * completing the Achievement
	 * @param userID User's userID
	 * @return the given User's Achievement progress if a User
	 * with the given userID exists or -1 if such a User
	 * doesn't exist
	 */
	public int getProgress(int userID) {
		try {
			Statement stmt = DBConnection.getStatement();
			String lookupString = "" + getAchievementID();
			ResultSet rs = stmt.executeQuery("SELECT " + lookupString + " FROM achievements WHERE userID = " + userID);
			if (rs.next()) {
				return rs.getInt(lookupString);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
