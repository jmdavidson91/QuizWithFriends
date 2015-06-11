package quiz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * Application Lifecycle Listener implementation class ContextAndSessionListener
 *
 */
@WebListener
public class ContextAndSessionListener implements ServletContextListener, HttpSessionListener {

    /**
     * Default constructor. 
     */
    public ContextAndSessionListener() {
        
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent contextEvent) {
		// Alternate database code for connecting to Evan's database
		DBConnection.openConnection();
        DBConnection.createStatement();
        
        // Delete old message tables and create new ones
        Message.buildMessageTables();
        
        // Delete old user table and create new one
        User.buildUserTable();
        
        // Delete old friendship table and create new one
        User.buildFriendshipTable();
        
        // Delete old achievement table and create new one
        Achievements.buildAchievementTable();
        
        // delete old quiz results table and create new one
        QuizResult.createResultsTable();
        
        // delete old quiz table and create new one
        Quiz.createQuizTable();
        
        // delete old questions and create new
        Question.createQuestionTable();
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent seshEvent) {
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent seshEvent) {
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent contextEvent) {
		DBConnection.close();
    }
}
