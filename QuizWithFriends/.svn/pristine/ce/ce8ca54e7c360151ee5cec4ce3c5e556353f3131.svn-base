package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * The ChallengeMessage class is used for sending quiz challenges between users.
 * 
 * @author Evan Jacobs <ehjacobs@stanford.edu>
 */
public class ChallengeMessage extends Message {
	private final String body;
	private final int quizID;
	private final String quizName;
	private final int senderHighScore;
	
	/**
	 * Creates a new ChallengeMessage
	 * 
	 * @param senderUserID user ID of the sender
	 * @param recipientUserID user ID of the recipient
	 * @param priorMessageID message ID of the message that this is a reply to
	 * @param body the body of the message
	 * @param quiz the quiz that sender is challenging recipient to take
	 */
	public ChallengeMessage(int senderUserID, int recipientUserID, int priorMessageID, String body, Quiz quiz) {
		super(senderUserID, recipientUserID, priorMessageID, Message.CHALLENGE_MESSAGE, false, false);
		
		String sql;
		ResultSet rs;
		
		this.body = body;
		this.quizID = quiz.getQuizID();
		this.quizName = quiz.getQuizTitle();
		this.senderHighScore = Quiz.getHighestQuizScoreForUser(senderUserID, quiz.getQuizID());
		
		try {			
			sql = "INSERT INTO challengeMessages "
				+ "(messageID) "
				+ "VALUES ("
				+ messageID
				+ ")";
			statement.executeUpdate(sql);
			
			sql = "SELECT * FROM challengeMessages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			rs.updateString("body", this.body);
			rs.updateInt("quizID", this.quizID);
			rs.updateString("quizName", this.quizName);
			rs.updateInt("senderHighScore", this.senderHighScore);
			rs.updateRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Extract a challenge message from the database (construct a new message object and populate it with data from DB)
	 * 
	 * @param id message ID
	 */
	public ChallengeMessage(int id) {
		super(id);
		
		String tempBody = null;
		int tempQuizID = 0;
		String tempQuizName = null;
		int tempSenderHighScore = 0;
		
		String sql;
		ResultSet rs;
		
		try {
			sql = "SELECT body, quizID, quizName, senderHighScore FROM challengeMessages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			tempBody = rs.getString("body");
			tempQuizID = rs.getInt("quizID");
			tempQuizName = rs.getString("quizName");
			tempSenderHighScore = rs.getInt("senderHighScore");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.body = tempBody;
		this.quizID = tempQuizID;
		this.quizName = tempQuizName;
		this.senderHighScore = tempSenderHighScore;
	}
	
	/**
	 * 
	 * @see quiz.Message#getSubject()
	 */
	@Override
	public String getSubject() {
		StringBuilder subject = new StringBuilder();
		
		subject.append("Quiz challenge from ");
		User sender = User.getUser(this.getSenderUserID());
		subject.append(sender.getUsername());
		
		return subject.toString();
	}

	/**
	 * 
	 * @see quiz.Message#getBodyText()
	 */
	@Override
	public String getBodyText() {
		return this.body;
	}

	/**
	 * 
	 * @see quiz.Message#getBodyHTMLSender()
	 */
	@Override
	public String getBodyHTMLSender() {
		return this.getBodyHTMLRecipient();
	}
	
	/**
	 * 
	 * @see quiz.Message#getBodyHTMLRecipient()
	 */
	@Override
	public String getBodyHTMLRecipient() {
		StringBuilder body = new StringBuilder();
		
		User sender = User.getUser(this.senderUserID);
		User recipient = User.getUser(this.recipientUserID);
		
		body.append("<p>" + recipient.getUsername());
		body.append(", ");
		body.append(sender.getUsername());
		body.append(" has challenged you to play the quiz ");
		body.append("<a href=\"CreateQuizSummaryServlet?quiz_id=" + this.getQuizID() + "\">");
		body.append(this.getQuizName() + "</a>");
		body.append(".");
		
		body.append("<p><i>Message from ");
		body.append(sender.getUsername());
		body.append(": </i><br>");
		body.append(this.getBodyText());
		
		body.append("<p>" + sender.getUsername());
		body.append("'s best score on \"");
		body.append(this.getQuizName() + "\" at the time of the challenge was ");
		body.append(this.getSenderHighScore() + ".");
		
		return body.toString();
	}
	
	/**
	 * Get the ID number of the given quiz
	 * 
	 * @return quiz ID number
	 */
	public int getQuizID() {
		return this.quizID;
	}
	
	/**
	 * Get the name of the given quiz
	 * 
	 * @return quiz name
	 */
	public String getQuizName() {
		return this.quizName;
	}
	
	/**
	 * Get the high score that the sender had received on the challenge quiz at the time the challenge was sent
	 * 
	 * @return quiz score
	 */
	public int getSenderHighScore() {
		return this.senderHighScore;
	}
}
