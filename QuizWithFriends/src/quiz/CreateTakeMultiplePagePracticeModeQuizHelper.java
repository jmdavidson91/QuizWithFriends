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
 * Servlet implementation class CreateTakeMultiplePagePracticeModeQuizHelper
 */
@WebServlet("/CreateTakeMultiplePagePracticeModeQuizHelper")
public class CreateTakeMultiplePagePracticeModeQuizHelper extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTakeMultiplePagePracticeModeQuizHelper() {
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
		HttpSession session = request.getSession();
		int[] ind= new int[1];
		ind = (int []) session.getAttribute("index");
		User currentUser = (User) session.getAttribute("viewer");
		ArrayList<Question> questionObjectsArr= new ArrayList<Question>();
		questionObjectsArr = (ArrayList<Question>) session.getAttribute("QUESTIONS");	
	
		response.sendRedirect("CreateTakeMultiplePagePracticeModeQuiz?quiz_id=" + qz.getQuizID());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
