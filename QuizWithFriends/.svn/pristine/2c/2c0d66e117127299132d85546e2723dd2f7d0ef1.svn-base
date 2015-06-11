<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.Message, quiz.BasicMessage, quiz.User, java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/base.css" rel="stylesheet">
<title>Compose</title>
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

%>

<div id="top">
<h1>Compose New Message</h1>
</div>

<div id="left">
</div>

<div id="middle">
<form action="CreateBasicMessageServlet" method="post">

<%
if (request.getParameter("r") != null) {	
	int replyID = Integer.parseInt(request.getParameter("r"));
	Message priorMessage = Message.getMessage(replyID);
	User priorSender = User.getUser(priorMessage.getSenderUserID());
	
	out.println("<input name=\"replyID\" type=\"hidden\" value=\"" + replyID + "\"/>");
	out.println("<p>To: <a href=\"homepage.jsp?user_id=" + priorSender.getUserID() + "\">" + priorSender.getUsername() + "</a>");
	out.println("<input name=\"recipientName\" type=\"hidden\" value=\"" + priorSender.getUsername() + "\"/>");
	out.println("<p>Subject: <input type=\"text\" value=\"Re: " + priorMessage.getSubject() + "\" name=\"subject\" />");
} else if (request.getParameter("a") != null && currentUser.isAdministrator()) {
	out.println("<input name=\"isAdminMsg\" type=\"hidden\" value=1 />");
	out.println("<p>To: " + Message.ADMIN_TO);
	out.println("<p>Subject: " + BasicMessage.SUBJECT_TAG + " <input type=\"text\" name=\"subject\" />");
} else {
	out.println("<p>To: ");
	out.println("<select name=\"recipientName\">");
	ArrayList<String> usernames = User.getUsernames();
	for (String username : usernames) {
		if (!(username.equals(currentUser.getUsername()))) {
			out.println("<option value=\"" + username + "\">" + username + "</option>");
		}
	}
	out.println("</select>");
	out.println("<p>Subject: <input type=\"text\" name=\"subject\" />");
}
%>

<p>Message:
<p><textarea cols="40" rows="5" name="body"></textarea>
<p><input type="submit" value="Send" /></p>

</form>
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
&gt; <a href="message_create_basic.jsp">Compose New Message</a>
<br><a href="message_create_request.jsp">Send Friend Request</a>
<br><a href="message_create_challenge.jsp">Send Quiz Challenge</a>
<hr>
<a href="LogoutServlet">Logout</a>
</div>

</body>
</html>