package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class QuizSummaryServlet
 */
@WebServlet("/CreateQuizSummaryServlet")
public class CreateQuizSummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MAX_LIST = 5;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateQuizSummaryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id= request.getParameter("quiz_id");
		int intID= Integer.parseInt(id);
		Quiz qz= new Quiz(intID);
		String title= qz.getQuizTitle();
		String description= qz.getDescription();
		
		int creatorID= qz.getCreatorID();
		User creator = User.getUser(creatorID);
		
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("viewer");
		if (currentUser == null)
			response.sendRedirect("index.html");	
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<head>");
		out.println("<link href=\"css/base.css\" rel=\"stylesheet\">");
		out.println("<title>Quiz Summary</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div id=\"top\">");
		out.println("<h2>Quiz: " + title + "</h2>");
		if (!creator.isDeleted())
			out.println("<h3>by <a href=\"homepage.jsp?user_id=" + creator.getUserID() + "\">" + creator.getUsername() + "</a></h3>");
		else
			out.println("<h3>by " + creator.getUsername() + "</h3>");
		out.println("</div>");

		out.println("<div id=\"middle\">");
		out.println("<p><b>Quiz Description:</b> " + description + "</p>");
		
		out.println("<h3>Stats for this quiz</h3>");		
		out.println("Total times taken: " + QuizResult.getTotalResultsForQuiz(qz.getQuizID()));
		out.println("<br>Average score: " + QuizResult.getAvgScoreForQuiz(qz.getQuizID()));
		out.println("<br>Highest score: " + QuizResult.getMaxScoreForQuiz(qz.getQuizID()));
		out.println("<br>Lowest score: " + QuizResult.getMinScoreForQuiz(qz.getQuizID()));
		
		out.println("<h3>Your recent performance:</h3>");
		ArrayList<QuizResult> results = QuizResult.getQuizResultsSortedByDateTaken(currentUser.getUserID(), qz.getQuizID());
		if (results.size() == 0)
			out.println("You have to take the quiz to get results!<br>");
		else {
			for (int i = 0; i < results.size() && i < MAX_LIST; i++)
				out.println((i+1) + ". Scored " + results.get(i).getScore() + " points in " + results.get(i).getTimeTaken() + " seconds<br>");
		}

		out.println("<h3>All time high scores</h3>");
		results = QuizResult.getAllQuizResultsSortedByHighScore(qz.getQuizID());
		if (results.size() == 0)
			out.println("Nodody has taken the quiz yet<br>");
		else {
			for (int i = 0; i < results.size() && i < MAX_LIST; i++) {
				User user = User.getUser(results.get(i).getUserID());
				//System.out.println("result user id = " + results.get(i).getUserID());
				out.println((i+1) + ". " + user.getUsername() + " scored " + results.get(i).getScore() + " points in " + results.get(i).getTimeTaken() + " seconds<br>");
			}
		}
		
		out.println("<h3>Recent high scores:</h3>");
		results = QuizResult.getQuizResultsForLastDaySortedByScore(qz.getQuizID());
		if (results.size() == 0)
			out.println("Nodody has taken the quiz recently<br>");
		else {
			for (int i = 0; i < results.size() && i < MAX_LIST; i++) {
				User user = User.getUser(results.get(i).getUserID());
				out.println((i+1) + ". " + user.getUsername() + " scored " + results.get(i).getScore() + " points in " + results.get(i).getTimeTaken() + " seconds<br>");
			}
		}
		
		out.println("<h3>All recent scores:</h3>");
		results = QuizResult.getQuizResultsForLastDay(qz.getQuizID());
		if (results.size() == 0)
			out.println("Nodody has taken the quiz recently<br>");
		else {
			for (int i = 0; i < results.size() && i < MAX_LIST; i++) {
				User user = User.getUser(results.get(i).getUserID());
				out.println((i+1) + ". " + user.getUsername() + " scored " + results.get(i).getScore() + " points in " + results.get(i).getTimeTaken() + " seconds<br>");
			}
		}
		
		out.println("<form action=\"CreateQuizSummaryServlet?quiz_id=" +intID+ "\"" + "method=\"post\">");
		out.println("<p>");
		out.println("<input type=\"submit\" name=\"submitType\" value=\"Take Quiz\"><br>");
		if(qz.getPracticeMode()==true){
			out.println("<input type=\"submit\" name=\"submitType\" value=\"Practice Mode\"><br>");
		}
		out.println("</p>");
		out.println("</form>");
		out.println("</div>");

		out.println("<div id=\"right\">");
		out.println("<h3>QuizWithFriends</h3>");
		out.println("<a href=\"homepage.jsp\">Homepage</a>");
		out.println("<br><a href=\"quizlist.jsp\">Play Quizzes</a>");
		out.println("<br><a href=\"quizhistory.jsp?userID=" + currentUser.getUserID() + "\">Quiz History</a>");
		out.println("<br><a href=\"message_list.jsp?m=" + Message.RECEIVED_MESSAGES + "\">Inbox ");
		out.println("(" + Message.getUserMessageCount(currentUser.getUserID(), Message.RECEIVED_MESSAGES, Message.ALL_MESSAGE_TYPES, true) + ")</a>");
		out.println("| <a href=\"message_list.jsp?m=" + Message.SENT_MESSAGES + "\">Sent</a>");
		out.println("| <a href=\"message_list.jsp?m=" + Message.DELETED_MESSAGES + "\">Trash</a>");
		out.println("<br><a href=\"achievementslist.jsp?userID=" + currentUser.getUserID() + "\">Achievements</a>");
		out.println("<br><a href=\"friendslist.jsp?userID=" + currentUser.getUserID() + "\">Friends</a>");
		if (currentUser.isAdministrator()) {
			out.println("<br><a href=\"administration.jsp\">Administration</a>");
		}
		out.println("<hr>");
		out.println("<a href=\"LogoutServlet\">Logout</a>");
		out.println("</div>");
		out.println("<div class=\"base\">");
		out.println("</div>");

		out.println("</body>");
		out.println("</html>");		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id= request.getParameter("quiz_id");
		int intID= Integer.parseInt(id);
		Quiz qz= new Quiz(intID);		
		//redirects to one of two servlets
		
		//redirects to CreateTakeQuizServlet
		if (request.getParameter("submitType").equals("Take Quiz")) {
			response.sendRedirect("CreateTakeQuizServlet?quiz_id=" + qz.getQuizID());	
		}
		
		//redirects to practiceModeServlet if "Practice Mode" Button is clicked
		else{
			response.sendRedirect("CreatePracticeModeServlet?quiz_id=" + qz.getQuizID());			
		}
	}

}
