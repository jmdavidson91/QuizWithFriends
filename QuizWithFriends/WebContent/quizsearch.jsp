<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/base.css" rel="stylesheet">
<title>Quiz Search</title>
</head>
<body>

<%
if (session.getAttribute("viewer") == null) {
%>
<jsp:forward page="index.html"></jsp:forward>
<%
}
%>

<% User viewer = (User) session.getAttribute("viewer"); %>

<div id="top">
<h2>Quizzes Found:</h2>
</div>

<div id="middle">
<%
@SuppressWarnings("unchecked")
ArrayList<Quiz> quizzes = (ArrayList<Quiz>) request.getAttribute("quizzes");
if (quizzes != null) {
	int size = quizzes.size();
	for (Quiz quiz: quizzes) {
		out.println("<p><a href=\"CreateQuizSummaryServlet?quiz_id=" + quiz.getQuizID() + "\">");
		out.println(quiz.getQuizTitle() + "</a> - " + quiz.getDescription() + "<p>");
	}
}
%>
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
<br>&gt; <form action="QuizSearchServlet" method="get">
Search for Quiz: <input type="text" name="quizName">
<input type="submit" value="Search">
</form>
<hr>
<a href="LogoutServlet">Logout</a>
</div>

</body>
</html>