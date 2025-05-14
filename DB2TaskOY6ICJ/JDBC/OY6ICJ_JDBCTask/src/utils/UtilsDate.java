package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UtilsDate {
	final private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public static LocalDate stringToLocalDate(String date) throws DateTimeParseException{
		return LocalDate.parse(date, formatter);
	}
}
