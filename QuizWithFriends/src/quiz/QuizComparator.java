package quiz;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class QuizComparator implements Comparator<Quiz> {
	
	public QuizComparator() {
		super();
	}
	
	public int compare(Quiz q1, Quiz q2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = df.parse(q1.getDate(), new ParsePosition(0));
		Date date2 = df.parse(q2.getDate(), new ParsePosition(0));
		
		return date1.compareTo(date2);
	}
}
