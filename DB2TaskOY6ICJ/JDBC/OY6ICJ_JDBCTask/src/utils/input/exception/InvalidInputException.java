package utils.input.exception;

public class InvalidInputException extends Exception {
	private static final long serialVersionUID = -4285272789302437223L;
	
	private String message;
	
	public InvalidInputException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
