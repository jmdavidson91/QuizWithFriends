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
 * Servlet implementation class CreateContinuePracticeModeServlet
 */
@WebServlet("/CreateContinuePracticeModeServlet")
public class CreateContinuePracticeModeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateContinuePracticeModeServlet() {
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

		String title = qz.getQuizTitle();
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("viewer");
		if (currentUser == null)
			response.sendRedirect("index.html");

		ArrayList<Question> questionObjectsArr= new ArrayList<Question>();
		questionObjectsArr = (ArrayList<Question>) session.getAttribute("QUESTIONS");
		ArrayList<Integer> counter = new ArrayList<Integer>();
		counter = (ArrayList<Integer>) session.getAttribute("counter");


		for(int i=0; i<questionObjectsArr.size(); i++){
			if(questionObjectsArr.get(i)!= null){
				if(questionObjectsArr.get(i).checkCorrectSubmission(request) == questionObjectsArr.get(i).maxNumCorrect() ){
					int val= counter.get(i);
					val= val+1;
					counter.set(i, val);
					session.setAttribute("counter", counter);
				}
			}
		}

		for(int j=0; j<counter.size(); j++){
			if(counter.get(j)==3){
				counter.set(j, 0);
				questionObjectsArr.set(j, null);
				session.setAttribute("QUESTIONS", questionObjectsArr);
			}
		}

		int nullCounter=0;
		for(int j=0; j<questionObjectsArr.size(); j++){
			if(questionObjectsArr.get(j)== null){
				nullCounter++;
			}
		}	
		if(questionObjectsArr.size()== nullCounter){
			response.sendRedirect("CreatePracticeModeCompleteServlet");	
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<head>");
		out.println("<link href=\"css/base.css\" rel=\"stylesheet\">");
		out.println("<title>Practice Mode</title>");
		out.println("</head>");
		out.println("<body>");

		out.println("<div id=\"top\">");
		out.println("<h2>You still have a few more questions to master. Continue With Practice Mode? </h2>");
		out.println("</div>");

		out.println("<div id=\"left\">");
		out.println("</div>");

		out.println("<div id=\"middle\">");

		out.println("<form action=\"CreatePracticeModeServlet?quiz_id=" +intID+ "\"" + "method=\"post\">");
		out.println("<p>");
		out.println("<input type=\"submit\" name=\"submitType\" value=\"continue\"><br>");
		out.println("</p>");
		out.println("</form>");

		out.println("<form action=\"homepage.jsp");
		out.println("<p>");
		out.println("<input type=\"submit\" name=\"submitType\" value=\"homepage\"><br>");
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

}
