package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;


public class MultipleResponseQuestion extends Question {
	private final static String TABLE_NAME = "quest_multiple_response";
	private final static String INDEX_COL = "id";
	private final static String QUEST_COL = "question";
	private final static String ANSWERS_COL = "answers";
	private final static String ORDERED_COL = "ordered";
	private final static String MATCH_NUM_COL = "number_correct";
	private final static String TYPE_STRING = "Multi Answer";
	
	private final static String INP_TAG = "input";
	
	private String question;
	private String[] answers;
	private boolean ordered;
	private int minNumCorrect;
	
	// will initialize the question fields from the database given a question index
	public MultipleResponseQuestion(int index) {
		String sql = "SELECT " 
				+ Question.MAIN_QUESTION_INDEX_COL  
				+ " FROM " 
				+ Question.MAIN_DB  
				+ " WHERE " 
				+ Question.MAIN_INDEX_COL 
				+ "=" + index;
		try {
			ResultSet results = DBConnection.getStatement().executeQuery(sql);
			if (results.first()) {
				int intermediateIndex = results.getInt(1);
				sql = "SELECT " 
							+ QUEST_COL 
							+ ","  
							+ ANSWERS_COL 
							+ "," 
							+ ORDERED_COL 
							+ "," 
							+ MATCH_NUM_COL + 
							" FROM " 
							+ TABLE_NAME 
							+ " WHERE " 
							+ INDEX_COL 
							+ "=" + intermediateIndex;
	
				results = DBConnection.getStatement().executeQuery(sql);
				if (results.first()) {
					question = results.getString(1);
					answers = results.getString(2).split(",");
					ordered = results.getBoolean(3);
					minNumCorrect = results.getInt(4);
					this.index = index;
				}
			}
			this.type = TYPE_STRING;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public MultipleResponseQuestion(HttpServletRequest request) {
		String question = request.getParameter("question");
		String answers = request.getParameter("answers");
		boolean ordered = (request.getParameter("ordered") != null);
		int minNumCorrect = Integer.valueOf(request.getParameter("number_correct"));
		createMultipleResponseQuestion(question, answers, ordered, minNumCorrect);	
	}

	// will create a new question and save it to the database, all fields will be initialized (including index)
	public void createMultipleResponseQuestion(String question, String answers, boolean ordered, int minNumCorrect) {

		
		String sql = "INSERT INTO " 
				+ TABLE_NAME 
				+ "(" 
				+ QUEST_COL 
				+ "," 
				+ ANSWERS_COL 
				+ "," 
				+ ORDERED_COL
				+ "," 
				+ MATCH_NUM_COL 
				+ ") VALUES (\'" 
				+ question 
				+ "\', \'" 
				+ answers 
				+ "\', \'"
				+ (ordered? 1 : 0)
				+ "\', \'"
				+ minNumCorrect + "\')";
		try {
			DBConnection.getStatement().executeUpdate(sql);
			sql = "SELECT " 
				+ INDEX_COL		
				+ " FROM " 
				+ TABLE_NAME 
				+ " WHERE ("
				+ QUEST_COL 
				+ "=\'" 
				+ question 
				+ "\' AND " 
				+ ANSWERS_COL 
				+ "=\'" 
				+ answers
				+ "\' AND " 
				+ ORDERED_COL 
				+ "=\'" 
				+ (ordered? 1 : 0)
				+ "\' AND " 
				+ MATCH_NUM_COL 
				+ "=" 
				+ minNumCorrect + ")";
			ResultSet result = DBConnection.getStatement().executeQuery(sql);
			if (result.first()) {
				int intermediateIndex = result.getInt(1);
				
				this.type = TYPE_STRING;
				this.question = question;
				this.answers = answers.split(",");
				this.ordered = ordered;
				this.minNumCorrect = minNumCorrect;
				sql = "INSERT INTO " 
						+ Question.MAIN_DB 
						+ "(" 
						+ Question.MAIN_TYPE_COL 
						+ "," 
						+ Question.MAIN_QUESTION_INDEX_COL 
						+ ") VALUES(\'"
						+ TYPE_STRING 
						+ "\', " 
						+ intermediateIndex + ")";
				DBConnection.getStatement().executeUpdate(sql);
				
				sql = "SELECT " 
						+ Question.MAIN_INDEX_COL  
						+ " FROM " 
						+ Question.MAIN_DB  
						+ " WHERE " 
						+ "("
						+ Question.MAIN_QUESTION_INDEX_COL 
						+ "=" + intermediateIndex
						+ " AND " 
						+ Question.MAIN_TYPE_COL 
						+ "=\'" 
						+ TYPE_STRING
						+ "\');";
				 result = DBConnection.getStatement().executeQuery(sql);
				if (result.first()) 
					this.index = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// returns the html to insert in between <form> tags NOTE: must use your own form tags and submission button
	public String getFullQuestionHtml() {
		String html = "<p>List out all the answers to the question";
		if (ordered)
			html = html + " in order</p>";
		else
			html = html + "</p>";
		html = html + "<p>" + question + "</p>";;
		for (int i = 0; i < minNumCorrect; i++) 
			html = html + (i+1) + ". <input type=\"text\" name=\"" + INP_TAG + index + "-" + i + "\" value=\"\"><br>";
		
		return html;
	}

	public int maxNumCorrect() {
		return minNumCorrect;
	}
	
	// checks the appropriate parameters within a request object to see if the question was answered correctly
	public int checkCorrectSubmission(HttpServletRequest request) {
		int correct = 0;
		//System.out.println("min number correct " + minNumCorrect);
		if (ordered) {
			for(int i = 0; i < answers.length; i++) {
				//System.out.println("about to match " + answers[i] + " to " + request.getParameter(INP_TAG + index + "-" + i));
				if (answers[i].equals(request.getParameter(INP_TAG + index + "-" + i))) {
					correct = correct + 1;
					//System.out.println("Matched " + answers[i] + " to " + request.getParameter(INP_TAG + index + "-" + i));
				} else
					break;
			}
		} else {
			int answerCount = 0;
			ArrayList<String> answerTracker = new ArrayList<String>();			
			for (int i = 0; i < answers.length; i++) 
				answerTracker.add(answers[i]);
				
			for (int i = 0; i < minNumCorrect; i++) {
				String ans = request.getParameter(INP_TAG + index + "-" + i);
				for(int j = 0; j < answers.length && j < answerTracker.size(); j++)
					if (answerTracker.get(j).equals(ans)) {
						correct = correct + 1;
						//System.out.println("Matched " + ans + " to " + answerTracker.get(j));
						answerTracker.remove(j);
						break;
					}
			}				
		}
		numCorrect = correct;
		//System.out.println("Multiple response Number correct = " + numCorrect);
		return correct;	
	}
	
	// static creation html function
	public static String getCreationHtml() {
		String html = "<form action=\"CreateQuestionServlet\" method=\"post\">"; 
		html = html + "<p>Fill out the fields below</p>";
		html = html + "<p>Enter your question<br>";
		html = html + "<textarea cols=\"40\" rows=\"5\" name=\"question\"></textarea></p>";
		html = html + "<p>Enter your answers, separated by the ',' character<br>";
		html = html + "<textarea cols=\"40\" rows=\"5\" name=\"answers\"></textarea></p>";
		html = html + "<p>Check the box if you require the responses to be in the order you specificed<br>";
		html = html + "<input type=\"checkbox\" name=\"ordered\" value=\"ordered\">Ordered</p>";
		html = html + "<p>Enter the number of answers that must be entered<br>";
		html = html + "<input type=\"text\" size=\"5\" name=\"number_correct\"/></p>";
		html = html + "<input type=\"submit\" value=\"Done\"><br>";
		html = html + "</form>";
		return html;
	}
}
