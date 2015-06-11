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
 * Servlet implementation class CreateTakeImmediateCorrectionServlet
 */
@WebServlet("/CreateTakeImmediateCorrectionServlet")
public class CreateTakeImmediateCorrectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTakeImmediateCorrectionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    //RENAME TO createTakeMultipePageQuiz
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// *****IGNORE NAME OF SERVLET IT WILL LATER BE CALLED createTakeMultipePageQuiz *****
		String id= request.getParameter("quiz_id");
		int intID= Integer.parseInt(id);
		Quiz qz= new Quiz(intID);	
		int[] ind= new int[1];
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("viewer");
		if (currentUser == null)
			response.sendRedirect("index.html");
		ind = (int []) session.getAttribute("index");
		ArrayList<Question> questionObjectsArr= new ArrayList<Question>();
		questionObjectsArr = (ArrayList<Question>) session.getAttribute("QUESTIONS");
		String title= qz.getQuizTitle();
		int questionNumber= ind[0]+1;
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<head>");
		out.println("<link href=\"css/base.css\" rel=\"stylesheet\">");
		out.println("<title>" + title+ "</title>");
		out.println("</head>");
		out.println("<body>");
		
		out.println("<div id=\"top\">");
		out.println("</div>");
		
		out.println("<div id=\"left\">");
		out.println("</div>");
		
		out.println("<div id=\"middle\">");
		out.println("<form action=\"CreateTakeImmediateCorrectionServlet?quiz_id=" +intID+ "\"" + "method=\"post\">");
		out.println("<p>");
	
		out.println("Question " +questionNumber + questionObjectsArr.get(ind[0]).getFullQuestionHtml());	
			
		out.println("<input type=\"submit\" name=\"submitType\" value=\"continue\"><br>");
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
		HttpSession session = request.getSession();
		String id= request.getParameter("quiz_id");
		int intID= Integer.parseInt(id);
		Quiz qz= new Quiz(intID);
		int[] ind= new int[1];
		ind = (int []) session.getAttribute("index");
		ArrayList<Question> questionObjectsArr= new ArrayList<Question>();
		questionObjectsArr = (ArrayList<Question>) session.getAttribute("QUESTIONS");
		ArrayList<Integer> questionScores= new ArrayList<Integer>();
		
		questionScores= (ArrayList<Integer>) session.getAttribute("qscore");
		questionScores.add(questionObjectsArr.get(ind[0]).checkCorrectSubmission(request));
		int[] score= new int[1];
		score = (int []) session.getAttribute("score");
		score[0]= score[0] + questionObjectsArr.get(ind[0]).checkCorrectSubmission(request);
		
		session.setAttribute("score", score);
		
		//redirects 
		if (request.getParameter("submitType").equals("continue")) {
			response.sendRedirect("CreateTakeMultiplePageQuizHelper?quiz_id=" + qz.getQuizID());	
		}
		
	}

}
