package quiz;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class QuizResultComparator implements Comparator<QuizResult> {
	
	public QuizResultComparator() {
		super();
	}
	
	public int compare(QuizResult r1, QuizResult r2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = df.parse(r1.getDateTaken(), new ParsePosition(0));
		Date date2 = df.parse(r2.getDateTaken(), new ParsePosition(0));
		
		return date1.compareTo(date2);
	}
}
