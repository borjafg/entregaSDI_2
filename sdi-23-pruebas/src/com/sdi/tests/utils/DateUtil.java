package com.sdi.tests.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import alb.util.log.Log;

public class DateUtil {

    public static boolean sameDay(Date date1, Date date2) {
	Date dateT1 = trunk(date1);
	Date dateT2 = trunk(date2);
	return dateT1.compareTo(dateT2) == 0;
    }

    private static Date trunk(Date fecha) {
	Calendar calendar = Calendar.getInstance(TimeZone
		.getTimeZone("Europe/Madrid"));
	calendar.setTime(fecha);
	calendar.set(Calendar.HOUR_OF_DAY, 0);
	calendar.set(Calendar.MINUTE, 0);
	calendar.set(Calendar.SECOND, 0);
	calendar.set(Calendar.MILLISECOND, 0);
	return calendar.getTime();
    }

    public static Date convertStringToDate(String fecha) {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	Date fechaDate = null;
	try {
	    fechaDate = sdf.parse(fecha);
	} catch (ParseException pe) {
	    Log.error("Error a la hora de convertir una fecha de String a Date");
	}
	return fechaDate;

    }

    public static Date diasSiguientes(Date fecha, int dias) {
	Calendar calendar = Calendar.getInstance(TimeZone
		.getTimeZone("Europe/Madrid"));
	calendar.setTime(fecha);
	calendar.set(Calendar.HOUR_OF_DAY, 0);
	calendar.set(Calendar.MINUTE, 0);
	calendar.set(Calendar.SECOND, 0);
	calendar.set(Calendar.MILLISECOND, 0);
	calendar.add(Calendar.DAY_OF_YEAR, dias);
	return calendar.getTime();

    }

}