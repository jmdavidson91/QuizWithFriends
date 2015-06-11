package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;


public class TextResponseQuestion extends Question {
	private final static String TABLE_NAME = "quest_long_answer";
	private final static String INDEX_COL = "id";
	private final static String QUEST_COL = "question";
	private final static String ANSWER_COL = "answer";
	private final static String TYPE_STRING = "Text Response";
	
	private final static String INP_TAG = "response";
	
	private String question;
	private String[] answers;
	
	// will initialize the question fields from the database given a question index
	public TextResponseQuestion(int index) {
		String sql = "SELECT " 
				+ Question.MAIN_QUESTION_INDEX_COL  
				+ " FROM " 
				+ Question.MAIN_DB  
				+ " WHERE " 
				+ Question.MAIN_INDEX_COL 
				+ "=" + index;
		//System.out.println("grabbing question with index = " + index);
		//System.out.println(sql);
		try {
			ResultSet results = DBConnection.getStatement().executeQuery(sql);
			if (results.first()) {
				int intermediateIndex = results.getInt(1);
				sql = "SELECT " 
						+ QUEST_COL 
						+ ","  
						+ ANSWER_COL + 
						" FROM " 
						+ TABLE_NAME 
						+ " WHERE " 
						+ INDEX_COL 
						+ "=" + intermediateIndex;
				results = DBConnection.getStatement().executeQuery(sql);
				if (results.first()) {
					question = results.getString(1);
					answers = results.getString(2).split(",");
					this.type = TYPE_STRING;
					this.index = index;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public TextResponseQuestion(HttpServletRequest request) {
		String question = request.getParameter("question");
		String answer = request.getParameter("answer");
		createTextResponseQuestion(question, answer);
	}

	// will create a new question and save it to the database, all fields will be initialized (including index)
	public void createTextResponseQuestion(String question, String answer) {
		String sql = "INSERT INTO " 
				+ TABLE_NAME 
				+ "(" 
				+ QUEST_COL 
				+ "," 
				+ ANSWER_COL 
				+ ") VALUES (\'" 
				+ question 
				+ "\', \'"
				+ answer + "\')";
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
				+ ANSWER_COL 
				+ "=\'" 
				+ answer + "\')";
			ResultSet result = DBConnection.getStatement().executeQuery(sql);
			if (result.first()) {
				int intermediateIndex = result.getInt(1);
				
				this.type = TYPE_STRING;
				this.question = question;
				this.answers = answer.split(",");
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

	public int maxNumCorrect() {
		return 1;
	}
	
	// returns the html to insert in between <form> tags NOTE: must use your own form tags and submission button
	public String getFullQuestionHtml() {
		String html = "<p>Answer the question</p>";
		html = html + "<p>" + question + "</p>";
		html = html + "<input type=\"text\" size=\"18\" name=\"" + INP_TAG + index + "\"/>";
		return html;
	}

	// checks the appropriate parameters within a request object to see if the question was answered correctly
	public int checkCorrectSubmission(HttpServletRequest request) {
		int correct = 0;
		String input = request.getParameter(INP_TAG + index);
		for (int i = 0; i < answers.length; i++)
			if (answers[i].equals(input))
				correct = 1;
		numCorrect = correct;
		//System.out.println("text response correct = " + correct);
		return correct;
	}
	
	// static creation html function
	public static String getCreationHtml() {
		String html = "<form action=\"CreateQuestionServlet\" method=\"post\">";
		html = html + "<p>Fill out the fields below</p>";
		html = html + "<p>Enter your question<br>";
		html = html + "<textarea cols=\"40\" rows=\"5\" name=\"question\"></textarea></p>";
		html = html + "<p>The list of possible correct answers, separated by ','<br>";
		html = html + "<input type=\"text\" size=\"18\" name=\"answer\"/></p>";
		html = html + "<input type=\"submit\" value=\"Done\"><br>";
		html = html + "</form>";
		return html;
	}
}
