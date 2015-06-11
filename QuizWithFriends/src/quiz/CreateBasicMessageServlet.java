package quiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateBasicMessageServlet
 */
@WebServlet("/CreateBasicMessageServlet")
public class CreateBasicMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateBasicMessageServlet() {
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
		boolean isAdminMsg;
		User recipient;
		
		if (request.getParameter("isAdminMsg") != null) {
			isAdminMsg = true;
			recipient = null;
		} else {
			isAdminMsg = false;
			recipient = User.getUser(request.getParameter("recipientName"));
		}
		
		String subject = request.getParameter("subject");
		String body = request.getParameter("body");
		
		int replyID;
		if (request.getParameter("replyID") != null) {
			replyID = Integer.parseInt(request.getParameter("replyID"));
		} else {
			replyID = 0;
		}
		
		if (isAdminMsg) {
			ArrayList<String> userNames = User.getUsernames();
			boolean firstMessageCreated = false;
			for (String userName : userNames) {
				if (!(userName.equals(sender.getUsername()))) {
					recipient = User.getUser(userName);
					new BasicMessage(sender.getUserID(), recipient.getUserID(), replyID, subject, body, true, firstMessageCreated);
					firstMessageCreated = true;
				}
			}
		} else {
			new BasicMessage(sender.getUserID(), recipient.getUserID(), replyID, subject, body, false, false);
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("message_list.jsp?m=" + Message.SENT_MESSAGES);
		dispatch.forward(request, response);
	}

}
