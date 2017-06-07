package model;

/**
 * Created by michal on 07.06.17.
 */
public interface Account {
	public void deposit(long amount);
	public void withdraw(long amount);
	public long getBalance();
	public Integer getAccountId();
}
