package quiz;

import java.sql.Timestamp;

public class CompletedAchievement implements Comparable<CompletedAchievement> {
	
	private Achievement achievement;
	private int userID;
	private Timestamp timestamp;

	public CompletedAchievement(Achievement achievement, int userID,
								Timestamp timestamp) {
		this.achievement = achievement;
		this.userID = userID;
		this.timestamp = timestamp;
	}
	
	public Achievement getAchievement() {
		return achievement;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public Timestamp getTimestamp() {
	return timestamp;
	}
	
	@Override
	public int compareTo(CompletedAchievement other) {
		if (timestamp.compareTo(other.getTimestamp()) == 0) {
		return achievement.getAchievementID() - 
		other.getAchievement().getAchievementID();
	} else {
		return timestamp.compareTo(other.getTimestamp());
		}
	}
}
