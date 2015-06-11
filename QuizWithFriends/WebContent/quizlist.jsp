<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="quiz.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/base.css" rel="stylesheet">
<title>Take A Quiz</title>
</head>
<body>

<%
if (session.getAttribute("viewer") == null) {
%>
<jsp:forward page="index.html"></jsp:forward>
<%
}
%>

<%
User currentUser = (User) session.getAttribute("viewer");
%>

<div id="top">
<h2>Take a quiz!</h2>
</div>

<div id="middle">
<h3>All quizzes</h3>
<br>
<%
ArrayList<Quiz> allQuizzes = Quiz.returnAllQuizObjectsStoredInTheDB();

for (int i = 0; i < allQuizzes.size(); i++) {
	out.println((i+1) + ". ");
	out.println("<a href=\"CreateQuizSummaryServlet?quiz_id=" + allQuizzes.get(i).getQuizID() + "\">" + allQuizzes.get(i).getQuizTitle() + "</a>");
	if (allQuizzes.get(i).getCreatorID() == currentUser.getUserID())
		out.println("<a href=\"CreateQuizServlet?quiz_id=" + allQuizzes.get(i).getQuizID() + "\">Edit</a>");
	out.println("<br>");
}
%>
</div>

<div id="right">
<h3>QuizWithFriends</h3>
<a href="homepage.jsp">Homepage</a>
<br>&gt; <a href="quizlist.jsp">Play Quizzes</a>
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
<a href="LogoutServlet">Logout</a>
</div>

</body>
</html>