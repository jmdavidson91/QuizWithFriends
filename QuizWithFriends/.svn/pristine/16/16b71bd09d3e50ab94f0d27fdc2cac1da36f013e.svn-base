package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The RequestMessage class is used for sending friend requests between users.
 * 
 * @author Evan Jacobs <ehjacobs@stanford.edu>
 */
public class RequestMessage extends Message {
	public final static int REQUEST_PENDING = 0;
	public final static int REQUEST_ACCEPTED = 1;
	public final static int REQUEST_REJECTED = 2;
	
	private final String body;
	
	/**
	 * Creates a new RequestMessage
	 * 
	 * @param senderUserID user ID of the sender
	 * @param recipientUserID user ID of the recipient
	 * @param priorMessageID message ID of the message that this is a reply to
	 * @param body the body of the message
	 */
	public RequestMessage(int senderUserID, int recipientUserID, int priorMessageID, String body) {
		super(senderUserID, recipientUserID, priorMessageID, Message.REQUEST_MESSAGE, false, false);
		
		this.body = body;
		
		String sql;
		ResultSet rs;
		
		try {			
			sql = "INSERT INTO requestMessages "
				+ "(messageID) "
				+ "VALUES ("
				+ messageID
				+ ")";
			statement.executeUpdate(sql);
			
			sql = "SELECT * FROM requestMessages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			rs.updateString("body", body);
			rs.updateRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Extract a request message from the database (construct a new message object and populate it with data from DB)
	 * 
	 * @param id message ID
	 */
	public RequestMessage(int id) {
		super(id);
		
		String sql;
		ResultSet rs;
		
		String tempBody = null;
		
		try {
			sql = "SELECT body FROM requestMessages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			tempBody = rs.getString("body");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		body = tempBody;
	}
	
	/**
	 * 
	 * @see quiz.Message#getSubject()
	 */
	@Override
	public String getSubject() {
		StringBuilder subject = new StringBuilder();
		
		subject.append("Friend request from ");
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
		return body;
	}

	/**
	 * 
	 * @see quiz.Message#getBodyHTMLSender()
	 */
	@Override
	public String getBodyHTMLSender() {
		StringBuilder body = new StringBuilder();
		
		User sender = User.getUser(this.senderUserID);
		User recipient = User.getUser(this.recipientUserID);
		
		body.append("<p>");
		body.append(recipient.getUsername());
		body.append(", ");
		body.append(sender.getUsername());
		body.append(" has sent you a friend request.");
		
		body.append("<p><i>Message from ");
		body.append(sender.getUsername());
		body.append(": </i><br>");
		body.append(this.getBodyText());
		
		int responseStatus = this.getResponse();
		
		body.append("<p>");
		if (responseStatus == RequestMessage.REQUEST_PENDING) {
			body.append("This request is <b>pending</b>.");
		} else if (responseStatus == RequestMessage.REQUEST_ACCEPTED) {
			body.append("This request was <b>accepted</b> at ");
			Timestamp responseTime = this.getResponseTime();
			SimpleDateFormat df = new SimpleDateFormat("h:mm a z");
			body.append(df.format(responseTime));
			df = new SimpleDateFormat("yyyy-MM-dd");
			body.append(" on ");
			body.append(df.format(responseTime));
			body.append(".");
		} else if (responseStatus == RequestMessage.REQUEST_REJECTED) {
			body.append("This request was <b>rejected</b> at ");
			Timestamp responseTime = this.getResponseTime();
			SimpleDateFormat df = new SimpleDateFormat("h:mm a z");
			body.append(df.format(responseTime));
			df = new SimpleDateFormat("yyyy-MM-dd");
			body.append(" on ");
			body.append(df.format(responseTime));
			body.append(".");
		}
		
		return body.toString();
	}
	
	/**
	 * 
	 * @see quiz.Message#getBodyHTMLRecipient()
	 */
	@Override
	public String getBodyHTMLRecipient() {
		int responseStatus = this.getResponse();

		if (responseStatus == RequestMessage.REQUEST_PENDING) {
			StringBuilder body = new StringBuilder();
			
			User sender = User.getUser(this.senderUserID);
			User recipient = User.getUser(this.recipientUserID);
			
			body.append("<p>");
			body.append(recipient.getUsername());
			body.append(", ");
			body.append(sender.getUsername());
			body.append(" has sent you a friend request.");
			
			body.append("<p><i>Message from ");
			body.append(sender.getUsername());
			body.append(": </i><br>");
			body.append(this.getBodyText());
			
			body.append("<p>This request is <b>pending</b>. Would you like to ");
			
			body.append("<form action=\"ProcessFriendRequestResponseServlet\" method=\"post\">");
			body.append("<input name=\"response\" type=\"hidden\" value=\"accept\"/>");
			body.append("<input name=\"messageID\" type=\"hidden\" value=\"" + this.getMessageID() + "\"/>");
			body.append("<input type=\"submit\" value=\"Accept\" />");
			body.append("</form>");
			
			body.append(" or ");
			
			body.append("<form action=\"ProcessFriendRequestResponseServlet\" method=\"post\">");
			body.append("<input name=\"response\" type=\"hidden\" value=\"reject\"/>");
			body.append("<input name=\"messageID\" type=\"hidden\" value=\"" + this.getMessageID() + "\"/>");
			body.append("<input type=\"submit\" value=\"Reject\" />");
			body.append("</form>");
			
			body.append(" this request?");
			
			return body.toString();
		} else {
			return this.getBodyHTMLSender();
		}
	}
	
	/**
	 * Retrieves the response status of this friend request
	 * 
	 * @return REQUEST_PENDING if pending, REQUEST_ACCEPTED if accepted, REQUEST_REJECTED if rejected (-1 if error)
	 */
	public int getResponse() {
		String sql;
		ResultSet rs;
		
		try {
			sql = "SELECT response FROM requestMessages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			return rs.getInt("response");
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Retrieves the date and time of the response to this friend request
	 * 
	 * @return date and time the recipient accepted or rejected this friend request (or null if request still pending)
	 */
	public Timestamp getResponseTime() {
		String sql;
		ResultSet rs;
		
		try {
			sql = "SELECT responseTime FROM requestMessages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			return rs.getTimestamp("responseTime");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Sets the response status of this friend request; updates 'responseTime'
	 * 
	 * @param response REQUEST_ACCEPTED if accepted, REQUEST_REJECTED if rejected
	 */
	public void setResponse(int response) {
		String sql;
		ResultSet rs;
		
		try {			
			sql = "SELECT messageID, response, responseTime FROM requestMessages WHERE messageID = "
				+ messageID;
			rs = statement.executeQuery(sql);
			rs.first();
			rs.updateInt("response", response);
			Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
			rs.updateTimestamp("responseTime", currentTimestamp);
			rs.updateRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
