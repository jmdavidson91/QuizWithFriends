package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;


public class MultipleChoiceQuestion extends Question {
	private final static String TABLE_NAME = "quest_multiple_choice";
	private final static String INDEX_COL = "id";
	private final static String QUEST_COL = "question";
	private final static String ANSWER_COL = "answer ";
	private final static String OTHER1_COL = "one_other_poss";
	private final static String OTHER2_COL = "two_other_poss";
	private final static String OTHER3_COL = "three_other_poss";
	private final static String TYPE_STRING = "Multiple Choice";
	
	private final static String INP_TAG = "radio";
	
	private String question;
	private String answer;
	private String one_other_poss;
	private String two_other_poss;
	private String three_other_poss;
	
	// will initialize the question fields from the database given a question index
	public MultipleChoiceQuestion(int index) {
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
						+ ANSWER_COL 
						+ "," 
						+ OTHER1_COL
						+ "," 
						+ OTHER2_COL
						+ "," 
						+ OTHER3_COL 
						+ " FROM " 
						+ TABLE_NAME 
						+ " WHERE " 
						+ INDEX_COL 
						+ "=" + intermediateIndex;
	
				results = DBConnection.getStatement().executeQuery(sql);
				if (results.first()) {
					question = results.getString(1);
					answer = results.getString(2);
					one_other_poss = results.getString(3);
					two_other_poss = results.getString(4);
					three_other_poss = results.getString(5);
					this.index = index;
					this.type = TYPE_STRING;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public MultipleChoiceQuestion(HttpServletRequest request) {
		String question = request.getParameter("question");
		String answer = request.getParameter("answer");
		String oneOtherPoss = request.getParameter("one_other_poss");
		String twoOtherPoss = request.getParameter("two_other_poss");
		String threeOtherPoss = request.getParameter("three_other_poss");
		createMultipleChoiceQuestion(question, answer, oneOtherPoss, twoOtherPoss, threeOtherPoss);
	}

	// will create a new question and save it to the database, all fields will be initialized (including index)
	public void createMultipleChoiceQuestion(String question, String answer, String otherPossibilityOne, String otherPossibilityTwo, String otherPossibilityThree) {
		String sql = "INSERT INTO " 
				+ TABLE_NAME 
				+ "(" 
				+ QUEST_COL 
				+ "," 
				+ ANSWER_COL 
				+ "," 
				+ OTHER1_COL 
				+ "," 
				+ OTHER2_COL 
				+ "," 
				+ OTHER3_COL 
				+ ") VALUES (\'" 
				+ question 
				+ "\', \'" 
				+ answer 
				+ "\', \'"
				+ otherPossibilityOne 
				+ "\', \'"
				+ otherPossibilityTwo 
				+ "\', \'"
				+ otherPossibilityThree + "\')";
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
				+ answer 
				+ "\' AND "  
				+ OTHER1_COL 
				+ "=\'" 
				+ otherPossibilityOne + "\')";
			ResultSet result = DBConnection.getStatement().executeQuery(sql);
			if (result.first()) {
				int intermediateIndex = result.getInt(1);
				this.type = TYPE_STRING;
				this.question = question;
				this.answer = answer;
				this.one_other_poss = otherPossibilityOne;
				this.two_other_poss = otherPossibilityTwo;
				this.three_other_poss = otherPossibilityThree;
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
		String html = "<p>Select the correct answer</p>";
		html = html + "<p>" + question + "</p>";		
		html = html + "<input type=\"radio\" name=\"" + INP_TAG + index + "\" value=\"" + one_other_poss + "\">" + one_other_poss + "<br>";
		html = html + "<input type=\"radio\" name=\"" + INP_TAG + index + "\" value=\"" + answer + "\">" + answer + "<br>";
		html = html + "<input type=\"radio\" name=\"" + INP_TAG + index + "\" value=\"" + two_other_poss + "\">" + two_other_poss + "<br>";
		html = html + "<input type=\"radio\" name=\"" + INP_TAG + index + "\" value=\"" + three_other_poss + "\">" + three_other_poss + "<br>";
		return html;
	}

	// checks the appropriate parameters within a request object to see if the question was answered correctly
	public int checkCorrectSubmission(HttpServletRequest request) {
		int correct = 0;
		String selectedAnswer = request.getParameter(INP_TAG + index);
		if (answer.equals(selectedAnswer))
			correct = 1;		
		numCorrect = correct;
		//System.out.println("Multiple choice Number correct = " + numCorrect);
		return correct;	
	}
	
	// static creation html function
	public static String getCreationHtml() {
		String html = "<form action=\"CreateQuestionServlet\" method=\"post\">"; 
		html = html + "<p>Fill out the fields below</p>";
		html = html + "<p>Enter your question<br>";
		html = html + "<textarea cols=\"40\" rows=\"5\" name=\"question\"></textarea></p>";
		html = html + "<p>The correct answer<br>";
		html = html + "<input type=\"text\" size=\"18\" name=\"answer\"/></p>";
		html = html + "<p>Another incorrect choice<br>";
		html = html + "<input type=\"text\" size=\"18\" name=\"one_other_poss\"/></p>";
		html = html + "<p>A second other incorrect choice<br>";
		html = html + "<input type=\"text\" size=\"18\" name=\"two_other_poss\"/></p>";
		html = html + "<p>A third other incorrect choice<br>";
		html = html + "<input type=\"text\" size=\"18\" name=\"three_other_poss\"/></p>";
		html = html + "<input type=\"submit\" value=\"Done\"><br>";
		html = html + "</form>";
		return html;
	}
}
