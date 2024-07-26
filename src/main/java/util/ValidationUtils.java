package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
	
	//return true if it is not text only - invalid users' name
	public static boolean isNotText(String text) {
		Pattern namePattern = Pattern.compile("[^a-zA-Z]+");
		Matcher nameMatcher = namePattern.matcher(text);
		return(nameMatcher.find());
	}
	
	//return true if it is not alphanumeric only - invalid username
	public static boolean isNotAlphaNumeric(String text) {
		Pattern usernamePattern = Pattern.compile("[^a-zA-Z0-9]+");
		Matcher usernameMatcher = usernamePattern.matcher(text);
		return (usernameMatcher.find());
	}
	
	//return true if it is not alphanumeric only - invalid username
		public static boolean isNotAlphaNumericAndBlankSpaces(String text) {
			Pattern usernamePattern = Pattern.compile("[^a-zA-Z0-9 ]+");
			Matcher usernameMatcher = usernamePattern.matcher(text);
			return (usernameMatcher.find());
		}
	
	//return true if it is not numeric only 
	public static boolean isNotNumeric(String text) {
		Pattern usernamePattern = Pattern.compile("[^0-9]+");
		Matcher usernameMatcher = usernamePattern.matcher(text);
		return (usernameMatcher.find());
	}
	
	//return true if it is not numeric or floating point only - invalid unit price
	public static boolean isNotFloatingPoint(String text) {
		Pattern usernamePattern = Pattern.compile("[^0-9.]+"); //this will probably allow more than 1 '.'
		Matcher usernameMatcher = usernamePattern.matcher(text);
		return (usernameMatcher.find());
	}
	
	public static boolean isEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$"); // Match standard email pattern
    }
	
}
