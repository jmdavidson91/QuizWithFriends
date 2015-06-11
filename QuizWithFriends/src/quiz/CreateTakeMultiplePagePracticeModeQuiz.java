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
 * Servlet implementation class CreateTakeMultiplePagePracticeModeQuiz
 */
@WebServlet("/CreateTakeMultiplePagePracticeModeQuiz")
public class CreateTakeMultiplePagePracticeModeQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateTakeMultiplePagePracticeModeQuiz() {
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
		out.println("<form action=\"CreateTakeMultiplePagePracticeModeQuiz?quiz_id=" +intID+ "\"" + "method=\"post\">");
		out.println("<p>");

		if(questionObjectsArr.get(ind[0]) != null){
			out.println("Question " + questionNumber + questionObjectsArr.get(ind[0]).getFullQuestionHtml());	
		}
		else{
			out.println("Question " + questionNumber + " has been mastered!");	
		}

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

		ArrayList<Integer> counter = new ArrayList<Integer>();
		counter = (ArrayList<Integer>) session.getAttribute("counter");
		if(questionObjectsArr.get(ind[0])!= null){
			if(questionObjectsArr.get(ind[0]).checkCorrectSubmission(request) == questionObjectsArr.get(ind[0]).maxNumCorrect()) {
				int val= counter.get(ind[0]);
				val= val+1;
				counter.set(ind[0], val);
				session.setAttribute("counter", counter);

				if(counter.get(ind[0])==3){
					counter.set(ind[0], 0);
					questionObjectsArr.set(ind[0], null);
					session.setAttribute("QUESTIONS", questionObjectsArr);
				}
			}
		}

		int nullCounter=0;
		for(int j=0; j<questionObjectsArr.size(); j++){
			if(questionObjectsArr.get(j)== null){
				nullCounter++;
			}
		}

		ind[0]= ind[0] +1;
		session.setAttribute("index", ind);

		if(ind[0]== questionObjectsArr.size()){
			ind[0]=0;
		}

		if(questionObjectsArr.size()== nullCounter){
			session.removeAttribute("index");
			response.sendRedirect("CreatePracticeModeCompleteServlet");	
		}

		//redirects 
		else {
			response.sendRedirect("CreateTakeMultiplePagePracticeModeQuizHelper?quiz_id=" + qz.getQuizID());	
		}


	}

}
