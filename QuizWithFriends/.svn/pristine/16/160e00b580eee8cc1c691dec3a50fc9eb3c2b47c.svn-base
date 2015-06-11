package quiz;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class QuizResult {
	private int resultID;
	private int quizID; 
	private int userID;
	private int points;
	private long timeTaken;
	private String dateTaken;
	private int time; 

	protected static final String MAIN_RESULTS_DB = "results";
	protected static final String RESULT_INDEX_COL = "id";
	protected static final String QUIZ_INDEX_COL  = "quizID";
	protected static final String USER_COL = "userID";
	protected static final String POINTS_COL = "points";
	protected static final String TIME_TAKEN_COL = "timeTaken";
	protected static final String DATE_TAKEN_COL = "dateTaken";

	//Constructor 1 retrieves a result given a a result index
	public QuizResult(int resultIndex){
		String sql = "SELECT "  
				+ QUIZ_INDEX_COL 
				+ "," 
				+ USER_COL 
				+ ","  
				+ POINTS_COL 
				+ "," 
				+ DATE_TAKEN_COL 
				+ "," 
				+ TIME_TAKEN_COL + 
				" FROM " 
				+ MAIN_RESULTS_DB 
				+ " WHERE " 
				+ RESULT_INDEX_COL 
				+ "=" + resultIndex;
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			results.last();
			this.resultID= resultIndex;
			this.quizID = results.getInt(1);
			this.userID = results.getInt(2);
			this.points=  results.getInt(3); 
			this.dateTaken= results.getString(4);
			this.time= results.getInt(5);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//Constructor 2 creates a QuizResult object
	public QuizResult(int quizID, int userID, int points, long timeTaken){
		this.quizID= quizID;
		this.userID= userID;
		this.points=points;
		this.timeTaken= timeTaken;
		this.dateTaken = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());// adapted from StackOverflow 
		storeResults();
	}
	
	public QuizResult() {
		
	}
	
	//returns resultID
	public int getResultID(){
		return this.resultID;
	}

	//returns quizID
	public int getQuizID(){
		return this.quizID;
	}

	//returns userID
	public int getUserID(){
		return this.userID;
	}

	//Returns the number of points received for a quiz
	public int getScore(){
		return this.points;
	}

	//returns timeTaken as a long. I WILL DELETE THIS METHOD. HAVENT DELETED BC I NEED IT FOR TESTING
	public long getTimeTaken(){
		return this.timeTaken;
	}
	
	//returns timeTaken as an int
	public int getTime(){
		return this.time;
	}

	//returns dateTaken
	public String getDateTaken(){
		return this.dateTaken;
	}

	//(int quizID, int userID, int points, long timeTaken, String dateTaken){
	//This function is for testing purposes
	public static void createResultsTable() {
		//Integer timeAsInteger= (int) (long) timeTaken;

		String sql;
		try {
			sql = "DROP TABLE IF EXISTS results";
			DBConnection.getStatement().executeUpdate(sql);
			
			sql = "CREATE TABLE results" 
					+ "(id INTEGER AUTO_INCREMENT, "
					+ "quizID INTEGER, "
					+ "userID INTEGER, "
					+ "points INTEGER, "
					+ "dateTaken TEXT, "
					+ "timeTaken INTEGER, "
					+ "PRIMARY KEY (id))";
			DBConnection.getStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

	public void storeResults(){			
		Integer timeAsInteger= (int) (long) timeTaken;
		this.time= timeAsInteger; //newly added code 
		String sql = "INSERT INTO " 
				+ MAIN_RESULTS_DB 
				+ "(" 
				+ QUIZ_INDEX_COL
				+ ","
				+ USER_COL
				+ ","
				+ POINTS_COL
				+ ","
				+ DATE_TAKEN_COL 
				+ ","
				+ TIME_TAKEN_COL				
				+ ") VALUES ("         
				+ quizID
				+ ", "
				+ userID
				+ ", "
				+ points
				+ ", \'"
				+ dateTaken
				+ "\',"
				+ timeAsInteger
				+ ")";

		try {
			DBConnection.getStatement().executeUpdate(sql);

			sql = "SELECT " 
					+ RESULT_INDEX_COL		
					+ " FROM " 
					+ MAIN_RESULTS_DB 
					+ " WHERE ("
					+ QUIZ_INDEX_COL 
					+ "=\"" 
					+ quizID 
					+ "\" AND "  
					+ USER_COL 
					+ "=\"" 
					+ userID 
					+ "\" AND " 
					+ POINTS_COL
					+ "=\"" 
					+ points 
					+ "\" AND "  
					+ DATE_TAKEN_COL 
					+ "=\"" 
					+ dateTaken 
					+ "\" AND "  
					+ TIME_TAKEN_COL 
					+ "=\"" 
					+ timeAsInteger  + "\")";
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			if (results.first()) {
				this.resultID = results.getInt(1); //sets the quizID
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int getPopularity(int quizID) {
		int popularity = 0;
		
		try {
			Statement stmt = DBConnection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT " + RESULT_INDEX_COL + " FROM " + MAIN_RESULTS_DB + " WHERE " + QUIZ_INDEX_COL + " = " + quizID);
			while (rs.next()) {
				popularity++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return popularity;
	}
	
	public static double getAvgScoreForQuiz(int quizID) {
		double avg = 0;
		String sql= "SELECT avg(points) FROM results WHERE quizID=" + quizID;
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			if (results.next()) {
				avg = results.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return avg;
	}
	
	public static int getMinScoreForQuiz(int quizID) {
		int score = 0;
		String sql= "SELECT min(points) FROM results WHERE quizID=" + quizID;
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			if (results.next()) {
				score = results.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return score;
	}
	
	public static int getMaxScoreForQuiz(int quizID) {
		int score = 0;
		String sql= "SELECT max(points) FROM results WHERE quizID=" + quizID;
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			if (results.next()) {
				score = results.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return score;
	}
	
	public static int getTotalResultsForQuiz(int quizID) {
		int count = 0;
		String sql= "SELECT count(*) FROM results WHERE quizID=" + quizID;
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			if (results.next()) {
				count = results.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static int getTotalResults() {
		int count = 0;
		String sql= "SELECT count(*) FROM results";
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			if (results.next()) {
				count = results.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	//returns all quizzes taken by a user sorted by Date Created
	public static ArrayList<QuizResult> getAllQuizResultsSortedByHighScore(int quizID){
		String sql= "SELECT * FROM results WHERE quizID=" + quizID + " ORDER BY " + POINTS_COL + " DESC, " + TIME_TAKEN_COL + " ASC" ;
		return buildArrayListOfQRs(sql);
	}
	
	private static ArrayList<QuizResult> buildArrayListOfQRs(String sql) {
		ArrayList<QuizResult> qrs = new ArrayList<QuizResult>();
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			while(results.next()){
				QuizResult foundQR = new QuizResult();
				foundQR.resultID = results.getInt("id");
				foundQR.quizID = results.getInt("quizID");	
				foundQR.userID = results.getInt("userID");
				foundQR.points = results.getInt("points");
				foundQR.timeTaken = results.getLong("timeTaken");
				foundQR.dateTaken = results.getString("dateTaken");
				qrs.add(foundQR);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return qrs;
	}
	
	//returns all quizzes taken by a user sorted by Date Created
	public static ArrayList<Quiz> getQuizzesSortedByDateTaken(int userID){
		String sql= "SELECT * FROM results WHERE userID = " + userID;
		ArrayList<Quiz> quizzesWithUserID = new ArrayList<Quiz>();
		ArrayList<Integer> quizIDs = new ArrayList<Integer>();
		try {
			ResultSet results;
			results = DBConnection.getStatement().executeQuery(sql);
			while(results.next()){
				int quizID = results.getInt("quizID");
				quizIDs.add(quizID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int [] indicesBackwards= new int[quizIDs.size()];
		for(int i=0; i<quizIDs.size(); i++){
			indicesBackwards[i]= quizIDs.get((quizIDs.size()-1) -i);
		}
		for(int i=0; i<indicesBackwards.length; i++){
			int ID= indicesBackwards[i];
			Quiz tempQuiz= new Quiz(ID);
			quizzesWithUserID.add(tempQuiz);
		}
		return quizzesWithUserID;
	}
	
	//returns all quizzes taken by a user sorted by Date Created
	public static ArrayList<QuizResult> getQuizResultsSortedByDateTaken(int userID, int quizID){
		String sql= "SELECT * FROM results WHERE userID = " + userID + " AND quizID=" + quizID + " ORDER BY " + RESULT_INDEX_COL + " DESC" ;
		return buildArrayListOfQRs(sql);
	}
	
	//returns all quizzes taken by a user sorted by Date Created
	public static ArrayList<QuizResult> getQuizResultsForUserSortedByDateTaken(int userID){
		String sql= "SELECT * FROM results WHERE userID = " + userID + " ORDER BY " + RESULT_INDEX_COL + " DESC" ;
		return buildArrayListOfQRs(sql);
	}
	
	public static ArrayList<QuizResult> getQuizResultsForLastDay(int quizID) {
		String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		Calendar yest = Calendar.getInstance();
		yest.add(Calendar.DAY_OF_MONTH, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(yest.getTime());
		String sql= "SELECT * FROM results WHERE quizID=" + quizID + " AND (dateTaken='" + today + "' OR dateTaken='" + yesterday + "') ORDER BY " + RESULT_INDEX_COL + " DESC";
		//System.out.println(sql);
		return buildArrayListOfQRs(sql);
	}
	
	public static ArrayList<QuizResult> getQuizResultsForLastDaySortedByScore(int quizID) {
		String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		Calendar yest = Calendar.getInstance();
		yest.add(Calendar.DAY_OF_MONTH, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(yest.getTime());
		String sql= "SELECT * FROM results WHERE quizID=" + quizID + " AND (dateTaken='" + today + "' OR dateTaken='" + yesterday + "') ORDER BY " + POINTS_COL + " DESC" ;
		//System.out.println(sql);
		return buildArrayListOfQRs(sql);
	}
}
