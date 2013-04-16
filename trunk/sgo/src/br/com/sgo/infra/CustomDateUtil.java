package br.com.sgo.infra;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CustomDateUtil {

	public static Calendar getDataAtual() {   

		Calendar cal = new GregorianCalendar();   
	    Date data = new Date(System.currentTimeMillis());     

	    cal.setTime(data);
	    cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));   
	    cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));   
	    cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));   
	    cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND)); 

	    return cal;   
	}
	
	public static Calendar getTimeAtual() {   

		Calendar cal = new GregorianCalendar();   
	    Date data = new Date(System.currentTimeMillis());     

	    cal.setTime(data);

	    return cal;   
	}

	public static Calendar getCalendarFim(Calendar calendarInicio) {

		Calendar calendarFim = new GregorianCalendar();

		calendarFim.setTime(calendarInicio.getTime());

		calendarFim.set(Calendar.HOUR_OF_DAY,calendarFim.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendarFim.set(Calendar.MINUTE,calendarFim.getActualMaximum(Calendar.MINUTE));
		calendarFim.set(Calendar.SECOND,calendarFim.getActualMaximum(Calendar.SECOND));
		calendarFim.set(Calendar.MILLISECOND, calendarFim.getActualMinimum(Calendar.MILLISECOND)); 

		return calendarFim;
	}
}
