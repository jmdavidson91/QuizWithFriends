<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/base.css" rel="stylesheet">
<title>Friends List</title>
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
<h3>
<%
int userID = Integer.parseInt(request.getParameter("userID"));
User user = User.getUser(userID);
out.println(user.getUsername() + "'s Friends:");
%>
</h3>
</div>

<div id="middle">
<%
User viewer = (User) session.getAttribute("viewer");
ArrayList<Integer> friendIDs = user.getFriendships();

for (int friendID: friendIDs) {
	User friend = User.getUser(friendID);
	out.println("<a href=\"GoToHomepageServlet?userID=" + friend.getUserID() + "\">" + friend.getUsername() + "</a>");
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
<br>&gt; <a href="friendslist.jsp?userID=<%= viewer.getUserID() %>">Friends</a>
<%
if (viewer.isAdministrator()) {
	out.println("<br><a href=\"administration.jsp\">Administration</a>");
}
%>
<hr>
<a href="message_create_request.jsp">Send Friend Request</a>
<hr>
<br><a href="LogoutServlet">Logout</a>
</div>

</body>
</html>