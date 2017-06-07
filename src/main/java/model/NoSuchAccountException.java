package model;

public class NoSuchAccountException extends Exception {
	private String description;

	public NoSuchAccountException(String description) {
		this.description = description;
	}
}
