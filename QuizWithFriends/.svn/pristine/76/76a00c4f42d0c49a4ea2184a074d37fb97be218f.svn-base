<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Collections" %>
<%@ page import="quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/base.css" rel="stylesheet">

<%
final int RECENT_DISPLAY_NUM = 3;

User viewer = (User) session.getAttribute("viewer");
if (viewer.isDeleted()) {
	%>
	<jsp:forward page="LogoutServlet"></jsp:forward>
	<%
}

User user = (User) request.getAttribute("user");
if (user == null) {
	String userIDString = request.getParameter("user_id");
	if (userIDString != null) {
		int userID = Integer.parseInt(userIDString);
		user = User.getUser(userID);
	}
}
if (user == null) {
	user = (User) session.getAttribute("viewer");
	if (user == null) {
		%>
		<jsp:forward page="index.html"></jsp:forward>
		<%		
	}
}

SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat df2 = new SimpleDateFormat("h:mm a");

boolean showHomepage = (viewer.getUserID() == user.getUserID());
%>

<title><% if (showHomepage) {out.println("Homepage");} else {out.println("Profile");} %></title>
</head>
<body>

<div id="top">
<h1><%= user.getUsername() %>'s <% if (showHomepage) {out.println("Homepage");} else {out.println("Profile");} %></h1>
</div>

<div id="middle">

<%
if (!showHomepage && !(viewer.isFriendsWith(user.getUserID()))) {
	%>
	<a href="message_create_request.jsp?u=<%= user.getUserID() %>"><i>Send <%= user.getUsername() %> a friend request</i></a>
	<%
}
%>

<%
if (viewer.getUsername().equals(user.getUsername())) {
	out.println("<h3>Recent Messages:</h3>");
	//Recent Messages
	ArrayList<Message> messages = Message.getUserMessages(user.getUserID(), Message.RECEIVED_MESSAGES,
														  Message.ALL_MESSAGE_TYPES, false);
	
	if (messages.size() == 0) {
		out.println("<p>No recent messages</p>");
	} else {	
		%>
		<table width="75%" border="1" style="border-collapse: collapse;">
		<col width="13%" />
		<col width="13%" />
		<col width="63%" />
		<col width="11%" />
		<tr>
		<th>From</th><th>To</th><th>Subject</th><th>Sent</th>
		</tr>
		<%
		int displayNum = (messages.size() < RECENT_DISPLAY_NUM) ? messages.size() : RECENT_DISPLAY_NUM;
		for (int i = 0; i < displayNum; i++) {
			Message m = messages.get(i);
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
			
			if (m.getUnreadStatus()) {
				// Show message as unread
				out.println("<td>&nbsp;<a href = \"message_display.jsp?id=" + m.getMessageID() + "\"><b><i>" + m.getSubject() + "</i></b></a></td>");
			} else {
				out.println("<td>&nbsp;<a href = \"message_display.jsp?id=" + m.getMessageID() + "\">" + m.getSubject() + "</a></td>");
			}
			
			out.println("<td>&nbsp;" + df2.format(m.getTimeSent()) + "</td>");
			out.println("</tr>");
		}	
		%>
		</table>
		<br>
		<%
	}	
}
%>

<h3>Popular Quizzes:</h3>
<!-- Popular Quizzes Stuff -->
<%
ArrayList<Quiz> quizzesByPopularity = Quiz.getQuizzesSortedByPopularity();

if (quizzesByPopularity.size() == 0) {
	out.println("<p>No Quizzes</p>");
} else {
	int displayNum = (quizzesByPopularity.size() < RECENT_DISPLAY_NUM) ? quizzesByPopularity.size() : RECENT_DISPLAY_NUM;
	for (int i = 0; i < displayNum; i++) {
		Quiz quiz = quizzesByPopularity.get(i);
		
		out.println("<p><a href=\"CreateQuizSummaryServlet?quiz_id=" + quiz.getQuizID() + "\">");
		out.println(quiz.getQuizTitle() + "</a> - ");
		out.println(quiz.getDescription() + "</p>");
	}
}
%>
<br>

<h3>Recently Created Quizzes:</h3>
<!-- Recent Quizzes Stuff -->
<%
ArrayList<Quiz> quizzesByDateCreated = Quiz.getQuizzesSortedByDateCreated();

if (quizzesByDateCreated.size() == 0) {
	out.println("<p>No Quizzes</p>");
} else {
	int displayNum = (quizzesByDateCreated.size() < RECENT_DISPLAY_NUM) ? quizzesByDateCreated.size() : RECENT_DISPLAY_NUM;
	for (int i = 0; i < displayNum; i++) {
		Quiz quiz = quizzesByDateCreated.get(i);
		
		out.println("<p><a href=\"CreateQuizSummaryServlet?quiz_id=" + quiz.getQuizID() + "\">");
		out.println(quiz.getQuizTitle() + "</a> - ");
		out.println(quiz.getDescription() + "</p>");
	}
}
%>
<br>

<h3>
<% out.println(user.getUsername() + "'s Recently Created Quizzes:"); %>
</h3>
<!-- User's Recently Created Quizzes -->
<%
ArrayList<Quiz> userQuizzesByDateCreated = Quiz.getQuizzesTakenByUserSortedByDateTaken(user.getUserID());

if (userQuizzesByDateCreated.size() == 0) {
	out.println("<p>No recently created quizzes</p>");
} else {
	int displayNum = (userQuizzesByDateCreated.size() < RECENT_DISPLAY_NUM) ? userQuizzesByDateCreated.size() : RECENT_DISPLAY_NUM;
	for (int i = 0; i < displayNum; i++) {
		Quiz quiz = userQuizzesByDateCreated.get(i);
		
		out.println("<p><a href=\"CreateQuizSummaryServlet?quiz_id=" + quiz.getQuizID() + "\">");
		out.println(quiz.getQuizTitle() + "</a> - ");
		out.println(quiz.getDescription() + "</p>");
	}
}
%>

<br>

<h3>
<% out.println(user.getUsername() + "'s Recently Taken Quizzes:"); %>
</h3>
<%
ArrayList<QuizResult> qrs = QuizResult.getQuizResultsForUserSortedByDateTaken(user.getUserID());
if (qrs.size() == 0) {
	out.println("<p>No recently taken quizzes</p>");
} else {
	int displayNum = (qrs.size() < RECENT_DISPLAY_NUM) ? qrs.size() : RECENT_DISPLAY_NUM;
	for (int i = 0; i < displayNum; i++) {
		QuizResult qr = qrs.get(i);
		Quiz qz = new Quiz(qr.getQuizID());
		out.println("<p><a href=\"homepage.jsp?user_id=" + qr.getUserID() + "\">" + User.getUser(qr.getUserID()).getUsername() + "</a>");
		out.println(" took the <a href=\"CreateQuizSummaryServlet?quiz_id=" + qz.getQuizID() + "\">");
		out.println(qz.getQuizTitle() + "</a> and scored " + qr.getScore() + " in " + qr.getTimeTaken() + " seconds</p>");
	}
}

%>

<br>

<h3>
<% out.println(user.getUsername() + "'s Recently Earned Achievements:"); %>
</h3>
<!-- User's Recently Earned Achievements -->
<%
ArrayList<CompletedAchievement> completedAchievements = Achievements.getCompletedAchievements(user.getUserID());

if (completedAchievements.size() == 0) {
	out.println("<p>No recently earned achievements</p>");
} else {
	int displayNum = (completedAchievements.size() < RECENT_DISPLAY_NUM) ? completedAchievements.size() : RECENT_DISPLAY_NUM;
	for (int i = 0; i < displayNum; i++) {
		CompletedAchievement completed = completedAchievements.get(i);
		Achievement achievement = completed.getAchievement();
		
		out.println("<p>" + achievement.getName() + " - " + achievement.getDescription() +
					" (" + df1.format(completed.getTimestamp()) + ")</p>");
	}
}
%>

<br>

<h3>
<% out.println(user.getUsername() + "'s Friends' Recent Activities:"); %>
</h3>
<!-- Implement Friends' Recent Activities -->
<%
ArrayList<Integer> friendIDs = user.getFriendships();
ArrayList<Quiz> createdQuizzes = new ArrayList<Quiz>();
ArrayList<QuizResult> takenQuizzes = new ArrayList<QuizResult>();
completedAchievements = new ArrayList<CompletedAchievement>();

for (Integer friendID: friendIDs) {
	User friend = User.getUser(friendID);
	if (friend != null && !friend.isDeleted()) {
		ArrayList<Quiz> friendCreatedQuizzes = Quiz.getRecentQuizzesSortedByDateCreated(friend.getUserID());
		ArrayList<QuizResult> friendTakenQuizzes = QuizResult.getQuizResultsForUserSortedByDateTaken(friend.getUserID());
		ArrayList<CompletedAchievement> friendCompletedAchievements = Achievements.getCompletedAchievements(friend.getUserID());
		
		int createdSize = (friendCreatedQuizzes.size() < RECENT_DISPLAY_NUM) ? friendCreatedQuizzes.size() : RECENT_DISPLAY_NUM;
		int takenSize = (friendTakenQuizzes.size() < RECENT_DISPLAY_NUM) ? friendTakenQuizzes.size() : RECENT_DISPLAY_NUM;
		int completedSize = (friendCompletedAchievements.size() < RECENT_DISPLAY_NUM) ? friendCompletedAchievements.size() : RECENT_DISPLAY_NUM;
		
		for (int i = 0; i < createdSize; i++) {
			createdQuizzes.add(friendCreatedQuizzes.get(i));
		}
		for (int j = 0; j < takenSize; j++) {
			takenQuizzes.add(friendTakenQuizzes.get(j));
		}
		for (int k = 0; k < completedSize; k++) {
			completedAchievements.add(friendCompletedAchievements.get(k));
		}
	}
}

Collections.sort(createdQuizzes, new QuizComparator());
Collections.sort(takenQuizzes, new QuizResultComparator());
Collections.sort(completedAchievements, Collections.reverseOrder());
%>

<h4>Recently Created Quizzes:</h4>

<%
if (createdQuizzes.size() == 0) {
	out.println("<p>No recently created quizzes</p>");
} else {
	int displayNum = (createdQuizzes.size() < RECENT_DISPLAY_NUM) ? createdQuizzes.size() : RECENT_DISPLAY_NUM;
	for (int i = 0; i < displayNum; i++) {
		Quiz quiz = createdQuizzes.get(i);
		
		out.println("<p><a href=\"homepage.jsp?user_id=" + quiz.getCreatorID() + "\">" + User.getUser(quiz.getCreatorID()).getUsername() + "</a>");
		out.println(" created the <a href=\"CreateQuizSummaryServlet?quiz_id=" + quiz.getQuizID() + "\">");
		out.println(quiz.getQuizTitle() + "</a><p>");
	}
}
%>

<h4>Recently Taken Quizzes:</h4>

<%
if (takenQuizzes.size() == 0) {
	out.println("<p>No recently taken quizzes</p>");
} else {
	int displayNum = (takenQuizzes.size() < RECENT_DISPLAY_NUM) ? takenQuizzes.size() : RECENT_DISPLAY_NUM;
	for (int i = 0; i < displayNum; i++) {
		QuizResult qr = takenQuizzes.get(i);
		Quiz qz = new Quiz(qr.getQuizID());
		out.println("<p><a href=\"homepage.jsp?user_id=" + qr.getUserID() + "\">" + User.getUser(qr.getUserID()).getUsername() + "</a>");
		out.println(" took the <a href=\"CreateQuizSummaryServlet?quiz_id=" + qz.getQuizID() + "\">");
		out.println(qz.getQuizTitle() + "</a> and scored " + qr.getScore() + " in " + qr.getTimeTaken() + " seconds</p>");
	}
}
%>

<h4>Recently Earned Achievements:</h4>

<%
if (completedAchievements.size() == 0) {
	out.println("<p>No recently earned achievements</p>");
} else {
	int displayNum = (completedAchievements.size() < RECENT_DISPLAY_NUM) ? completedAchievements.size() : RECENT_DISPLAY_NUM;
	for (int i = 0; i < displayNum; i++) {
		CompletedAchievement completed = completedAchievements.get(i);
		Achievement achievement = completed.getAchievement();
		
		out.println("<p><a href=\"homepage.jsp?user_id=" + completed.getUserID() + "\">" + User.getUser(completed.getUserID()).getUsername() + "</a>");
		out.println(" earned the " + achievement.getName() + " achievement</p>");
	}
}

%>
</div>

<div id="right">
<h3>QuizWithFriends</h3>
<% if (showHomepage) {out.println("&gt; ");} %>
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

<form action="UserSearchServlet" method="get">
Search for User: <input type="text" name="username">
<input type="submit" value="Search">
</form>

<br><form action="QuizSearchServlet" method="get">
Search for Quiz: <input type="text" name="quizName">
<input type="submit" value="Search">
</form>

<%
if (viewer.getUsername().equals(user.getUsername())) {
	out.println("<br><a href =\"CreateQuizServlet\">Create Quiz</a>");
} else {
	if (viewer.isFriendsWith(user.getUserID())) {
		out.println("<br><a href =\"RemoveFriendServlet?removedID=" + user.getUserID() + "\">Remove Friend</a>");
	} else {
		out.println("<br><a href =\"message_create_request.jsp?u=" + user.getUserID() + "\">Send Friend Request</a>");
	}
}
%>

<hr>
<a href="LogoutServlet">Logout</a>

</div>

</body>
</html>