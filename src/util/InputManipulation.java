package util;

import java.util.regex.Pattern;

import exceptions.PasswordsDontMatchException;

public class InputManipulation {
	
	public static final int minPasswordLength = 8;
	public static final int minNameLength = 4;
	public static final int minEmailLenght = 10;
	public static final int minTeamNameLength = 3; 
	public static final int minTeamAbbreviationLength = 2;
	public static final int maxTeamAbbreviationLentgth = 5;
	private static final String regex = "^[a-zA0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	
	public static boolean isMinLengthPassword(String password) {
		return password.length() >= minPasswordLength;
	}
	
	public static boolean isMinLengthEmail(String email) {
		return email.length() >= minEmailLenght;
	}
	
	public static boolean isValidEmail(String email) {
		return Pattern.matches(regex, email);
	}
	
	public static boolean isValidName(String name) {
		return name.length() >= minNameLength;
	}	
	
	public static String joinPasswords(String password1, String password2) throws PasswordsDontMatchException {
		if(!password1.equals(password2)) {
			throw new PasswordsDontMatchException("passwords are not the same.");
		}
		return password1;
	}
	
	public static String generateHashedPassword(String password) {
		return BCrypt.hashpw(password, util.BCrypt.gensalt());
	}
	
	public static boolean compareToHashedPassword(String originalPassword, String hashedPassword) {
		return BCrypt.checkpw(originalPassword, hashedPassword);
	}
	
	public static boolean isMinTeamNameLength(String teamName) {
		return teamName.length() >= minTeamNameLength;
	}
	
	public static boolean isMinTeamAbbreviationLength(String teamAbbreviation) {
		return teamAbbreviation.length() >= minTeamAbbreviationLength;
	}	
	
	public static boolean isMaxTeamAbbreviationLength(String teamAbbreviation) {
		return teamAbbreviation.length() >= maxTeamAbbreviationLentgth;
	}	
	
}