package quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProcessFriendRequestResponseServlet
 */
@WebServlet("/ProcessFriendRequestResponseServlet")
public class ProcessFriendRequestResponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessFriendRequestResponseServlet() {
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
		RequestMessage requestMessage = new RequestMessage(Integer.parseInt(request.getParameter("messageID")));
		
		String requestResponse = request.getParameter("response");

		User sender = User.getUser(requestMessage.getSenderUserID());
		User recipient = User.getUser(requestMessage.getRecipientUserID());
		
		if (requestResponse.equals("accept")) {
			sender.addFriendship(recipient.getUserID());
			recipient.addFriendship(sender.getUserID());
			
			requestMessage.setResponse(RequestMessage.REQUEST_ACCEPTED);
		} else if (requestResponse.equals("reject")) {
			requestMessage.setResponse(RequestMessage.REQUEST_REJECTED);
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("friendslist.jsp?userID=" + sender.getUserID());
		dispatch.forward(request, response);
	}

}
