package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class MultipleChoiceMultipleAnswerQuestion extends Question {
	private final static String TABLE_NAME = "quest_multiresponse_multianswer";
	private final static String INDEX_COL = "id";
	private final static String QUEST_COL = "question";
	private final static String ANSWERS_COL = "answers";
	private final static String OTHERS_COL = "others";
	private final static String TYPE_STRING = "Multiple Choice Multiple Answer";
	
	private final static String INP_TAG = "choice";
	
	private String question;
	private String[] answers;
	private String[] others;
	private String[] choices;
	
	// will initialize the question fields from the database given a question index
	public MultipleChoiceMultipleAnswerQuestion(int index) {
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
						+ OTHERS_COL + 
						" FROM " 
						+ TABLE_NAME 
						+ " WHERE " 
						+ INDEX_COL 
						+ "=" + intermediateIndex;

				results = DBConnection.getStatement().executeQuery(sql);
			
				if (results.first()) {
					question = results.getString(1);
					answers = results.getString(2).split(",");
					others = results.getString(3).split(",");
					init_arrays();
					this.index = index;
					this.type = TYPE_STRING;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public MultipleChoiceMultipleAnswerQuestion(HttpServletRequest request) {
		String question = request.getParameter("question");
		String answers = request.getParameter("answers");
		String others = request.getParameter("others");
		createMultipleChoiceMultipleAnswerQuestion(question, answers, others);
	}
	
	private void init_arrays() {
		choices = new String[answers.length + others.length];
		String[] tempChoices = new String[answers.length + others.length];
		
		int counter = 0;
		for(int i = 0; i < answers.length; i++) {
			tempChoices[counter] = answers[i];
			counter = counter + 1;
		}
		for(int i = 0; i < others.length; i++) {
			tempChoices[counter] = others[i];
			counter = counter + 1;
		}
		
		ArrayList<String> mixer = new ArrayList<String>();
		for (int i = 0; i < tempChoices.length; i++)
			mixer.add(tempChoices[i]);
		
		Random rand = new Random();
		
		int outpIndex = 0;
		while (mixer.size() > 0) {
			choices[outpIndex] = mixer.remove(rand.nextInt(mixer.size()));
			outpIndex = outpIndex + 1;
		}
	}
	
	// will create a new question and save it to the database, all fields will be initialized (including index)
	public void createMultipleChoiceMultipleAnswerQuestion(String question, String answers, String others) {

		String sql = "INSERT INTO " 
				+ TABLE_NAME 
				+ "(" 
				+ QUEST_COL 
				+ "," 
				+ ANSWERS_COL 
				+ "," 
				+ OTHERS_COL 
				+ ") VALUES (\'" 
				+ question 
				+ "\', \'" 
				+ answers 
				+ "\', \'"
				+ others + "\')";
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
				+ OTHERS_COL 
				+ "=\'" 
				+ others + "\')";
			ResultSet result = DBConnection.getStatement().executeQuery(sql);
			if (result.first()) {
				int intermediateIndex = result.getInt(1);
				this.type = TYPE_STRING;
				this.answers = answers.split(",");
				this.others = others.split(",");
				
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
			init_arrays();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int maxNumCorrect() {
		return answers.length;
	}
	
	// returns the html to insert in between <form> tags NOTE: must use your own form tags and submission button
	public String getFullQuestionHtml() {
		String html = "<p>Select all the answers that apply</p>";
		html = html + "<p>" + question + "</p>";;
		for (int i = 0; i < choices.length; i++) 
			html = html + (i+1) + ". <input type=\"checkbox\" name=\"" + INP_TAG + index + "\" value=\"" + choices[i] + "\">" + choices[i] + "<br>";

		return html;
	}

	// checks the appropriate parameters within a request object to see if the question was answered correctly
	public int checkCorrectSubmission(HttpServletRequest request) {
		int correct = 0;
		
		String[] selected = request.getParameterValues(INP_TAG + index);
		ArrayList<String> answerTracker = new ArrayList<String>();			
		for (int i = 0; i < answers.length; i++) 
			answerTracker.add(answers[i]);
			
		for (int i = 0; i < answerTracker.size(); i++)
			for(int j = 0; j < selected.length; j++)
				if (answerTracker.get(i).equals(selected[j])) {
					correct = correct + 1;
					//System.out.println("Matched " + selected[j] + " to " + answerTracker.get(i));
					answerTracker.remove(i);
					i = i - 1;
					break;
				}
		numCorrect = correct;
		//System.out.println("MCMA number correct = " + numCorrect);
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
		html = html + "<p>Enter the other incorrect options, separated by the ',' character<br>";
		html = html + "<textarea cols=\"40\" rows=\"5\" name=\"others\"></textarea></p>";
		html = html + "<input type=\"submit\" value=\"Done\"><br>";
		html = html + "</form>";
		return html;
	}
}
