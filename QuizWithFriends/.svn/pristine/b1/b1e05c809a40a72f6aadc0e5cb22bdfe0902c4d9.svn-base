<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.Message, quiz.User, java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/base.css" rel="stylesheet">
<title>Friend Request</title>
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
<h1>Send Friend Request</h1>
</div>

<div id="left">
</div>

<div id="middle">
<form action="CreateRequestMessageServlet" method="post">

<%
ArrayList<String> usernames = User.getUsernames();
ArrayList<String> usernamesNotAlreadyFriends = new ArrayList<String>();

for (String username : usernames) {
	User user = User.getUser(username);
	if (currentUser.getUserID() != user.getUserID() && !currentUser.isFriendsWith(user.getUserID())) {
		usernamesNotAlreadyFriends.add(username);
	}
}

if (usernamesNotAlreadyFriends.size() == 0) {
	out.println("<p>There are no users that you aren't already friends with! <a href=\"homepage.jsp\">Go to homepage.</a>");
} else {
	out.println("<p>To: ");
	
	if (request.getParameter("u") != null) {
		User recipient = User.getUser(Integer.parseInt(request.getParameter("u")));
		out.println("<input name=\"recipientName\" type=\"hidden\" value=\"" + recipient.getUsername() + "\"/>");
		out.println(recipient.getUsername());
	} else {
		out.println("<select name=\"recipientName\">");
		for (String username : usernamesNotAlreadyFriends) {
			out.println("<option value=\"" + username + "\">" + username + "</option>");
		}
		out.println("</select>");
	}
	
	out.println("<p>Message:");
	out.println("<p><textarea cols=\"40\" rows=\"5\" name=\"body\"></textarea>");
	out.println("<p><input type=\"submit\" value=\"Send\" /></p>");
}
%>

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
<a href="message_create_basic.jsp">Compose New Message</a>
<br>&gt; <a href="message_create_request.jsp">Send Friend Request</a>
<br><a href="message_create_challenge.jsp">Send Quiz Challenge</a>
<hr>
<a href="LogoutServlet">Logout</a>
</div>

</body>
</html>