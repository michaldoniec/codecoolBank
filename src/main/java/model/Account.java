package model;

public interface Account {
	public void deposit(long amount);
	public void withdraw(long amount);
	public long getBalance();
	public Integer getAccountId();
}
