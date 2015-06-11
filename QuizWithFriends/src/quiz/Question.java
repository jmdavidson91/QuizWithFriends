package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;


public class Question {
	protected static final String MAIN_DB = "questions";
	protected static final String MAIN_INDEX_COL = "id";
	protected static final String MAIN_TYPE_COL = "question_type";
	protected static final String MAIN_QUESTION_INDEX_COL = "question_index";
	
	private Question currQuestion;
	protected int index = -1;
	protected String type = "";
	protected int numCorrect = 0;

	private static String[] types = {"Text Response", "Fill In Blank", "Multiple Choice", "Picture Response", "Multi Answer", "Multiple Choice Multiple Answer", "Matching"};
	
	// necessary for subclasses
	public Question() {
		
	}
	
	/*
	 * Initialize a generic question class from a given index
	 * Will figure out the type for you
	 */
	public Question(int index) {
		this.index = index;
		numCorrect = 0;
		type = typeForQuestion(index);
		if (type.equals("Text Response"))
			currQuestion = new TextResponseQuestion(index);
		else if (type.equals("Fill In Blank"))
			currQuestion = new FillBlankQuestion(index);
		else if (type.equals("Multiple Choice"))
			currQuestion = new MultipleChoiceQuestion(index);
		else if (type.equals("Picture Response"))
			currQuestion = new PictureResponseQuestion(index);
		else if (type.equals("Multi Answer"))
			currQuestion = new MultipleResponseQuestion(index);
		else if (type.equals("Multiple Choice Multiple Answer"))
			currQuestion = new MultipleChoiceMultipleAnswerQuestion(index);
		else if (type.equals("Matching")) 
			currQuestion = new MatchingQuestion(index);
	}
	
	/*
	 * Creates the class given the type 
	 */
	public Question(HttpServletRequest request, String type) {
		numCorrect = 0;
		if (type.equals("Text Response"))
			currQuestion = new TextResponseQuestion(request);
		else if (type.equals("Fill In Blank"))
			currQuestion = new FillBlankQuestion(request);
		else if (type.equals("Multiple Choice"))
			currQuestion = new MultipleChoiceQuestion(request);
		else if (type.equals("Picture Response"))
			currQuestion = new PictureResponseQuestion(request);
		else if (type.equals("Multi Answer"))
			currQuestion = new MultipleResponseQuestion(request);
		else if (type.equals("Multiple Choice Multiple Answer"))
			currQuestion = new MultipleChoiceMultipleAnswerQuestion(request);
		else if (type.equals("Matching")) 
			currQuestion = new MatchingQuestion(request);
	}
	
	/*
	 * returns an array list with the types of possible question types
	 */
	public static String[] allQuestionTypes() {
		return types;
	}
	
	/*
	 * Returns the type of question for a given index
	 * Must supply the SQL statement
	 */
	public static String typeForQuestion(int index) {
		String typeAtIndex = "";
		String sql = "SELECT question_type FROM " + MAIN_DB + " WHERE id=" + index;
		ResultSet results;
		try {
			results = DBConnection.getStatement().executeQuery(sql);
			if (results.first()) {
				typeAtIndex = results.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return typeAtIndex;
	}

	/*
	 * Static function to return the generic question creation html
	 */
	public static String getCreationHtml(String type) {
		if (type.equals("Text Response"))
			return TextResponseQuestion.getCreationHtml();
		else if (type.equals("Fill In Blank"))
			return FillBlankQuestion.getCreationHtml();
		else if (type.equals("Multiple Choice"))
			return MultipleChoiceQuestion.getCreationHtml();
		else if (type.equals("Picture Response"))
			return PictureResponseQuestion.getCreationHtml();
		else if (type.equals("Multi Answer"))
			return MultipleResponseQuestion.getCreationHtml();
		else if (type.equals("Multiple Choice Multiple Answer"))
			return MultipleChoiceMultipleAnswerQuestion.getCreationHtml();
		else if (type.equals("Matching")) 
			return MatchingQuestion.getCreationHtml();
		
		return "";
	}
	
	public int maxNumCorrect() {
		return currQuestion.maxNumCorrect();
	}
	
	/*
	 * returns the question index
	 */
	public int getIndex() {
		return currQuestion.index;
	}
	
	/*
	 * returns the question type
	 */
	public String getType() {
		return currQuestion.type;
	}
	
	/*
	 *  Returns if the question has been answered correctly.
	 *  Must call check correct submission first
	 */
	public int numCorrect() {
		return currQuestion.numCorrect;
	}
	
	/*
	 *  returns the html to insert in between <form> tags NOTE: must use your own form tags and submission button
	 */
	public String getFullQuestionHtml() {
		return currQuestion.getFullQuestionHtml();
	}
	
	/*
	 *  checks the appropriate parameters within a request object to see if the question was answered correctly
	 */
	public int checkCorrectSubmission(HttpServletRequest request) {
		currQuestion.checkCorrectSubmission(request);
		return currQuestion.numCorrect;
	}
	
	//This function is for testing purposes
	public static void createQuestionTable() {
		String sql;
		try {
			sql = "DROP TABLE IF EXISTS questions";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "DROP TABLE IF EXISTS quest_long_answer";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "DROP TABLE IF EXISTS quest_fill_blank";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "DROP TABLE IF EXISTS quest_multiple_choice";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "DROP TABLE IF EXISTS quest_picture_response";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "DROP TABLE IF EXISTS quest_multiple_response";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "DROP TABLE IF EXISTS quest_multiresponse_multianswer";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "DROP TABLE IF EXISTS quest_matching";
			DBConnection.getStatement().executeUpdate(sql);
			
			sql = "CREATE TABLE questions (id INTEGER AUTO_INCREMENT, question_type TEXT, question_index INTEGER, PRIMARY KEY (id))";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "CREATE TABLE quest_long_answer (id INTEGER AUTO_INCREMENT, question TEXT, answer TEXT, PRIMARY KEY (id))";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "CREATE TABLE quest_fill_blank (id INTEGER AUTO_INCREMENT, first_part_question TEXT, answer TEXT,	second_part_question TEXT, PRIMARY KEY (id))";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "CREATE TABLE quest_multiple_choice (id INTEGER AUTO_INCREMENT, question TEXT, answer TEXT, one_other_poss TEXT, two_other_poss TEXT, three_other_poss TEXT, PRIMARY KEY (id))";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "CREATE TABLE quest_picture_response (id INTEGER AUTO_INCREMENT, question TEXT, picture TEXT,	answer TEXT, PRIMARY KEY (id))";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "CREATE TABLE quest_multiple_response (id INTEGER AUTO_INCREMENT,	question TEXT, answers TEXT, ordered BOOL, number_correct INTEGER, PRIMARY KEY (id))";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "CREATE TABLE quest_multiresponse_multianswer (id INTEGER AUTO_INCREMENT,	question TEXT, answers TEXT, others TEXT, PRIMARY KEY (id))";
			DBConnection.getStatement().executeUpdate(sql);
			sql = "CREATE TABLE quest_matching (id INTEGER AUTO_INCREMENT, prompts TEXT, answers TEXT, PRIMARY KEY (id))";
			DBConnection.getStatement().executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
