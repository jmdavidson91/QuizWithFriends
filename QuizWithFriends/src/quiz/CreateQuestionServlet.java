package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateQuestionServlet
 */
@WebServlet("/CreateQuestionServlet")
public class CreateQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("viewer");
		if (currentUser == null)
			response.sendRedirect("index.html");	
		
		String type = (String) session.getAttribute("questiontype");
		
		out.println("<!DOCTYPE html>");
		out.println("<head>");
		out.println("<link href=\"css/base.css\" rel=\"stylesheet\">");
		out.println("<title>Question Creation</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div id=\"top\">");
		out.println("<h2>Create your question</h2>");
		out.println("</div>");
		
		out.println("<div id=\"left\">");
		out.println("</div>");
		
		out.println("<div id=\"middle\">");
		out.println(Question.getCreationHtml(type));
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
		HttpSession session = request.getSession();
		String type = (String) session.getAttribute("questiontype");
		ArrayList<Question> quests = (ArrayList<Question>) session.getAttribute("questions_attr");
		
		Question newGuy = new Question(request, type);
		quests.add(newGuy);
		session.setAttribute("questions_attr", quests);
		
		response.sendRedirect("CreateQuizServlet");
	}

}
