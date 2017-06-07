package model;

/**
 * Created by michal on 07.06.17.
 */
public class NoSuchAccountException extends Exception {
	private String description;

	public NoSuchAccountException(String description) {
		this.description = description;
	}
}
