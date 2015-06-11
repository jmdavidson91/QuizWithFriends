<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.Message, quiz.User, java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/base.css" rel="stylesheet">
<title>Message</title>
</head>
<body>

<%
if (session.getAttribute("viewer") == null) {
%>
<jsp:forward page="index.html"></jsp:forward>
<%
}

User currentUser = (User) session.getAttribute("viewer");

if (currentUser.isDeleted()) {
	%>
	<jsp:forward page="LogoutServlet"></jsp:forward>
	<%
}

SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd h:mm:ss a z");

int messageID = Integer.parseInt(request.getParameter("id"));
Message message = Message.getMessage(messageID);

User sender = User.getUser(message.getSenderUserID());
User recipient = User.getUser(message.getRecipientUserID());

// If the current (logged in) user is neither the sender or recipient of this message, don't let them view it
if (currentUser.getUserID() != sender.getUserID() && currentUser.getUserID() != recipient.getUserID()) {
%>
<jsp:forward page="message_list.jsp"></jsp:forward>
<%
}

// If the current user is the recipient, mark message as 'read' when they view it
if (currentUser.getUserID() == recipient.getUserID()) message.setUnreadStatus(false);

%>

<div id="top">
<h1><%= message.getSubject() %></h1>
</div>

<div id="left">
</div>

<div id="middle">
<%
out.print("From: ");
if (!(sender.isDeleted())) out.print("<a href=\"homepage.jsp?user_id=" + sender.getUserID() + "\">");
out.print(sender.getUsername());
if (!(sender.isDeleted())) out.print("</a>");
if (currentUser.getUserID() == sender.getUserID()) out.print(" <i>(you)</i>");
if (sender.isAdministrator()) out.print(" <i>(admin)</i>");
if (sender.isDeleted()) out.println(" <i>(deleted account)</i>");

if (message.getAdminClassification()) {
	out.println("<br>To: " + Message.ADMIN_TO);
} else {
	out.print("<br>To: ");
	if (!(recipient.isDeleted())) out.print("<a href=\"homepage.jsp?user_id=" + recipient.getUserID() + "\">");
	out.print(recipient.getUsername());
	if (!(recipient.isDeleted())) out.print("</a>");
	if (currentUser.getUserID() == recipient.getUserID()) out.print(" <i>(you)</i>");	
	if (recipient.isAdministrator()) out.print(" <i>(admin)</i>");
	if (recipient.isDeleted()) out.println(" <i>(deleted account)</i>");
}

out.println("<br>Sent: " + df.format(message.getTimeSent()));
if (message.getPriorMessageID() > 0) {
	Message priorMessage = message.getPriorMessage();
	out.println("<br>In reply to <a href = \"message_display.jsp?id=" + priorMessage.getMessageID() + "\">" + priorMessage.getSubject() + "</a>");
}

out.println("<hr>");

if (currentUser.getUserID() == sender.getUserID()) {
	out.println("<p>" + message.getBodyHTMLSender());
} else if (currentUser.getUserID() == recipient.getUserID()) {
	out.println("<p>" + message.getBodyHTMLRecipient());
}

out.println("<hr>");

if (currentUser.getUserID() != sender.getUserID() && !(sender.isDeleted())) {
%>
<form action="message_create_basic.jsp" method="get">
<input name="r" type="hidden" value=<%= message.getMessageID() %>>
<input type="submit" value="Reply">
</form>
<%
}

out.println("<form action=\"DeleteMessageServlet\" method=\"post\">");
out.println("<input name=\"messageID\" type=\"hidden\" value=\"" + messageID + "\" />");
if ((currentUser.getUserID() == recipient.getUserID() && message.getRecipientDeletedStatus() == false) || 
		(currentUser.getUserID() == sender.getUserID() && message.getSenderDeletedStatus() == false)) {
	out.println("<input name=\"delete\" type=\"hidden\" value=\"true\" />");
	out.println("<input type=\"submit\" value=\"Delete\" />");	
} else {
	out.println("<input name=\"delete\" type=\"hidden\" value=\"false\" />");
	out.println("<input type=\"submit\" value=\"Undelete\" />");	
}
out.println("</form>");

if (currentUser.getUserID() == recipient.getUserID() && message.getUnreadStatus() == false) {
	out.println("<form action=\"MarkMessageUnreadServlet\" method=\"post\">");
	out.println("<input name=\"messageID\" type=\"hidden\" value=\"" + messageID + "\" />");
	out.println("<input type=\"submit\" value=\"Mark Unread\" />");
	out.println("</form>");
}
%>
</div>

<div id="right">
<h3>QuizWithFriends</h3>
<a href="homepage.jsp">Homepage</a>
<br><a href="quizlist.jsp">Play Quizzes</a>
<br><a href="quizhistory.jsp?userID=<%= currentUser.getUserID() %>">Quiz History</a>
<br><a href="message_list.jsp?m=<%= Message.RECEIVED_MESSAGES %>">Inbox (<%= Message.getUserMessageCount(currentUser.getUserID(), Message.RECEIVED_MESSAGES, Message.ALL_MESSAGE_TYPES, true) %>)</a>
| <a href="message_list.jsp?m=<%= Message.SENT_MESSAGES %>">Sent</a>
| <a href="message_list.jsp?m=<%= Message.DELETED_MESSAGES %>">Trash</a>
<br><a href="achievementslist.jsp?userID=<%= currentUser.getUserID() %>">Achievements</a>
<br><a href="friendslist.jsp?userID=<%= currentUser.getUserID() %>">Friends</a>
<%
if (currentUser.isAdministrator()) {
	out.println("<br><a href=\"administration.jsp\">Administration</a>");
}
%>
<hr>
<a href="message_create_basic.jsp">Compose New Message</a>
<br><a href="message_create_request.jsp">Send Friend Request</a>
<br><a href="message_create_challenge.jsp">Send Quiz Challenge</a>
<hr>
<a href="LogoutServlet">Logout</a>
</div>

</body>
</html>