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
 * Servlet implementation class EditUserServlet
 */
@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		
		ArrayList<User> allUsers = User.getUsers();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
		out.println("<link href=\"css/base.css\" rel=\"stylesheet\">");
		out.println("<title>Administration - Edit Users</title>");
		out.println("</head>");
		out.println("<body>");

		out.println("<div id=\"top\">");
		out.println("<h1>Administration - Edit User</h1>");
		out.println("</div>");

		out.println("<div id=\"left\">");

		out.println("</div>");

		out.println("<div id=\"middle\">");
		out.println("<h3>All current users:</h3>");
		out.println("<form action=\"EditUserServlet\" method=\"post\">");
		out.println("<table width=\"60%\" border=\"1\" style=\"border-collapse: collapse;\">");
		out.println("<col width=\"20%\"/>");
		out.println("<col width=\"35%\"/>");
		out.println("<col width=\"25%\"/>");
		out.println("<col width=\"20%\"/>");
		out.println("<tr>");
		out.println("<th>");
		out.println("User ID");
		out.println("</th>");
		out.println("<th>");
		out.println("User Name");
		out.println("</th>");
		out.println("<th>");
		out.println("Admin");
		out.println("</th>");
		out.println("<th>");
		out.println("</th>");
		out.println("</tr>");
		for (int i = 0; i < allUsers.size(); i++) {
			out.println("<tr>");
			out.println("<td>");
			out.println(allUsers.get(i).getUserID());
			out.println("</td>");
			out.println("<td>");
			out.println(allUsers.get(i).getUsername());
			out.println("</td>");
			out.println("<td>");
			if (allUsers.get(i).isAdministrator())
				out.println("x");
			else
				out.println("");
			out.println("</td>");
			out.println("<td>");
			out.println("<input type=\"checkbox\" name=\"users\" value=\"" + allUsers.get(i).getUserID() + "\">");
			out.println("</td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("<input type=\"submit\" name=\"submitType\" value=\"Delete\"><br>");
		out.println("<input type=\"submit\" name=\"submitType\" value=\"Promote\"><br>");
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
		out.println("<br>&gt; <a href=\"EditUserServlet\">Edit Users</a></li>");
		out.println("<br><a href=\"EditQuizzesServlet\">Edit Quizzes</a>");
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
		String[] selectedIndices = request.getParameterValues("users");
		
		if (selectedIndices != null) {
			for (int i = 0; i < selectedIndices.length; i++) {
				int userIndex = Integer.valueOf(selectedIndices[i]);
				if (request.getParameter("submitType").equals("Promote")) {
					User.makeAdministrator(userIndex);
				} else {
					User.deleteUser(userIndex);
				}
			}			
		}

		response.sendRedirect("EditUserServlet");		
	}

}
