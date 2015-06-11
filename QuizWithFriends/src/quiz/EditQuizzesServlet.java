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
 * Servlet implementation class EditQuizzesServlet
 */
@WebServlet("/EditQuizzesServlet")
public class EditQuizzesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditQuizzesServlet() {
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
		// 
		ArrayList<Quiz> allQuizzes = Quiz.returnAllQuizObjectsStoredInTheDB();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
		out.println("<link href=\"css/base.css\" rel=\"stylesheet\">");
		out.println("<title>Administration - Edit Quizzes</title>");
		out.println("</head>");
		out.println("<body>");

		out.println("<div id=\"top\">");
		out.println("<h1>Administration - Edit Quizzes</h1>");
		out.println("</div>");

		out.println("<div id=\"left\">");

		out.println("</div>");

		out.println("<div id=\"middle\">");
		out.println("<h3>All current quizzes:</h3>");
		out.println("<form action=\"EditQuizzesServlet\" method=\"post\">");
		out.println("<table width=\"50%\" border=\"1\" style=\"border-collapse: collapse;\">");
		out.println("<col width=\"20%\"/>");
		out.println("<col width=\"60%\"/>");
		out.println("<col width=\"20%\"/>");
		out.println("<tr>");
		out.println("<th>");
		out.println("Quiz ID");
		out.println("</th>");
		out.println("<th>");
		out.println("Quiz Name");
		out.println("</th>");
		out.println("<th>");
		out.println("</th>");
		out.println("</tr>");
		for (int i = 0; i < allQuizzes.size(); i++) {
			out.println("<tr>");
			out.println("<td>");
			out.println(allQuizzes.get(i).getQuizID());
			out.println("</td>");
			out.println("<td>");
			out.println(allQuizzes.get(i).getQuizTitle());
			out.println("</td>");
			out.println("<td>");
			out.println("<input type=\"checkbox\" name=\"quizzes\" value=\"" + allQuizzes.get(i).getQuizID() + "\">");
			out.println("</td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("<input type=\"submit\" name=\"submitType\" value=\"Delete History\"><br>");
		out.println("<input type=\"submit\" name=\"submitType\" value=\"Delete Quiz\"><br>");
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
		out.println("<a href=\"administration.jsp\">Admin Home</a>");
		out.println("<br><a href=\"message_create_basic.jsp?a=1\">Create Announcement</a>");
		out.println("<br><a href=\"EditUserServlet\">Edit Users</a>");
		out.println("<br>&gt; <a href=\"EditQuizzesServlet\">Edit Quizzes</a>");
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
		String[] selectedIndices = request.getParameterValues("quizzes");
		
		if (selectedIndices != null) {
			for (int i = 0; i < selectedIndices.length; i++) {
				int quizIndex = Integer.valueOf(selectedIndices[i]);
				if (request.getParameter("submitType").equals("Delete History")) {
					Quiz.deleteAllQuizResultsForQuizID(quizIndex);
				} else {
					Quiz.removeQuizFromDB(quizIndex);
				}
			}
		}

		response.sendRedirect("EditQuizzesServlet");	
	}

}
