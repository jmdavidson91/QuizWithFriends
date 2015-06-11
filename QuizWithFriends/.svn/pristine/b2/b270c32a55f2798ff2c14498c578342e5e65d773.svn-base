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
 * Servlet implementation class CreateQuizServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "title_attr";
	private static final String DESC = "description_attr";
	private static final String RAND = "randomized_attr";
	private static final String MULT = "multiple_attr";
	private static final String PRACT = "practice_attr";
	private static final String CORR = "correction_attr";
	private static final String QUESTS = "questions_attr";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuizServlet() {
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
		
		String title = "";
		String description = "";
		boolean randomized =  false;
		boolean multiple = false;
		boolean practice = false;
		boolean correction = false;
		ArrayList<Question> questions;
		
		if (request.getParameter("quiz_id") != null) {
			int quizID = Integer.valueOf(request.getParameter("quiz_id"));
			//System.out.println("quiz id = " + quizID);
			Quiz quiz = new Quiz(quizID);
			title = quiz.getQuizTitle();
			description = quiz.getDescription();
			randomized = quiz.getRandomized();
			multiple = quiz.getMultiplePgs();
			practice = quiz.getPracticeMode(); 
			correction = quiz.getImmediateCorrection();
			questions = quiz.getQuestionObjectsArray();
			session.setAttribute(QUESTS, questions);
		} else {
			title = (String) session.getAttribute(TITLE);
			if (title == null) title = "";
			description = (String) session.getAttribute(DESC);
			if (description == null) description = "";
			randomized = false;
			if (session.getAttribute(RAND) != null)
				randomized = (Boolean) session.getAttribute(RAND);	
			
			multiple = false;
			if (session.getAttribute(MULT)!= null)
				multiple = (Boolean) session.getAttribute(MULT);	

			practice = false;
			if (session.getAttribute(PRACT) != null)
				practice = (Boolean) session.getAttribute(PRACT);
			
			correction = false;
			if (session.getAttribute(CORR) != null)
				correction = (Boolean) session.getAttribute(CORR);
			
			questions = (ArrayList<Question>) session.getAttribute(QUESTS);
			if (questions == null) {
				questions = new ArrayList<Question>();
				session.setAttribute(QUESTS, questions);
			}
		}
		
	
		out.println("<!DOCTYPE html>");
		out.println("<head>");		
		out.println("<link href=\"css/base.css\" rel=\"stylesheet\">");
		out.println("<title>Quiz Creation</title>");
		out.println("</head>");
		out.println("<body>");
		
		out.println("<div id=\"top\">");
		out.println("<h2>Create your quiz</h2>");
		out.println("</div>");
		
		out.println("<div id=\"left\">");
		out.println("</div>");
		
		out.println("<div id=\"middle\">");
		out.println("<form action=\"CreateQuizServlet\" method=\"post\">");
		out.println("<p>Please fill out all the fields below and add questions to create the quiz</p>");
		out.println("<p>Enter the title for your quiz<br>");
		out.println("<input type=\"text\" size=\"18\" name=\"title\" value=\"" + title + "\"/></p>");
		out.println("<p>Enter the description for your quiz<br>");
		out.println("<textarea cols=\"40\" rows=\"5\" name=\"description\">" + description + "</textarea></p>");
		out.println("<p>Want questions to be randomized each time?<br>");
		if (randomized)
			out.println("<input type=\"checkbox\" name=\"random\" value=\"randomized\" checked>Randomized<br>");
		else
			out.println("<input type=\"checkbox\" name=\"random\" value=\"randomized\">Randomized</p>");
		out.println("<p>Want questions to be shown all on one page or multiple pages?<br>");
		if (multiple) {
			out.println("<input type=\"radio\" name=\"pagedecision\" value=\"onepage\">One page<br>");
			out.println("<input type=\"radio\" name=\"pagedecision\" value=\"multiple\" checked>Multiple pages</p>");
		} else {
			out.println("<input type=\"radio\" name=\"pagedecision\" value=\"onepage\" checked>One page<br>");
			out.println("<input type=\"radio\" name=\"pagedecision\" value=\"multiple\">Multiple pages</p>");
		}
		out.println("<p>Allow practice mode?<br>");
		if (practice)
			out.println("<input type=\"checkbox\" name=\"practice\" value=\"enabled\" checked>Practice mode allowed</p>");
		else
			out.println("<input type=\"checkbox\" name=\"practice\" value=\"enabled\">Practice mode allowed</p>");
		out.println("<p>Force immediate correction?<br>");
		if (correction)
			out.println("<input type=\"checkbox\" name=\"immcorrection\" value=\"enabled\" checked>Immediate correction</p>");
		else 
			out.println("<input type=\"checkbox\" name=\"immcorrection\" value=\"enabled\">Immediate correction</p>");

		out.println("<p>Question list:<br>");
		if (questions.size() == 0)
			out.println("No questions added yet! Add them below:<br>");
		else {
			for (int i = 0; i < questions.size(); i++) {
				out.println((i+1) + ": A " + questions.get(i).getType() + " question ");
				out.println("<input type=\"submit\" name=\"delete" + i + "\" value=\"X\"><br>");
			}
		}
		out.println("</p><p>Choose a question type and click add:<br>");
		out.println("<select name=\"questiontype\">");
		String[] types = Question.allQuestionTypes();
		for (int i = 0; i < types.length; i++)
			out.println("<option value=\"" + types[i] + "\">" + types[i] + "</option>");
		out.println("</select>");
		out.println("<input type=\"submit\" name=\"submitType\" value=\"Create Question\"><br>");
		out.println("</p>");
		
		out.println("<input type=\"submit\" name=\"submitType\" value=\"Finished with Quiz\"><br>");
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
		String title = (String) request.getParameter("title");	
		String desc = (String) request.getParameter("description");
		boolean randomized = (request.getParameter("random") != null);	
		boolean multiple = (request.getParameter("pagedecision").equals("multiple"));
		boolean practice = (request.getParameter("practice") != null);		
		boolean correction = (request.getParameter("immcorrection") != null);	
		
		session.setAttribute(TITLE, title);
		session.setAttribute(DESC, desc);
		session.setAttribute(RAND, randomized);
		session.setAttribute(MULT, multiple);
		session.setAttribute(PRACT, practice);
		session.setAttribute(CORR, correction);
		
		ArrayList<Question> quests = (ArrayList<Question>) session.getAttribute(QUESTS);
		//System.out.println("quest size = " + quests.size());
		boolean deleteCheck = false;
		for (int i = 0; i < quests.size(); i++) {
			if (request.getParameter("delete" + i) != null) {
				//System.out.println("deleting question = " + i);
				quests.remove(i);
				session.setAttribute(QUESTS, quests);
				deleteCheck = true;
				break;
			}
		}
		if (!deleteCheck) {
			if (request.getParameter("submitType").equals("Create Question")) {

				String typeToCreate = (String) request.getParameter("questiontype");
				session.setAttribute("questiontype", typeToCreate);
				response.sendRedirect("CreateQuestionServlet");			
			} else {

				session.removeAttribute(QUESTS);
				session.removeAttribute(TITLE);
				session.removeAttribute(DESC);
				session.removeAttribute(RAND);
				session.removeAttribute(MULT);
				session.removeAttribute(PRACT);
				session.removeAttribute(CORR);
				
				User currentUser = (User) session.getAttribute("viewer");
				Achievements.updateProgress(currentUser.getUserID(), Achievements.QUIZ_CREATION_ACTION);
				
				Quiz qz = new Quiz(currentUser.getUserID(), quests, title, desc, randomized, multiple, practice, correction);
				
				response.sendRedirect("CreateQuizSummaryServlet?quiz_id=" + qz.getQuizID());
			}
		} else 
			response.sendRedirect("CreateQuizServlet");		
	}

}
