package model.exception;

public class NoSuchTransactionInDatabaseException extends Exception {

	public NoSuchTransactionInDatabaseException(String message) {
		super(message);
	}
}
