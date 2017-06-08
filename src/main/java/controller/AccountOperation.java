package controller;

import model.AbstractAccount;

public interface AccountOperation {
	public void deposit(long amount, AbstractAccount account);
	public void withdraw(long amount, AbstractAccount account);
}
