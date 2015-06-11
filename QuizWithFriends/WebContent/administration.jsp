<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.Quiz, quiz.User, quiz.Message, quiz.QuizResult, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/base.css" rel="stylesheet">
<title>Administration</title>
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
<h1>Administration</h1>
</div>

<div id="left">

</div>

<div id="middle">
<h3>Site statistics</h3>
<ul>
<li>Number of users: <%= User.getNumUsers() %></li>
<li>Number of quizzes taken: <%= QuizResult.getTotalResults() %></li>
<li>Number of quizzes total: <%= Quiz.getTotalNumerOfQuizzes() %></li>
</ul>
</div>

<div id="right">
<%
User currentUser = (User) session.getAttribute("viewer");
%>
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
	out.println("<br>&gt; <a href=\"administration.jsp\">Administration</a>");
}
%>
<hr>
&gt; <a href="administration.jsp">Admin Home</a>
<br><a href="message_create_basic.jsp?a=1">Create Announcement</a>
<br><a href="EditUserServlet">Edit Users</a>
<br><a href="EditQuizzesServlet">Edit Quizzes</a>
<hr>
<a href="LogoutServlet">Logout</a>
</div>

</body>
</html>