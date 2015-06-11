package quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateChallengeMessageServlet
 */
@WebServlet("/CreateChallengeMessageServlet")
public class CreateChallengeMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateChallengeMessageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// GET method not used
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User sender = (User) session.getAttribute("viewer");
		User recipient = User.getUser(request.getParameter("recipientName"));
		
		int quizID = Integer.parseInt(request.getParameter("quizID"));
		Quiz quiz = new Quiz(quizID);
		String body = request.getParameter("body");
		
		new ChallengeMessage(sender.getUserID(), recipient.getUserID(), 0, body, quiz);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("message_list.jsp?m=" + Message.SENT_MESSAGES);
		dispatch.forward(request, response);
	}

}
