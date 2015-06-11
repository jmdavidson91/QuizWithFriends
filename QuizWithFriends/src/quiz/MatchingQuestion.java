package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;


public class MatchingQuestion extends Question {
	private final static String TABLE_NAME = "quest_matching";
	private final static String INDEX_COL = "id";
	private final static String PROMPTS_COL = "prompts";
	private final static String ANSWERS_COL = "answers";
	private final static String TYPE_STRING = "Matching";
	
	private final static String INP_TAG = "matches";
	
	private String[] promptsOrig;
	private String[] answersOrig;
	private String[] prompts;
	private String[] answers;
	
	// will initialize the question fields from the database given a question index
	public MatchingQuestion(int index) {
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
						+ PROMPTS_COL 
						+ ","  
						+ ANSWERS_COL 
						+ " FROM " 
						+ TABLE_NAME 
						+ " WHERE " 
						+ INDEX_COL 
						+ "=" + intermediateIndex;
				results = DBConnection.getStatement().executeQuery(sql);
				if (results.first()) {
					promptsOrig = results.getString(1).split(",");
					answersOrig = results.getString(2).split(",");
					
					init_arrays();
					
					this.index = index;
					this.type = TYPE_STRING;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public MatchingQuestion(HttpServletRequest request) {
		String prompts = request.getParameter("prompts");
		String answers = request.getParameter("answers");
		createMatchingQuestion(prompts, answers);
	}
	
	private void init_arrays() {
		prompts = promptsOrig;
		answers = new String[3];
		
		ArrayList<String> mixer = new ArrayList<String>();
		for (int i = 0; i < answersOrig.length; i++)
			mixer.add(answersOrig[i]);
		
		Random rand = new Random();
		
		int outpIndex = 0;
		while (mixer.size() > 0) {
			answers[outpIndex] = mixer.remove(rand.nextInt(mixer.size()));
			outpIndex = outpIndex + 1;
		}
	}

	// will create a new question and save it to the database, all fields will be initialized (including index)
	public void createMatchingQuestion(String prompts, String answers) {
	
		String sql = "INSERT INTO " 
				+ TABLE_NAME 
				+ "(" 
				+ PROMPTS_COL 
				+ ","  
				+ ANSWERS_COL 
				+ ") VALUES (\'" 
				+ prompts 
				+ "\', \'"
				+ answers + "\')";
		try {
			DBConnection.getStatement().executeUpdate(sql);
			sql = "SELECT " 
				+ INDEX_COL		
				+ " FROM " 
				+ TABLE_NAME 
				+ " WHERE ("
				+ PROMPTS_COL 
				+ "=\'" 
				+ prompts 
				+ "\' AND " 
				+ ANSWERS_COL 
				+ "=\'" 
				+ answers 
				+ "\')";
			ResultSet result = DBConnection.getStatement().executeQuery(sql);
			if (result.first()) {
				int intermediateIndex = result.getInt(1);
				this.type = TYPE_STRING;
				this.answersOrig = answers.split(",");
				this.promptsOrig = prompts.split(",");
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
		return 3;
	}

	// returns the html to insert in between <form> tags NOTE: must use your own form tags and submission button
	public String getFullQuestionHtml() {
		String html = "<p>Match each prompt on the right to the correct answer on the left</p> " +
				"<canvas id=\"matching" + index + "\" width=\"350\" height=\"200\" style=\"border:1px solid #000000;\">" +
				"</canvas>" +
				"<input type=\"hidden\" name=\"" + INP_TAG + index + "\" value=\"\">" +
				"<script>" +
				"var matches = [];" +
				"var others = [];" +
				"var answers = [];" +
				"var selectedIndex = -1;" +
				"var ans = document.getElementsByName(\"" + INP_TAG + index + "\");" +
				"var c = document.getElementById(\"matching" + index + "\");" +
				"var context = c.getContext(\"2d\");" +
				"var canvasX = c.offsetLeft;" +
				"var canvasY = c.offsetTop;" +
				"others.push({" +
				"	text: \"" + prompts[0] + "\"," +
				"	top: 25," +
				"	left: 8," +
				"	width:  90," +
				"	height: 30" +
				"});" +
				"others.push({" +
				"	text: \"" + prompts[1] + "\"," +
				"	top: 100," +
				"	left: 8," +
				"	width:  90," +
				"	height: 30" +
				"});" +
				"others.push({" +
				"	text: \"" + prompts[2] + "\"," +
				"	top: 175," +
				"	left: 8," +
				"	width:  90," +
				"	height: 30" +
				"});" +
				"answers.push({" +
				"	text: \"" + answers[0] + "\"," +
				"	top: 25," +
				"	left: 340," +
				"	width:  90," +
				"	height: 30" +
				"});" +
				"answers.push({" +
				"	text: \"" + answers[1] + "\"," +
				"	top: 100," +
				"	left: 340," +
				"width:  90," +
				"height: 30" +
				"});" +
				"answers.push({" +
				"	text: \"" + answers[2] + "\"," +
				"	top: 175," +
				"	left: 340," +
				"	width:  90," +
				"	height: 30" +
				"});" +
				
				"function drawText() {" +
				"	context.font = \"14px sans-serif\";" +
				"	context.textAlign = \"left\";" +
				"	var count = 0;" +
				"	others.forEach(function(element) {" +
				"		if (selectedIndex == count) {" +
				"			context.font = \"bold 14px sans-serif\";" +
				"		}" +
				"		context.fillText(element.text, element.left, element.top);" +
				"		if (selectedIndex == count) {" +
				"			context.font = \"14px sans-serif\";" +
				"		}" +
				"		count = count + 1;" +
				"	});" +
				"	context.textAlign = \"right\";	" +
				"	answers.forEach(function(element) {" +
				"		context.fillText(element.text, element.left, element.top);" +
				"	});" +
				"}" +
				"function clearOtherMatchOnClick(index) {" +
				"	var count = 0;" +
				"	matches.forEach(function(element) {" +
				"		if (element.other == index) {" +
				"			matches.splice(count, 1);" +
				"		}" +
				"		count = count + 1;" +
				"	});	" +
				"}" +
				"function clearAnswerMatchOnClick(index) {" +
				"	var count = 0;" +
				"	matches.forEach(function(element) {" +
				"		if (element.answer == index) {" +
				"			matches.splice(count, 1);" +
				"		}" +
				"		count = count + 1;" +
				"	});	" +
				"}" +
				"function drawMatches() {" +
				"	matches.forEach(function(element) {" +
				"		context.beginPath();" +
				"		context.moveTo(" +
				"			others[element.other].left + others[element.other].width, " +
				"			others[element.other].top);" +
				"		context.lineTo(" +
				"			answers[element.answer].left - answers[element.answer].width," +
				"			answers[element.answer].top);" +
				"		context.stroke();" +
				"	});" +
				"}" +
				"function setAnswers() {" +
				"	answer = \"\";" +
				"	matches.forEach(function(element) {" +
				"		answer = answer + others[element.other].text;" +
				"		answer = answer + \",\" + answers[element.answer].text + \",\";" +
				"	});" +
				"	ans[0].value = answer;" +
				"}" +
				"drawText();" +
				"c.addEventListener('click', function(event) {" +
				"	canvasX = c.offsetLeft;" +
				"	canvasY = c.offsetTop;" +
				"	var x = event.pageX - canvasX;" +
				"	var y = event.pageY - canvasY +	20;" +
				"	var count = 0;" +
				"	answers.forEach(function(element) {" +
				"		if (y > element.top && y < element.top + element.height " +
				"            && x+70 > element.left &&  x+70 < element.left + element.width) {" +
				"			if (selectedIndex >= 0) {" +
				"				clearAnswerMatchOnClick(count);" +
				"				matches.push({" +
				"					other: selectedIndex," +
				"					answer: count" +
				"				});				" +
				"			}" +
				"		}" +
				"		count = count + 1;" +
				"	});" +
				"	selectedIndex = -1;" +
				"	count = 0;" +
				"	others.forEach(function(element) {" +
				"		if (y > element.top && y < element.top + element.height " +
				"            && x > element.left && x < element.left + element.width) {" +
				"			selectedIndex = count;" +
				"			clearOtherMatchOnClick(selectedIndex);" +
				"		}" +
				"		count = count + 1;" +
				"	});" +
				"	context.clearRect(0, 0, c.width, c.height);" +
				"	drawText();" +
				"	drawMatches();" +
				"	setAnswers();" +
				"});" +
				"</script>";		
		return html;
	}

	// checks the appropriate parameters within a request object to see if the question was answered correctly
	public int checkCorrectSubmission(HttpServletRequest request) {
		int correct = 0;
		String[] input = request.getParameter(INP_TAG + index).split(",");
		for (int i = 0; i < (input.length-1); i = i + 2) {
			
			for (int j = 0; j < answers.length; j++) {
				if (input[i].equals(promptsOrig[j])) {
					if (input[i+1].equals(answersOrig[j])) {
						//System.out.println("Matched " + input[i+1] + " to " + answersOrig[j]);
						correct = correct + 1;
						break;
					}
				}
			}
		}
		numCorrect = correct;
		//System.out.println("matching number correct = " + numCorrect);
		return correct;
	}
	
	// static creation html function
	public static String getCreationHtml() {
		String html = "<form action=\"CreateQuestionServlet\" method=\"post\">"; 
		html = html + "<p>Fill out the fields below - ORDER MATTERS</p>";
		html = html + "<p>Enter the left side of the matches, separated by the ',' character<br>";
		html = html + "<textarea cols=\"40\" rows=\"5\" name=\"prompts\"></textarea></p>";
		html = html + "<p>Enter the matching right side in the same sequence as above, separated by the ',' character<br>";
		html = html + "<textarea cols=\"40\" rows=\"5\" name=\"answers\"></textarea></p>";
		html = html + "<input type=\"submit\" value=\"Done\"><br>";
		html = html + "</form>";
		return html;
	}
}
