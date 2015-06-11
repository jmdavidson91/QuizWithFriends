<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/base.css" rel="stylesheet">
<title>Search Failed</title>
</head>
<body>

<%
if (session.getAttribute("viewer") == null) {
%>
<jsp:forward page="index.html"></jsp:forward>
<%
}
%>

<div id="top">
<h1>Couldn't Find User.</h1>
</div>

<div id="middle">
<p>
<%
User viewer = (User) session.getAttribute("viewer");
String username = (String) request.getAttribute("username");
out.println("A user with the username \"" + username + "\" does not exist. Please try again.");
%>
<p>
</div>

<div id="right">
<h3>QuizWithFriends</h3>
<a href="homepage.jsp">Homepage</a>
<br><a href="quizlist.jsp">Play Quizzes</a>
<br><a href="quizhistory.jsp?userID=<%= viewer.getUserID() %>">Quiz History</a>
<br><a href="message_list.jsp?m=<%= Message.RECEIVED_MESSAGES %>">Inbox (<%= Message.getUserMessageCount(viewer.getUserID(), Message.RECEIVED_MESSAGES, Message.ALL_MESSAGE_TYPES, true) %>)</a>
| <a href="message_list.jsp?m=<%= Message.SENT_MESSAGES %>">Sent</a>
| <a href="message_list.jsp?m=<%= Message.DELETED_MESSAGES %>">Trash</a>
<br><a href="achievementslist.jsp?userID=<%= viewer.getUserID() %>">Achievements</a>
<br><a href="friendslist.jsp?userID=<%= viewer.getUserID() %>">Friends</a>
<%
if (viewer.isAdministrator()) {
	out.println("<br><a href=\"administration.jsp\">Administration</a>");
}
%>
<hr>
&gt; <form action="UserSearchServlet" method="get">
Search for User: <input type="text" name="username">
<input type="submit" value="Login">
</form>
<a href="LogoutServlet">Logout</a>
</div>

</body>
</html>