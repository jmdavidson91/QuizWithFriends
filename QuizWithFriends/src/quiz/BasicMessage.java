package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The BasicMessage class is used for basic text messages between users.
 * 
 * @author Evan Jacobs <ehjacobs@stanford.edu>
 */
public class BasicMessage extends Message {
	public final static String SUBJECT_TAG = "[ANNOUNCEMENT]";
	
	private final String subject;
	private final String body;
	
	/**
	 * Creates a new BasicMessage
	 * 
	 * @param senderUserID user ID of the sender
	 * @param recipientUserID user ID of the recipient
	 * @param priorMessageID message ID of the message that this is a reply to
	 * @param subject the subject line of the message
	 * @param body the body of the message
	 */
	public BasicMessage(int senderUserID, int recipientUserID, int priorMessageID, String subject, String body, boolean isAdmin, boolean isAdminDuplicate) {
		super(senderUserID, recipientUserID, priorMessageID, Message.BASIC_MESSAGE, isAdmin, isAdminDuplicate);
		
		this.subject = subject;
		this.body = body;
		
		String sql;
		ResultSet rs;
		
		try {			
			sql = "INSERT INTO basicMessages "
				+ "(messageID) "
				+ "VALUES ("
				+ messageID
				+ ")";
			statement.executeUpdate(sql);
			
			sql = "SELECT * FROM basicMessages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			rs.updateString("subject", this.subject);
			rs.updateString("body", this.body);
			rs.updateRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Extract a basic message from the database (construct a new message object and populate it with data from DB)
	 * 
	 * @param id message ID
	 */
	public BasicMessage(int id) {
		super(id);
		
		String tempSubject = null;
		String tempBody = null;
		
		String sql;
		ResultSet rs;
		
		try {
			sql = "SELECT subject, body FROM basicMessages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			tempSubject = rs.getString("subject");
			tempBody = rs.getString("body");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.subject = tempSubject;
		this.body = tempBody;
	}
	
	/**
	 * 
	 * @see quiz.Message#getSubject()
	 */
	@Override
	public String getSubject() {
		if (this.isAdminMessage) {
			return BasicMessage.SUBJECT_TAG + " " + this.subject;
		} else {
			return this.subject;
		}
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
		return this.getBodyText();
	}
	
	/**
	 * 
	 * @see quiz.Message#getBodyHTMLRecipient()
	 */
	@Override
	public String getBodyHTMLRecipient() {
		return this.getBodyText();
	}
}