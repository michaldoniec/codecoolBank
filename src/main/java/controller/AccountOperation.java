package controller;

import model.AbstractAccount;

public interface AccountOperation {
	public void deposit(long amount);
	public void withdraw(long amount);
}
