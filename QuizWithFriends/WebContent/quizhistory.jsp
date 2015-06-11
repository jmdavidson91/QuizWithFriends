<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/base.css" rel="stylesheet">
<title>Quiz History</title>
</head>
<body>

<% final int RECENT_DISPLAY_NUM = 3; %>

<div id="top">
<h2>
<%

User user;
int userID = -1;
String userIDString = request.getParameter("userID");
if (userIDString == null) {
	user = null;
} else {
	userID = Integer.parseInt(request.getParameter("userID"));
	user = User.getUser(userID);
}
if (user == null) {
	user = (User) session.getAttribute("viewer");
	if (user == null) {
%>

<jsp:forward page="index.jsp"></jsp:forward>

<%		
	}
	userID = user.getUserID();
}
out.println(user.getUsername() + "'s Quiz History");
User viewer = (User) session.getAttribute("viewer");
%>
</h2>
</div>

<div id="middle">
<h3><%= user.getUsername() + "'s Created Quizzes:" %></h3>

<%-- Created Quizzes Stuff --%>
<%
ArrayList<Quiz> userQuizzesByDateCreated = Quiz.getRecentQuizzesSortedByDateCreated(user.getUserID());

if (userQuizzesByDateCreated.size() == 0) {
	out.println("<p>No Quizzes</p>");
} else {
	int displayNum = userQuizzesByDateCreated.size();
	for (int i = 0; i < displayNum; i++) {
		Quiz quiz = userQuizzesByDateCreated.get(i);
		
		out.println("<a href=\"CreateQuizSummaryServlet?quiz_id=" + quiz.getQuizID() + "\">");
		out.println(quiz.getQuizTitle() + "</a> - ");
		out.println(quiz.getDescription() + "<br>");
	}
}
%>
<br>

<h3><%= user.getUsername() + "'s Taken Quizzes:" %></h3>

<%-- Taken Quizzes Stuff --%>
<%
ArrayList<QuizResult> qrs = QuizResult.getQuizResultsForUserSortedByDateTaken(user.getUserID());
if (qrs.size() == 0) {
	out.println("<p>Haven't taken any quizzes</p>");
} else {
	int displayNum = qrs.size();
	for (int i = 0; i < displayNum; i++) {
		QuizResult qr = qrs.get(i);
		Quiz qz = new Quiz(qr.getQuizID());
		out.println("<p><a href=\"homepage.jsp?user_id=" + user.getUserID() + "\">" + user.getUsername() + "</a>");
		out.println(" took the <a href=\"CreateQuizSummaryServlet?quiz_id=" + qz.getQuizID() + "\">");
		out.println(qz.getQuizTitle() + "</a> and scored " + qr.getScore() + " in " + qr.getTimeTaken() + " seconds<p>");
	}
}

%>
</div>

<div id="right">
<h3>QuizWithFriends</h3>
<a href="homepage.jsp">Homepage</a>
<br><a href="quizlist.jsp">Play Quizzes</a>
<br>&gt; <a href="quizhistory.jsp?userID=<%= viewer.getUserID() %>">Quiz History</a>
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
<a href="LogoutServlet">Logout</a>
</div>

</body>
</html>