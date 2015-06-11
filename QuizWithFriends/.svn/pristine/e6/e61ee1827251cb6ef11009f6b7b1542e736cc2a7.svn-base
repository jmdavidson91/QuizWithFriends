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
 * Servlet implementation class CreateTakeMultiplePageQuizHelper
 */
@WebServlet("/CreateTakeMultiplePageQuizHelper")
public class CreateTakeMultiplePageQuizHelper extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateTakeMultiplePageQuizHelper() {
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
		HttpSession session = request.getSession();
		int[] ind= new int[1];
		ind = (int []) session.getAttribute("index");
		User currentUser = (User) session.getAttribute("viewer");
		if (currentUser == null)
			response.sendRedirect("index.html");
		ArrayList<Question> questionObjectsArr= new ArrayList<Question>();
		questionObjectsArr = (ArrayList<Question>) session.getAttribute("QUESTIONS");		

		if(qz.getImmediateCorrection() == true){		
			int points;
			ArrayList<Integer> questionScores= new ArrayList<Integer>();
			questionScores= (ArrayList<Integer>) session.getAttribute("qscore");
			points= questionScores.get(ind[0]);

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<head>");
			out.println("<link href=\"css/base.css\" rel=\"stylesheet\">");
			out.println("<title>" + "Feedback"+ "</title>");
			out.println("</head>");
			out.println("<body>");
			
			out.println("<div id=\"top\">");
			out.println("</div>");
			
			out.println("<div id=\"left\">");
			out.println("</div>");
			
			out.println("<div id=\"middle\">");
			out.println("<form action=\"CreateTakeMultiplePageQuizHelper?quiz_id=" +intID+ "\"" + "method=\"post\">");
			out.println("<p>");
			out.println("<h2>Points Received: " + points + "</h2>");
			out.println("<input type=\"submit\" name=\"submitType\" value=\"next\"><br>");
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
		//case where immediate correction is false. this will be you regular multi-page quiz
		else{
			ind[0]= ind[0] +1;
			session.setAttribute("index", ind);
			
			//code for when it's ready to score the quiz
			if(ind[0]== questionObjectsArr.size()){
				long time = (Long) session.getAttribute("time");
				long currTime= System.currentTimeMillis()/1000 ;
				long timeTaken= currTime- time;
				time= timeTaken;
				session.setAttribute("time", time);
				
				session.removeAttribute("QUESTIONS");
				session.removeAttribute("index");
				
				if (currentUser == null)
					response.sendRedirect("index.html");
				
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<!DOCTYPE html>");
				out.println("<head>");
				out.println("<link href=\"css/base.css\" rel=\"stylesheet\">");
				out.println("<title>" + "Quiz Complete"+ "</title>");
				out.println("</head>");
				out.println("<body>");
				
				out.println("<div id=\"top\">");
				out.println("<h2>Quiz Complete! " + "</h2>");
				out.println("</div>");
				
				out.println("<div id=\"left\">");
				out.println("</div>");
				
				out.println("<div id=\"middle\">");
				out.println("<form action=\"CreateScoreMultiplePageQuizServlet?quiz_id=" +intID+ "\"" + "method=\"post\">");
				out.println("<p>");
				out.println("<input type=\"submit\" name=\"submitType\" value=\"score\"><br>");
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
			else{
			response.sendRedirect("CreateTakeImmediateCorrectionServlet?quiz_id=" + qz.getQuizID());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id= request.getParameter("quiz_id");
		int intID= Integer.parseInt(id);
		Quiz qz= new Quiz(intID);	

		HttpSession session = request.getSession();
		int[] ind= new int[1];
		ind = (int []) session.getAttribute("index");

		ArrayList<Question> questionObjectsArr= new ArrayList<Question>();
		questionObjectsArr = (ArrayList<Question>) session.getAttribute("QUESTIONS");		

		ind[0]= ind[0] +1;
		session.setAttribute("index", ind);
		if(ind[0]== questionObjectsArr.size()){	
			long time = (Long) session.getAttribute("time");
			long currTime= System.currentTimeMillis()/1000 ;
			long timeTaken= currTime- time;
			time= timeTaken;
			session.setAttribute("time", time);
			session.removeAttribute("QUESTIONS");
			session.removeAttribute("index");
			
			response.sendRedirect("CreateScoreQuizServlet?quiz_id=" + qz.getQuizID());

		}

		else{
			response.sendRedirect("CreateTakeImmediateCorrectionServlet?quiz_id=" + qz.getQuizID());	
		}
	}

}
