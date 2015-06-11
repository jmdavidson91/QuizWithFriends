package quiz;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class Quiz {
	private int quizID;
	private int creatorID; 
	private String quizTitle;
	private String description; 
	private boolean randomized;
	private boolean multiplePgs;
	private boolean practiceMode;
	private boolean immediateCorrection;
	private ArrayList<Integer>questionIndices;
	private ArrayList<Question> questionObjectsArray;
	private String date;
	private String stringQuestionIndices;

	protected static final String MAIN_QUIZ_DB = "quizzes";
	protected static final String QUIZ_INDEX_COL = "id";
	protected static final String QUIZ_CREATOR_COL = "creatorID";
	protected static final String QUIZ_TITLE_COL = "title";
	protected static final String DESCRIPTION_COL = "description";
	protected static final String DATE_CREATED_COL = "dateCreated";
	protected static final String QUESTION_INDICES_COL = "questionIndices";
	protected static final String BOOLEAN_RANDOMIZED_COL = "randomized";
	protected static final String BOOLEAN_ONE_PAGE_COL = "multiplePgs";
	protected static final String BOOLEAN_PRACTICE_MODE_COL = "practiceMode";
	protected static final String BOOLEAN_IMMEDIATE_CORRECTION_COL = "immediateCorrection";

	//Constructor #1: retrieves a quiz given a quizIndex
	public Quiz(int quizIndex){
		String sql = "SELECT "  
				+ QUIZ_TITLE_COL 
				+ "," 
				+ DESCRIPTION_COL 
				+ ","  
				+ QUESTION_INDICES_COL 
				+ "," 
				+ QUIZ_CREATOR_COL 
				+ "," 
				+ DATE_CREATED_COL 
				+ "," 
				+ BOOLEAN_RANDOMIZED_COL 
				+ "," 
				+ BOOLEAN_ONE_PAGE_COL 
				+ "," 
				+ BOOLEAN_PRACTICE_MODE_COL 
				+ "," 
				+ BOOLEAN_IMMEDIATE_CORRECTION_COL + 
				" FROM " 
				+ MAIN_QUIZ_DB 
				+ " WHERE " 
				+ QUIZ_INDEX_COL 
				+ "=" + quizIndex;
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			results.last();
			questionIndices= new ArrayList<Integer>();
			questionObjectsArray= new ArrayList<Question>();
			this.quizID= quizIndex;
			this.quizTitle = results.getString(1);
			this.description = results.getString(2);
			this.stringQuestionIndices =  results.getString(3); 
			this.creatorID= results.getInt(4);
			this.date= results.getString(5);
			int rand= results.getInt(6);
			int mult= results.getInt(7);
			int pract= results.getInt(8);
			int imm=results.getInt(9);

			if(rand==0){ this.randomized= false;}
			else{ this.randomized= true;}

			if(mult==0){ this.multiplePgs= false;}
			else{ this.multiplePgs= true;}

			if(pract==0){ this.practiceMode= false;}
			else{ this.practiceMode= true;}

			if(imm==0){ this.immediateCorrection= false;}
			else{ this.immediateCorrection= true;}

		

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Quiz() {
		
	}
	
	//Constructor #2: Creates a quiz
	public Quiz (int creatorID, ArrayList<Question> questionObjectsArrayFromServlet, String quizTitle, String description, boolean randomized, boolean multiplePgs, boolean practiceMode, boolean immediateCorrection) {
		questionIndices= new ArrayList<Integer>();
		questionObjectsArray= new ArrayList<Question>();
		this.creatorID= creatorID;	
		this.questionObjectsArray= questionObjectsArrayFromServlet;
		this.quizTitle= quizTitle;
		this.description= description;
		this.date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());// adapted from StackOverflow
		this.randomized=randomized;
		this.multiplePgs= multiplePgs;
		this.practiceMode= practiceMode;
		this.immediateCorrection= immediateCorrection;
		storeQuestionIndices(questionObjectsArray); //this function will set "quizIndices" and store the quiz in the DB

	}

	//stores indices to questions inside the array and adds a 
	private void storeQuestionIndices(ArrayList<Question> questionObjectsArray){
		for(Question q: questionObjectsArray){
			int index= q.getIndex();
			//System.out.println("The index is: " + index);
			questionIndices.add(index); 
		}
		String stringQuestionIndices= "";
		for(int i=0; i<questionIndices.size(); i++){
			if(i!= questionIndices.size()-1){ // done so that there isn't that pesky comma at the end of the sql table
				stringQuestionIndices= stringQuestionIndices+ Integer.toString(questionIndices.get(i)) + ","; 
			}
			else{
				stringQuestionIndices= stringQuestionIndices+ Integer.toString(questionIndices.get(i));
			}
		}	
		int rand= (randomized ? 1 : 0) ;
		int mult= (multiplePgs ? 1 : 0);
		int pract= (practiceMode ? 1 : 0) ;
		int imm= (immediateCorrection ? 1 : 0) ;

		String sql = "INSERT INTO " 
				+ MAIN_QUIZ_DB 
				+ "(" 
				+ QUIZ_TITLE_COL
				+ ","
				+ DESCRIPTION_COL
				+ ","
				+ QUESTION_INDICES_COL
				+ ","
				+ QUIZ_CREATOR_COL
				+ ","
				+ DATE_CREATED_COL
				+ ","
				+ BOOLEAN_RANDOMIZED_COL
				+ ","
				+ BOOLEAN_ONE_PAGE_COL
				+ ","
				+ BOOLEAN_PRACTICE_MODE_COL
				+ ","
				+ BOOLEAN_IMMEDIATE_CORRECTION_COL				
				+ ") VALUES (\'"         
				+ quizTitle
				+ "\', \'"
				+ description
				+ "\', \'"
				+ stringQuestionIndices //changed 
				+ "\',"
				+ creatorID
				+ ", \'"
				+ date
				+ "\',"
				+ rand
				+ ","
				+ mult
				+ ","
				+ pract 
				+ ", "
				+ imm
				+ ")";



		try {
			DBConnection.getStatement().executeUpdate(sql);

			sql = "SELECT " 
					+ QUIZ_INDEX_COL		
					+ " FROM " 
					+ MAIN_QUIZ_DB 
					+ " WHERE ("
					+ QUIZ_TITLE_COL 
					+ "=\"" 
					+ quizTitle 
					+ "\" AND "  
					+ DESCRIPTION_COL 
					+ "=\"" 
					+ description 
					+ "\" AND " 
					+ DATE_CREATED_COL
					+ "=\"" 
					+ date 
					+ "\" AND "  
					+ QUESTION_INDICES_COL 
					+ "=\"" 
					+ stringQuestionIndices 
					+ "\" AND "  
					+ BOOLEAN_RANDOMIZED_COL 
					+ "=\"" 
					+ rand 
					+ "\" AND "  
					+ BOOLEAN_ONE_PAGE_COL 
					+ "=\"" 
					+ mult 
					+ "\" AND "  
					+ BOOLEAN_PRACTICE_MODE_COL 
					+ "=\"" 
					+ pract
					+ "\" AND "  
					+ BOOLEAN_IMMEDIATE_CORRECTION_COL 
					+ "=\"" 
					+ imm + "\")";
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			if (results.first()) {
				this.quizID = results.getInt(1); //sets the quizID
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//This function is for testing purposes
	public static void createQuizTable() {
		String sql;
		try {
			sql = "DROP TABLE IF EXISTS quizzes";
			DBConnection.getStatement().executeUpdate(sql);
			
			sql = "CREATE TABLE quizzes" 
					+ "(id INTEGER AUTO_INCREMENT, "
					+ "title TEXT, "
					+ "description TEXT, "
					+ "questionIndices TEXT, "
					+ "creatorID INTEGER, "
					+ "dateCreated TEXT, "
					+ "randomized BOOLEAN DEFAULT FALSE, "
					+ "multiplePgs BOOLEAN DEFAULT FALSE, "
					+ "practiceMode BOOLEAN DEFAULT FALSE, "
					+ "immediateCorrection BOOLEAN DEFAULT FALSE, "
					+ "PRIMARY KEY (id))";
			DBConnection.getStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Question> getQuestionObjectsArray(){
		if (this.questionObjectsArray.size() == 0)
			initQuestionObjects();
		return this.questionObjectsArray;
	}

	private void initQuestionObjects() {
		//Sets the value for the quizIndices ArrayList
		String [] in;
		in = stringQuestionIndices.split(",");
		//EXTRA: ADD ERROR HANDLING when an empty quiz is created, right now it crashes
		for(int i=0; i<in.length; i++){
			String tempStrIndex= in[i];
			int tempIndex= Integer.parseInt(tempStrIndex);
			questionIndices.add(tempIndex);
		}

		//sets the question objects Array
		for(int i=0; i< in.length; i++){
			String tempStrIndex= in[i];
			int tempIndex= Integer.parseInt(tempStrIndex);
			Question question= new Question(tempIndex);
			this.questionObjectsArray.add(question);
		}
	}
	
	//returns the creatorID
	public int getCreatorID(){
		return this.creatorID;
	}

	//returns the quizID
	public int getQuizID(){
		return this.quizID;
	}
	public String getQuizTitle(){
		return this.quizTitle;
	}
	//returns a quiz description
	public String getDescription(){
		return this.description;
	}

	//returns questionIndices Arraylit
	public ArrayList<Integer> getQuestionIndices(){
		if (this.questionIndices.size() == 0)
			initQuestionObjects();
		return this.questionIndices;
	}

	//returns the date a quiz was created
	public String getDate(){ 
		return this.date;
	}

	// returns randomized boolean
	public boolean getRandomized(){
		return this.randomized;
	}

	// returns multiplePgs boolean
	public boolean getMultiplePgs(){
		return this.multiplePgs;
	}

	//returns PracticeMode boolean{
	public boolean getPracticeMode(){
		return this.practiceMode;
	}

	//returns immediateCorrection boolean
	public boolean getImmediateCorrection(){
		return this.immediateCorrection;
	}

	public String getStringQuestionIndices(){
		return this.stringQuestionIndices;
	}

	//randomizes question indices array and question objects array 
	public ArrayList<Question> randomizeQuestions(){
		if (questionIndices.size() == 0)
			initQuestionObjects();
		
		Set <Integer> randomIndicesSet;
		//Randomizes questionIndices and question Objects array-I will call helper methods to make this code look cleaner. 
		if(randomized== true){
			Random r= new Random();
			randomIndicesSet= new HashSet<Integer>();
			int i=0;
			//needed to swap out vals and randomize questionIndices ArrayList
			int [] questionIndicesCopy = new int[questionIndices.size()]; 
			for(int j=0; j< questionIndices.size(); j++){
				questionIndicesCopy[j]= questionIndices.get(j);	
			}

			//keep this
			while(true){
				int index= r.nextInt(questionIndices.size());
				if(!randomIndicesSet.contains(index)){
					randomIndicesSet.add(index);
					int temp=questionIndicesCopy[index];
					questionIndices.set(i, temp);
					i++;
					if(i==(questionIndices.size())) {
						break;
					}
				} 
			}

			//Randomizing question Objects array
			for(int k=0; k<questionIndices.size(); k++){
				int index= questionIndices.get(k);
				//System.out.println("the random index is: " + index);
				Question question= new Question(index);
				this.questionObjectsArray.set(k, question);
			}
		}
		return questionObjectsArray;
	}

	//Return all Quiz Objects method
	public static ArrayList<Quiz> returnAllQuizObjectsStoredInTheDB() {
		ArrayList<Quiz> allQuizObjects = new ArrayList<Quiz>();
		String sql= "SELECT * FROM " + MAIN_QUIZ_DB;
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			while(results.next()){
				Quiz foundQZ = new Quiz();
				foundQZ.quizID = results.getInt(1);
				foundQZ.quizTitle = results.getString(2);
				foundQZ.description = results.getString(3);
				foundQZ.stringQuestionIndices =  results.getString(4); 
				foundQZ.creatorID= results.getInt(5);
				foundQZ.date= results.getString(6);
				int rand= results.getInt(7);
				int mult= results.getInt(8);
				int pract= results.getInt(9);
				int imm=results.getInt(10);

				if(rand==0){ foundQZ.randomized= false;}
				else{ foundQZ.randomized= true;}

				if(mult==0){ foundQZ.multiplePgs= false;}
				else{ foundQZ.multiplePgs= true;}

				if(pract==0){ foundQZ.practiceMode= false;}
				else{ foundQZ.practiceMode= true;}

				if(imm==0){ foundQZ.immediateCorrection= false;}
				else{ foundQZ.immediateCorrection= true;}
				
			
				allQuizObjects.add(foundQZ);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return allQuizObjects;
	}

	//returns one top high score for a quiz given a user ID and a quiz ID
	public static int getHighestQuizScoreForUser(int userID, int quizID){
		int highScore=0; 
		String sql= "SELECT points FROM results WHERE userID=" + userID + " AND quizID=" + quizID;
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			while(results.next()){
				int score= results.getInt("points");
				if (score > highScore){
					highScore= score; 
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return highScore;
	}

	//returns whether the score for a given quiz is the highest score given a quizID and a score
	public static boolean isHighestScore(int quizID, int currScore){
		int highScore=0; 
		String sql= "SELECT points FROM results WHERE quizID = " + quizID;
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			while(results.next()){
				int score= results.getInt("points");
				if (score > highScore){
					highScore= score; 
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (currScore > highScore);
	}

	// returns the total number of quizzes in the DB
	public static int getTotalNumerOfQuizzes() {
		ResultSet rs;
		int totalQuizzes = 0;
		try {
			rs = DBConnection.getStatement().executeQuery("SELECT id FROM quizzes");
			while (rs.next()) {
				totalQuizzes++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalQuizzes;
	}

	//removes a quiz from the quizzes DB given a quiz index
	public static void removeQuizFromDB(int quizIndex){
		String sql;
		sql= "DELETE FROM quizzes WHERE id =" + quizIndex;
		deleteAllQuizResultsForQuizID(quizIndex);
		try {
			DBConnection.getStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//returns all quizzes sorted by Date Created
	public static ArrayList<Quiz> getQuizzesSortedByDateCreated(){
		String sql= "SELECT * FROM quizzes" + " ORDER BY " + QUIZ_INDEX_COL + " DESC" ;
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		ArrayList<Integer> quizIDs = new ArrayList<Integer>();
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			while(results.next()){
				int quizID= results.getInt("id");
				quizIDs.add(quizID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for(int i=0; i<quizIDs.size(); i++){
			int ID= quizIDs.get(i);
			Quiz tempQuiz= new Quiz(ID);
			quizzes.add(tempQuiz);
		}
		return quizzes;
	}

	
	public static ArrayList<Quiz> getRecentQuizzesSortedByDateCreated(int creatorID){
		String sql= "SELECT * FROM quizzes WHERE creatorID=" + creatorID + " ORDER BY " + QUIZ_INDEX_COL + " DESC";
		ArrayList<Quiz> quizzesWithCreatorID = new ArrayList<Quiz>();
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			while(results.next()){
				Quiz foundQZ = new Quiz();
				foundQZ.quizID = results.getInt(1);
				foundQZ.quizTitle = results.getString(2);
				foundQZ.description = results.getString(3);
				foundQZ.stringQuestionIndices =  results.getString(4); 
				foundQZ.creatorID= results.getInt(5);
				foundQZ.date= results.getString(6);
				int rand= results.getInt(7);
				int mult= results.getInt(8);
				int pract= results.getInt(9);
				int imm=results.getInt(10);

				if(rand==0){ foundQZ.randomized= false;}
				else{ foundQZ.randomized= true;}

				if(mult==0){ foundQZ.multiplePgs= false;}
				else{ foundQZ.multiplePgs= true;}

				if(pract==0){ foundQZ.practiceMode= false;}
				else{ foundQZ.practiceMode= true;}

				if(imm==0){ foundQZ.immediateCorrection= false;}
				else{ foundQZ.immediateCorrection= true;}
				
			
				quizzesWithCreatorID.add(foundQZ);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizzesWithCreatorID;
	}
	
	//returns all quizzes taken by a user sorted by Date Created
	public static ArrayList<Quiz> getQuizzesSortedByDateTaken(int userID){
		return QuizResult.getQuizzesSortedByDateTaken(userID);
	}
	
	//returns all quizzes taken by a user sorted by Date Created
	public static ArrayList<Quiz> getQuizzesTakenByUserSortedByDateTaken(int userID){
		String sql= "SELECT * FROM quizzes WHERE creatorID = " + userID + " ORDER BY " + QUIZ_INDEX_COL + " DESC" ;
		ArrayList<Quiz> qzs = new ArrayList<Quiz>();
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			while(results.next()){
				Quiz foundQZ = new Quiz();
				foundQZ.quizID = results.getInt(1);
				foundQZ.quizTitle = results.getString(2);
				foundQZ.description = results.getString(3);
				foundQZ.stringQuestionIndices =  results.getString(4); 
				foundQZ.creatorID= results.getInt(5);
				foundQZ.date= results.getString(6);
				int rand= results.getInt(7);
				int mult= results.getInt(8);
				int pract= results.getInt(9);
				int imm=results.getInt(10);

				if(rand==0){ foundQZ.randomized= false;}
				else{ foundQZ.randomized= true;}

				if(mult==0){ foundQZ.multiplePgs= false;}
				else{ foundQZ.multiplePgs= true;}

				if(pract==0){ foundQZ.practiceMode= false;}
				else{ foundQZ.practiceMode= true;}

				if(imm==0){ foundQZ.immediateCorrection= false;}
				else{ foundQZ.immediateCorrection= true;}
				
			
				qzs.add(foundQZ);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return qzs;
		
	}

	//returns all quizzes sorted by popularity
	public static ArrayList<Quiz> getQuizzesSortedByPopularity(){
		ArrayList<Integer> quizIDs = new ArrayList<Integer>();
		
		try {
			Statement stmt = DBConnection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM quizzes");
			while (rs.next()) {
				int id = rs.getInt("id");
				quizIDs.add(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		int size = quizIDs.size();
		for (int i = 0; i < size; i++) {
			Quiz quiz = new Quiz(quizIDs.get(i));
			quizzes.add(quiz);
		}
		
		QuizPopularityCmp cmp = new QuizPopularityCmp();
		
		Collections.sort(quizzes, cmp);
		
		return quizzes;
	}

	//deletes all quiz results for a given quiz id in the results DB
	public static void deleteAllQuizResultsForQuizID(int quizID){
		String sql;
		sql= "DELETE FROM results WHERE quizID = " + quizID;
		try {
			DBConnection.getStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// returns the total number of results in the DB
	public static int getTotalNumerOfResults() {
		ResultSet rs;
		int totalResults = 0;
		try {
			rs = DBConnection.getStatement().executeQuery("SELECT id FROM results");
			while (rs.next()) {
				totalResults++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalResults;
	}

	// downloads all quizzes with a given title
	public static ArrayList<Quiz> getAllQuizzesWithTitle(String title){
		String sql= "SELECT * FROM quizzes WHERE title = " + "\""+ title + "\"";
		ArrayList<Quiz> quizzesWithTitle = new ArrayList<Quiz>();
		ArrayList<Integer> quizIDs = new ArrayList<Integer>();
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			while(results.next()){
				int quizID= results.getInt("id");
				quizIDs.add(quizID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println("^^In download all quizzes with given title- The size of quizIDs is: " + quizIDs.size() );

		for(int ID: quizIDs){
			//System.out.println("^^In download all quizzes with given titlethe id is: " + ID);
			Quiz tempQuiz= new Quiz(ID);
			quizzesWithTitle.add(tempQuiz);
		}

		return quizzesWithTitle;
	}

	public int getPopularity() {
		return QuizResult.getPopularity(quizID);
	}



}
