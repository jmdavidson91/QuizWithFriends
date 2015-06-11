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
 * Servlet implementation class CreateScoreMultiplePageQuizServlet
 */
@WebServlet("/CreateScoreMultiplePageQuizServlet")
public class CreateScoreMultiplePageQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateScoreMultiplePageQuizServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id= request.getParameter("quiz_id");
		int intID= Integer.parseInt(id);
		Quiz qz= new Quiz(intID);
		String title= qz.getQuizTitle();
		HttpSession session = request.getSession();
		ArrayList<Question> questionObjectsArr= new ArrayList<Question>();
		questionObjectsArr = qz.getQuestionObjectsArray();
		int[] score= new int[1];
		score = (int []) session.getAttribute("score");
		int totalScore= score[0];
		int maxScore=0;
		for(int j=0; j<questionObjectsArr.size(); j++){
			maxScore= maxScore+ questionObjectsArr.get(j).maxNumCorrect();
		}
		
		double percentCorrect= ((double)totalScore/maxScore) *100;
		
		long timeTaken = (Long) session.getAttribute("time");
		
		User currentUser = (User) session.getAttribute("viewer");
		if (currentUser == null)
			response.sendRedirect("index.html");
		Achievements.updateProgress(currentUser.getUserID(), Achievements.QUIZ_TAKEN_ACTION);
		if(Quiz.isHighestScore(qz.getQuizID(), totalScore)){
			Achievements.updateProgress(currentUser.getUserID(), Achievements.QUIZ_TOP_SCORE_ACTION);
		}
		QuizResult qr= new QuizResult(qz.getQuizID(), currentUser.getUserID(), totalScore, timeTaken);
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<head>");
		out.println("<link href=\"css/base.css\" rel=\"stylesheet\">");
		out.println("<title>Score</title>");
		out.println("</head>");
		out.println("<body>");
		
		out.println("<div id=\"top\">");
		out.println("<h2>Quiz Title: " + title + "</h2>");
		out.println("</div>");
		
		out.println("<div id=\"left\">");
		out.println("</div>");
		
		out.println("<div id=\"middle\">");
		out.println("<h3>Quiz ID: " + id + "</h3>");
		out.println("<h3>Time Taken in Seconds: " + timeTaken + "</h3>");
		out.println("<h3>Score: " + percentCorrect + " % "+ "</h3>");
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
		out.println("</div>");
		out.println("<div class=\"base\">");
		out.println("</div>");
		
		out.println("</body>");
		out.println("</html>");	
	
		session.removeAttribute("score");
		session.removeAttribute("time");
	}
}
