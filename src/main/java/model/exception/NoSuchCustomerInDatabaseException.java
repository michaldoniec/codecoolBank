package model.exception;


public class NoSuchCustomerInDatabaseException extends Exception {

	public NoSuchCustomerInDatabaseException(String message) {
		super(message);
	}
}
