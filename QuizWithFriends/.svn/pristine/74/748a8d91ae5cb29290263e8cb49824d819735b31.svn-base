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
 * Servlet implementation class CreateTakeQuizServlet
 */
@WebServlet("/CreateTakeQuizServlet")
public class CreateTakeQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateTakeQuizServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("viewer");
		if (currentUser == null)
			response.sendRedirect("index.html");
		String id= request.getParameter("quiz_id");
		int intID= Integer.parseInt(id);
		Quiz qz= new Quiz(intID);	

		if(qz.getRandomized()== true){
			qz.randomizeQuestions();
		}
		ArrayList<Question> questionObjectsArr= new ArrayList<Question>();
		questionObjectsArr= qz.getQuestionObjectsArray();
		String title= qz.getQuizTitle();
		
		
		
		//***Case for multiple page quizzes. ***
		if(qz.getMultiplePgs()== true){
			//THIS SERVLET WILL LATER BE RENAMED TO createTakeMultipePageQuiz later
			long time=  System.currentTimeMillis()/1000 ;
			session.setAttribute("time", time);
			response.sendRedirect("CreateTakeImmediateCorrectionServlet?quiz_id=" + qz.getQuizID());
			int[] index= new int[1];
			index[0]= 0;
			session.setAttribute("QUESTIONS", questionObjectsArr);
			session.setAttribute("index", index);
			
			int [] score= new int[1];
			score[0]=0;
			session.setAttribute("score", score);
			
			ArrayList<Integer> questionScores= new ArrayList<Integer>();
			score[0]=0;
			session.setAttribute("qscore", questionScores);
		}
		long time=  System.currentTimeMillis()/1000 ;
		session.setAttribute("time", time);
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<head>");
		out.println("<link href=\"css/base.css\" rel=\"stylesheet\">");
		out.println("<title>" + title+ "</title>");
		out.println("</head>");
		out.println("<body>");
		
		out.println("<div id=\"top\">");
		out.println("<h2>Quiz: " + title + "</h2>");
		out.println("</div>");
		
		out.println("<div id=\"left\">");
		out.println("</div>");
		
		out.println("<div id=\"middle\">");
		out.println("<form action=\"CreateScoreQuizServlet?quiz_id=" +intID+ "\"" + "method=\"post\">");
		out.println("<p>");
	
		//***Case for single page quizzes***
			out.println("<ol>");
			for(int i=0; i< questionObjectsArr.size(); i++){
				out.println("<li>");
				out.println(questionObjectsArr.get(i).getFullQuestionHtml());
				out.println("</li>");
			}		
		out.println("</ol>");
		out.println("<input type=\"submit\" name=\"submitType\" value=\"Score Quiz\"><br>");
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
		
	}

}
