package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;


public class FillBlankQuestion extends Question {
	private final static String TABLE_NAME = "quest_fill_blank";
	private final static String INDEX_COL = "id";
	private final static String FIRST_QUEST_COL = "first_part_question";
	private final static String ANSWER_COL = "answer";
	private final static String SECOND_QUEST_COL = "second_part_question";
	private final static String TYPE_STRING = "Fill In Blank";
	
	private final static String INP_TAG = "blank";
	
	private String questionPartOne;
	private String questionPartTwo;
	private String[] answers;

	public FillBlankQuestion(int index) {
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
						+ FIRST_QUEST_COL 
						+ ","  
						+ SECOND_QUEST_COL 
						+ "," 
						+ ANSWER_COL + 
						" FROM " 
						+ TABLE_NAME 
						+ " WHERE " 
						+ INDEX_COL 
						+ "=" + intermediateIndex;
				results = DBConnection.getStatement().executeQuery(sql);
				if (results.first()) {
					questionPartOne = results.getString(1);
					questionPartTwo = results.getString(2);
					answers = results.getString(3).split(",");
					this.index = index;
					this.type = TYPE_STRING;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public FillBlankQuestion(HttpServletRequest request) {
		String questionPartOne = request.getParameter("questionPartOne");
		String questionPartTwo = request.getParameter("questionPartTwo");
		String answer = request.getParameter("answer");
		createFillBlankQuestion(questionPartOne, answer, questionPartTwo);
	}
	
	public void createFillBlankQuestion(String partOneQuestion, String answer, String partTwoQuestion) {
		String sql = "INSERT INTO " 
				+ TABLE_NAME 
				+ "(" 
				+ FIRST_QUEST_COL 
				+ "," 
				+ ANSWER_COL 
				+ "," 
				+ SECOND_QUEST_COL 
				+ ") VALUES (\'" 
				+ partOneQuestion 
				+ "\', \'" 
				+ answer 
				+ "\', \'"
				+ partTwoQuestion + "\')";
		try {
			DBConnection.getStatement().executeUpdate(sql);
			sql = "SELECT " 
				+ INDEX_COL		
				+ " FROM " 
				+ TABLE_NAME 
				+ " WHERE ("
				+ FIRST_QUEST_COL 
				+ "=\'" 
				+ partOneQuestion 
				+ "\' AND " 
				+ ANSWER_COL 
				+ "=\'" 
				+ answer 
				+ "\' AND " 
				+ SECOND_QUEST_COL 
				+ "=\'" 
				+ partTwoQuestion + "\')";
			//System.out.println(sql);
			ResultSet result = DBConnection.getStatement().executeQuery(sql);
			if (result.first()) {
				int intermediateIndex = result.getInt(1);
				
				this.type = TYPE_STRING;
				this.questionPartOne = partOneQuestion;
				this.questionPartTwo = partTwoQuestion;
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
		String html = "<p>Fill in the blank</p>";
		html = html + "<p>";
		html = html + questionPartOne + " ";
		html = html + "<input type=\"text\" size=\"12\" name=\"" + INP_TAG + index + "\"/>";
		html = html + " " + questionPartTwo;
		html = html + "</p>";
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
		//System.out.println("fill blank response correct = " + correct);
		return correct;
	}
	
	// static creation html function
	public static String getCreationHtml() {
		String html = "<form action=\"CreateQuestionServlet\" method=\"post\">"; 
		html = html + "<p>Fill out the fields below</p>";
		html = html + "<p>The first part of your question<br>";
		html = html + "<textarea cols=\"40\" rows=\"5\" name=\"questionPartOne\"></textarea></p>";
		html = html + "<p>The fill in the blank answer for the user to guess. Please list all possible entries separated by ','<br>";
		html = html + "<input type=\"text\" size=\"20\" name=\"answer\"/></p>";
		html = html + "<p>The second part of your question<br>";
		html = html + "<textarea cols=\"40\" rows=\"5\" name=\"questionPartTwo\"></textarea></p>";
		html = html + "<input type=\"submit\" value=\"Done\"><br>";
		html = html + "</form>";
		return html;
	}
}
