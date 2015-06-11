<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.Message, quiz.User, java.util.ArrayList, java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/base.css" rel="stylesheet">

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

int mode = Message.RECEIVED_MESSAGES;
int messageType = Message.ALL_MESSAGE_TYPES;
boolean unreadOnly = false;

if (request.getParameter("m") != null) {
	mode = Integer.parseInt(request.getParameter("m"));
}

if (request.getParameter("t") != null) {
	messageType = Integer.parseInt(request.getParameter("t"));
}

if (request.getParameter("u") != null) {
	unreadOnly = Boolean.parseBoolean(request.getParameter("u"));
}

StringBuilder title = new StringBuilder();
if (mode == Message.RECEIVED_MESSAGES) {
	title.append("Inbox");
} else if (mode == Message.SENT_MESSAGES) {
	title.append("Sent Messages");
} else if (mode == Message.DELETED_MESSAGES) {
	title.append("Trash");
}
%>

<title><%= title.toString() %></title>
</head>

<body>

<%
if (messageType == Message.BASIC_MESSAGE) {
	title.append(" - Messages");
} else if (messageType == Message.REQUEST_MESSAGE) {
	title.append(" - Friend Requests");
} else if (messageType == Message.CHALLENGE_MESSAGE) {
	title.append(" - Quiz Challenges");
}

if (unreadOnly) {
	title.append(" (Unread Only)");
}
%>

<div id="top">
<h1><%= title.toString() %></h1>
</div>

<div id="left">

<b>
<% if (mode == Message.RECEIVED_MESSAGES) {out.println("&gt; ");} %>
<a href="message_list.jsp?m=<%= Message.RECEIVED_MESSAGES %>">Inbox (<%= Message.getUserMessageCount(currentUser.getUserID(), Message.RECEIVED_MESSAGES, Message.ALL_MESSAGE_TYPES, true) %>)</a>
<br><% if (mode == Message.SENT_MESSAGES) {out.println("&gt; ");} %>
<a href="message_list.jsp?m=<%= Message.SENT_MESSAGES %>">Sent</a>
<br><% if (mode == Message.DELETED_MESSAGES) {out.println("&gt; ");} %>
<a href="message_list.jsp?m=<%= Message.DELETED_MESSAGES %>">Trash</a>
</b>

<hr>
<% if (messageType == Message.ALL_MESSAGE_TYPES) {out.println("&gt; ");} %>
<a href="message_list.jsp?m=<%= mode %>&t=<%= Message.ALL_MESSAGE_TYPES %>&u=<%= unreadOnly %>">All Communications</a>
<br><% if (messageType == Message.BASIC_MESSAGE) {out.println("&gt; ");} %>
<a href="message_list.jsp?m=<%= mode %>&t=<%= Message.BASIC_MESSAGE %>&u=<%= unreadOnly %>">Messages</a>
<br><% if (messageType == Message.REQUEST_MESSAGE) {out.println("&gt; ");} %>
<a href="message_list.jsp?m=<%= mode %>&t=<%= Message.REQUEST_MESSAGE %>&u=<%= unreadOnly %>">Friend Requests</a>
<br><% if (messageType == Message.CHALLENGE_MESSAGE) {out.println("&gt; ");} %>
<a href="message_list.jsp?m=<%= mode %>&t=<%= Message.CHALLENGE_MESSAGE %>&u=<%= unreadOnly %>">Quiz Challenges</a>

<%
if (mode == Message.RECEIVED_MESSAGES) {
%>
<hr>
<% if (unreadOnly == false) {out.println("&gt; ");} %>
<a href="message_list.jsp?m=<%= mode %>&t=<%= messageType %>&u=false">All</a>
<br><% if (unreadOnly == true) {out.println("&gt; ");} %>
<a href="message_list.jsp?m=<%= mode %>&t=<%= messageType %>&u=true">Unread Only</a>
<%
}
%>

</div>

<div id="middle">

<%
ArrayList<Message> messages = Message.getUserMessages(currentUser.getUserID(), mode, messageType, unreadOnly);
if (messages.size() == 0) {
	out.println("<i>No messages here!</i>");
} else {
	%>
	<table width="75%" border="1" style="border-collapse: collapse;">
	<col width="13%" />
	<col width="13%" />
	<col width="63%" />
	<col width="11%" />
	<tr><th>From</th><th>To</th><th>Subject</th><th>Sent</th></tr>
	<%
	
	SimpleDateFormat df = new SimpleDateFormat("h:mm a");
	
	for (Message m : messages) {

		out.println("<tr>");
			
		User sender = User.getUser(m.getSenderUserID());
		out.println("<td>&nbsp;");
		if (!(sender.isDeleted())) out.println("<a href=\"homepage.jsp?user_id=" + sender.getUserID() + "\">");
		out.println(sender.getUsername());
		if (!(sender.isDeleted())) out.println("</a>");
		out.println("</td>");
		
		if (m.getAdminClassification()) {
			out.println("<td>&nbsp;" + Message.ADMIN_TO + "</td>");
		} else {
			User recipient = User.getUser(m.getRecipientUserID());
			
			out.println("<td>&nbsp;");
			if (!(recipient.isDeleted())) out.println("<a href=\"homepage.jsp?user_id=" + recipient.getUserID() + "\">");
			out.println(recipient.getUsername());
			if (!(recipient.isDeleted())) out.println("</a>");
			out.println("</td>");
		}
	
		if (mode == Message.RECEIVED_MESSAGES && m.getUnreadStatus()) {
			// Show message as unread
			out.println("<td>&nbsp;<a href = \"message_display.jsp?id=" + m.getMessageID() + "\"><b><i>" + m.getSubject() + "</i></b></a></td>");
		} else {
			out.println("<td>&nbsp;<a href = \"message_display.jsp?id=" + m.getMessageID() + "\">" + m.getSubject() + "</a></td>");
		}
		
		out.println("<td>&nbsp;" + df.format(m.getTimeSent()) + "</td>");
		out.println("</tr>");
	}
	%>
	</table>
	<%
}
%>

</div>

<div id="right">
<h3>QuizWithFriends</h3>
<a href="homepage.jsp">Homepage</a>
<br><a href="quizlist.jsp">Play Quizzes</a>
<br><a href="quizhistory.jsp?userID=<%= currentUser.getUserID() %>">Quiz History</a>
<br>&gt; <a href="message_list.jsp?m=<%= mode %>">Communications</a>
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