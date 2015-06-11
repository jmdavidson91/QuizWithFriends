package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Message class is an abstract class that defines the common interface for various
 * message subclasses.
 * 
 * This class implements Comparable. The default sort order is oldest messages first, based
 * on time. (The Message.getUserMessages() function reverses this order)
 * 
 * @author Evan Jacobs <ehjacobs@stanford.edu>
 */
public abstract class Message implements Comparable<Message> {
	public final static int ALL_MESSAGE_TYPES = 0;
	public final static int BASIC_MESSAGE = 1;
	public final static int REQUEST_MESSAGE = 2;
	public final static int CHALLENGE_MESSAGE = 3;
	
	public final static int RECEIVED_MESSAGES = 1;
	public final static int SENT_MESSAGES = 2;
	public final static int DELETED_MESSAGES = 3;
	
	public final static String ADMIN_TO = "All Users"; // Admin announcements show up as to "All Users" rather than being addressed to someone in particular
	
	protected final int messageID;		// A unique ID for each message ... ties to DB tables
	protected final int messageType;	// BASIC_MESSAGE, REQUEST_MESSAGE, or CHALLENGE_MESSAGE
	
	protected final int senderUserID;		// The sender
	protected final int recipientUserID;	// The recipient
	protected final int priorMessageID;		// The ID of the prior message, if the current message is a reply
	
	protected final Timestamp timeSent;		// Time the message was sent
	
	protected final boolean isAdminMessage;				// A message send by an admin as an announcement
	protected final boolean isDuplicateAdminMessage;	// When an admin sends an announcement, a new message is generated on the backend for every recipient.
														// All but the first message are marked as duplicates so admin only sees one of them in their outbox
	
	protected Statement statement;
	
	/**
	 * Constructs a new Message object and adds it to the DB; only to be called from within a subclass constructor
	 * 
	 * @param senderID ID of the user sending the message
	 * @param recipientID ID of the user recieving the message
	 * @param priorID ID of prior message (if a reply)
	 * @param type type of message (BASIC_MESSAGE, REQUEST_MESSAGE, CHALLENGE_MESSAGE)
	 * @param isAdmin 'true' if this is an admin announcement, 'false' for all other messages
	 * @param isDuplicateAdmin 'true' if this is a duplicate admin announcement, 'false' otherwise
	 */
	public Message(int senderID, int recipientID, int priorID, int type, boolean isAdmin, boolean isDuplicateAdmin) {
		String sql;
		statement = DBConnection.getStatement();
		ResultSet rs;
		
		this.messageType = type;
		
		this.senderUserID = senderID;
		this.recipientUserID = recipientID;
		this.priorMessageID = priorID;
		
		this.isAdminMessage = isAdmin;
		this.isDuplicateAdminMessage = isDuplicateAdmin;
		
		int tempMessageID = 0;	// Need a temporary field until setting 'messageID', since 'messageID' is final
		Timestamp tempTimeSent = null;
		
		try {
			sql = "INSERT INTO messages "
				+ "(messageType, senderUserID, recipientUserID, priorMessageID, isAdminMessage, isDuplicateAdminMessage) "
				+ "VALUES ("
				+ messageType + ", "
				+ senderUserID + ", "
				+ recipientUserID + ", "
				+ priorMessageID + ", "
				+ isAdminMessage + ", "
				+ isDuplicateAdminMessage
				+ ")";
			statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rs = statement.getGeneratedKeys();
			rs.first();
			tempMessageID = rs.getInt("GENERATED_KEY");	// Message ID # is automatically created by SQL
			
			sql = "SELECT timeSent FROM messages WHERE messageID = "
				+ tempMessageID;
			rs = statement.executeQuery(sql);
			rs.first();
			tempTimeSent = rs.getTimestamp("timeSent");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		messageID = tempMessageID;	// Set final data fields
		timeSent = tempTimeSent;	
	}
	
	/**
	 * Constructs a new Message object and fills it with data from existing message in DB; only to be called from within a subclass constructor
	 * 
	 * @param id message ID of an existing message
	 */
	public Message(int id) {
		String sql;
		statement = DBConnection.getStatement();
		ResultSet rs;
		
		this.messageID = id;
		
		int tempMessageType = 0;
		int tempSenderUserID = 0;
		int tempRecipientUserID = 0;
		int tempPriorMessageID = 0;
		Timestamp tempTimeSent = null;
		boolean tempIsAdminMessage = false;
		boolean tempIsDuplicateAdminMessage = false;
		
		try {
			sql = "SELECT * FROM messages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			tempMessageType = rs.getInt("messageType");
			tempSenderUserID = rs.getInt("senderUserID");
			tempRecipientUserID = rs.getInt("recipientUserID");
			tempPriorMessageID = rs.getInt("priorMessageID");
			tempTimeSent = rs.getTimestamp("timeSent");
			tempIsAdminMessage = rs.getBoolean("isAdminMessage");
			tempIsDuplicateAdminMessage = rs.getBoolean("isDuplicateAdminMessage");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		messageType = tempMessageType;
		senderUserID = tempSenderUserID;
		recipientUserID = tempRecipientUserID;
		priorMessageID = tempPriorMessageID;
		timeSent = tempTimeSent;
		isAdminMessage = tempIsAdminMessage;
		isDuplicateAdminMessage = tempIsDuplicateAdminMessage;
	}
	
	/**
	 * Get the message type (basic, request, or challenge)
	 * 
	 * @return BASIC_MESSAGE, REQUEST_MESSAGE, or CHALLENGE_MESSAGE
	 */
	public int getMessageType() {
		return messageType;
	}
	
	public String getMessageTypeString() {
		if (messageType == Message.BASIC_MESSAGE) {
			return "basicMessage";
		} else if (messageType == Message.REQUEST_MESSAGE) {
			return "requestMessage";
		} else if (messageType == Message.CHALLENGE_MESSAGE) {
			return "challengeMessage";
		}
		
		return null;
	}
	
	/**
	 * Retrieves the unique ID number for this message
	 * 
	 * @return message ID number
	 */
	public int getMessageID() {
		return messageID;
	}
	
	/**
	 * Comparator to sort messages from oldest to newest.
	 */
	@Override
	public int compareTo(Message other) {
		Timestamp thisTime = this.getTimeSent();
		Timestamp otherTime = other.getTimeSent();
		
		if (thisTime.compareTo(otherTime) == 0) {
			return this.getMessageID() - other.getMessageID();
		} else {
			return thisTime.compareTo(otherTime);
		}
	}
	
	/**
	 * Retrieves the date & time this message was sent
	 * 
	 * @return date & time sent
	 */
	public Timestamp getTimeSent() {
		return timeSent;
	}
	
	/**
	 * Retrieves the ID of the user who sent this message
	 * 
	 * @return user ID of sender
	 */
	public int getSenderUserID() {
		return senderUserID;
	}
	
	/**
	 * Retrieves the ID of the user who received this message
	 * 
	 * @return user ID of recipient
	 */
	public int getRecipientUserID() {
		return recipientUserID;
	}
	
	/**
	 * Retrieves the unread/read status of the message
	 * 
	 * @return true if message is unread, false if message is read
	 */
	public boolean getUnreadStatus() {
		String sql;
		ResultSet rs;
		
		try {
			sql = "SELECT unreadStatus FROM messages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			return rs.getBoolean("unreadStatus");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Sets the message to unread or read
	 * 
	 * @param unread true if unread, false if read
	 */
	public void setUnreadStatus(boolean unread) {
		String sql;
		ResultSet rs;
		
		try {			
			sql = "SELECT messageID, unreadStatus FROM messages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			rs.updateBoolean("unreadStatus", unread);
			rs.updateRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves the deleted/not deleted status of the message, from
	 * the perspective of the sender
	 * 
	 * @return true if deleted by sender, false if not deleted
	 */
	public boolean getSenderDeletedStatus() {
		String sql;
		ResultSet rs;
		
		try {
			sql = "SELECT senderDeletedStatus FROM messages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			return rs.getBoolean("senderDeletedStatus");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Set the deleted/not deleted status of the message, from
	 * the perspective of the sender
	 * 
	 * @param deleted true if deleted, false if not deleted
	 */
	public void setSenderDeletedStatus(boolean deleted) {
		String sql;
		ResultSet rs;
		
		try {
			sql = "SELECT messageID, senderDeletedStatus FROM messages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			rs.updateBoolean("senderDeletedStatus", deleted);
			rs.updateRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves the deleted/not deleted status of the message, from
	 * the perspective of the recipient
	 * 
	 * @return true if deleted by recipient, false if not deleted
	 */
	public boolean getRecipientDeletedStatus() {
		String sql;
		ResultSet rs;
		
		try {
			sql = "SELECT recipientDeletedStatus FROM messages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			return rs.getBoolean("recipientDeletedStatus");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Set the deleted/not deleted status of the message, from
	 * the perspective of the recipient
	 * 
	 * @param deleted true if deleted, false if not deleted
	 */
	public void setRecipientDeletedStatus(boolean deleted) {
		String sql;
		ResultSet rs;
		
		try {
			sql = "SELECT messageID, recipientDeletedStatus FROM messages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			rs.updateBoolean("recipientDeletedStatus", deleted);
			rs.updateRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves whether or not this is an admin announcement
	 * 
	 * @return 'true' if admin announcement, 'false' if not
	 */
	public boolean getAdminClassification() {
		return isAdminMessage;
	}
	
	/**
	 * Retrieves whether or not this is a duplicate admin announcement
	 * 
	 * @return 'true' if duplicate admin announcement, 'false if not (either not an admin message, or first instance of this admin message)
	 */
	public boolean getDuplicateAdminClassification() {
		return isDuplicateAdminMessage;
	}
	
	/**
	 * Retrieves the message's subject line
	 * 
	 * @return subject line
	 */
	public abstract String getSubject();
	
	/**
	 * Retrieves the text of the message's body.
	 * 
	 * @return body text
	 */
	public abstract String getBodyText();
	
	/**
	 * Retrieves the message's full body, in HTML, from the sender's perspective
	 * 
	 * @return body HTML
	 */
	public abstract String getBodyHTMLSender();
	
	/**
	 * Retrieves the message's full body, in HTML, from the recipient's perspective
	 * 
	 * @return body HTML
	 */
	public abstract String getBodyHTMLRecipient();
	
	/**
	 * Retrieves the ID number of the prior message, if this message is a reply
	 * 
	 * @return ID of prior message, or 0 if not a reply
	 */
	public int getPriorMessageID() {
		return priorMessageID;
	}
	
	/**
	 * Retrieves the prior message, if this message is a reply
	 * 
	 * @return prior message, or null if not a reply
	 */
	public Message getPriorMessage() {
		if (priorMessageID <= 0) {
			return null;
		} else {
			return Message.getMessage(priorMessageID);
		}
	}
	
	/**
	 * Retrieves a basic message, request message, or challenge message from the database
	 * 
	 * @param messageID ID of an existing message in the DB
	 * @return either a BasicMessage, RequestMessage, or ChallengeMessage object
	 */
	public static Message getMessage(int messageID) {
		String sql;
		ResultSet rs;
		Statement statement = DBConnection.getStatement();
		
		try {
			sql = "SELECT messageType FROM messages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			int messageType = rs.getInt("messageType");
			
			if (messageType == Message.BASIC_MESSAGE) {
				return new BasicMessage(messageID);
			} else if (messageType == Message.REQUEST_MESSAGE) {
				return new RequestMessage(messageID);
			} else if (messageType == Message.CHALLENGE_MESSAGE) {
				return new ChallengeMessage(messageID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	
	/**
	 * Retrieves a user's messages, based on given parameters
	 * 
	 * @param userID user to retrieve messages for
	 * @param mode RECEIVED_MESSAGES for received, SENT_MESSAGES for sent, DELETED_MESSAGES for deleted (sent & received)
	 * @param messageType ALL_MESSAGE_TYPES for all messages, BASIC_MESSAGE for basic messages, REQUEST_MESSAGE for request messages, CHALLENGE_MESSAGE for challenge messages
	 * @param unreadOnly true for unread messages, false for all messages (read & unread) (NOTE: Only applicable for mode = RECEIVED_MESSAGES)
	 * @return ArrayList of messages matching parameters, sorted newest to oldest (by sent date)
	 */
	public static ArrayList<Message> getUserMessages(int userID, int mode, int messageType, boolean unreadOnly) {
		ResultSet rs;
		ArrayList<Integer> messageIDs = new ArrayList<Integer>();
		ArrayList<Message> messages = new ArrayList<Message>();
		Statement statement = DBConnection.getStatement();
		
		String sql = Message.generateSQLQueryForMessages(userID, mode, messageType, unreadOnly);
		
		try {
			rs = statement.executeQuery(sql);
			rs.beforeFirst();
			while (rs.next()) {
				int messageID = rs.getInt("messageID");
				messageIDs.add(messageID);
			}
			for (int ID : messageIDs) {
				Message message = Message.getMessage(ID);
				messages.add(message);
			}
			Collections.sort(messages, Collections.reverseOrder());
			return messages;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Retrieves number of messages a user has
	 * 
	 * @param userID user to retrieve message count for
	 * @param mode RECEIVED_MESSAGES for received, SENT_MESSAGES for sent, DELETED_MESSAGES for deleted (sent & received)
	 * @param messageType ALL_MESSAGE_TYPES for all messages, BASIC_MESSAGE for basic messages, REQUEST_MESSAGE for request messages, CHALLENGE_MESSAGE for challenge messages
	 * @param unreadOnly true for unread messages, false for all messages (read & unread) (NOTE: Only applicable for mode = RECEIVED_MESSAGES)
	 * @return number of messages matching parameters
	 */
	public static int getUserMessageCount(int userID, int mode, int messageType, boolean unreadOnly) {
		ResultSet rs;
		Statement statement = DBConnection.getStatement();
		
		int messageCount = 0;
		
		String sql = Message.generateSQLQueryForMessages(userID, mode, messageType, unreadOnly);
		
		try {
			rs = statement.executeQuery(sql);
			rs.beforeFirst();
			while (rs.next()) {
				messageCount++;
			}
			return messageCount;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Helper function to create SQL queries for Message.getUserMessages(..) and Message.getUserMessageCount(..)
	 * 
	 * @param userID user to retrieve messages / message count for
	 * @param mode RECEIVED_MESSAGES for received, SENT_MESSAGES for sent, DELETED_MESSAGES for deleted (sent & received)
	 * @param messageType ALL_MESSAGE_TYPES for all messages, BASIC_MESSAGE for basic messages, REQUEST_MESSAGE for request messages, CHALLENGE_MESSAGE for challenge messages
	 * @param unreadOnly true for unread messages, false for all messages (read & unread) (NOTE: Only applicable for mode = RECEIVED_MESSAGES)
	 * @return SQL query string for use with message getters or counters
	 */
	private static String generateSQLQueryForMessages(int userID, int mode, int messageType, boolean unreadOnly) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT messageID FROM messages WHERE");
		
		if (mode == SENT_MESSAGES) {
			sql.append(" (senderUserID = ");
			sql.append(userID);
			sql.append(" AND senderDeletedStatus = FALSE AND isDuplicateAdminMessage = FALSE)");
		} else if (mode == RECEIVED_MESSAGES) {
			sql.append(" (recipientUserID = ");
			sql.append(userID);
			if (unreadOnly) sql.append(" AND unreadStatus = TRUE");
			sql.append(" AND recipientDeletedStatus = FALSE)");
		} else if (mode == DELETED_MESSAGES) {
			sql.append(" ((senderUserID = ");
			sql.append(userID);
			sql.append(" AND senderDeletedStatus = TRUE)");
			sql.append(" OR (recipientUserID = ");
			sql.append(userID);
			sql.append(" AND recipientDeletedStatus = TRUE))");
		}

		if (messageType != Message.ALL_MESSAGE_TYPES) {
			sql.append(" AND messageType = ");
			sql.append(messageType);
		}

		return sql.toString();
	}
	
	/**
	 * 
	 */
	public static void buildMessageTables() {
		String sql;
		Statement statement = DBConnection.getStatement();
		
		try {
			sql = "DROP TABLE IF EXISTS messages";
			statement.executeUpdate(sql);
			sql = "DROP TABLE IF EXISTS basicMessages";
			statement.executeUpdate(sql);
			sql = "DROP TABLE IF EXISTS requestMessages";
			statement.executeUpdate(sql);
			sql = "DROP TABLE IF EXISTS challengeMessages";
			statement.executeUpdate(sql);
			
			sql = "CREATE TABLE messages"
				+ "(messageID INTEGER AUTO_INCREMENT, "
				+ "messageType INTEGER, "
				+ "senderUserID INTEGER, "
				+ "recipientUserID INTEGER, "
				+ "priorMessageID INTEGER, "
				+ "timeSent TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
				+ "unreadStatus BOOLEAN DEFAULT TRUE, "
				+ "senderDeletedStatus BOOLEAN DEFAULT FALSE, "
				+ "recipientDeletedStatus BOOLEAN DEFAULT FALSE, "
				+ "isAdminMessage BOOLEAN DEFAULT FALSE, "
				+ "isDuplicateAdminMessage BOOLEAN DEFAULT FALSE, "
				+ "PRIMARY KEY (messageID))";
			statement.executeUpdate(sql);
			
			sql = "CREATE TABLE basicMessages"
				+ "(messageID INTEGER, "
				+ "subject TEXT, "
				+ "body TEXT, "
				+ "PRIMARY KEY (messageID))";
			statement.executeUpdate(sql);
			
			sql = "CREATE TABLE requestMessages"
				+ "(messageID INTEGER, "
				+ "body TEXT, "
				+ "response INTEGER DEFAULT 0, "
				+ "responseTime TIMESTAMP, "
				+ "PRIMARY KEY (messageID))";
			statement.executeUpdate(sql);
			
			sql = "CREATE TABLE challengeMessages"
				+ "(messageID INTEGER, "
				+ "body TEXT, "
				+ "quizID INTEGER, "
				+ "quizName TEXT, "
				+ "senderHighScore INTEGER, "
				+ "PRIMARY KEY (messageID))";
			statement.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
