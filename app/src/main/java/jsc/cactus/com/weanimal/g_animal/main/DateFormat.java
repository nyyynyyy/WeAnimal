package jsc.cactus.com.weanimal.g_animal.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateFormat {
	public enum Type{
		DAY(FORMAT_DAY), SECOND(FORMAT_SECOND);

		private SimpleDateFormat formatDay;
		Type(SimpleDateFormat formatDay) {
			this.formatDay = formatDay;
		}
	}

	private static final SimpleDateFormat FORMAT_SECOND = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss a");
	private static final SimpleDateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String formatDate(Date date, Type type){
		return type.formatDay.format(date);
	}
	
	public static Date parseDate(String date, Type type){
		try {
			return type.formatDay.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
}
