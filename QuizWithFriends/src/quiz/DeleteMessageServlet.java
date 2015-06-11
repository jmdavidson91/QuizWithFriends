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
 * Servlet implementation class DeleteMessageServlet
 */
@WebServlet("/DeleteMessageServlet")
public class DeleteMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMessageServlet() {
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
		User currentUser = (User) session.getAttribute("viewer");
		
		Message messageToDelete = Message.getMessage(Integer.parseInt(request.getParameter("messageID")));
		boolean delete = Boolean.parseBoolean(request.getParameter("delete"));
		
		if (messageToDelete.getSenderUserID() == currentUser.getUserID()) {
			messageToDelete.setSenderDeletedStatus(delete);
		} else if (messageToDelete.getRecipientUserID() == currentUser.getUserID()) {
			messageToDelete.setRecipientDeletedStatus(delete);
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("message_list.jsp");
		dispatch.forward(request, response);	
	}

}
