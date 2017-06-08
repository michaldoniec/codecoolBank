package model;

import java.time.LocalDate;

public class Card {
	private Integer id;
	private String number;
	private CardType cardType;
	private LocalDate validity;
	private long buyingLimit;
	private long cashWithdrawLimit;
	private long limit;
	private AbstractAccount abstractAccount;
	private CardStatus cardStatus;

	public Card(String number, CardType cardType, LocalDate validity, long buyingLimit,
	            long cashWithdrawLimit, long limit, AbstractAccount abstractAccount, CardStatus cardStatus) {
		this.id = null;
		this.number = number;
		this.cardType = cardType;
		this.validity = validity;
		this.buyingLimit = buyingLimit;
		this.cashWithdrawLimit = cashWithdrawLimit;
		this.limit = limit;
		this.abstractAccount = abstractAccount;
		this.cardStatus = cardStatus;
	}

	public Card(Integer id, String number, CardType cardType, LocalDate validity, long buyingLimit,
	            long cashWithdrawLimit, long limit, AbstractAccount abstractAccount, CardStatus cardStatus) {
		this.id = id;
		this.number = number;
		this.cardType = cardType;
		this.validity = validity;
		this.buyingLimit = buyingLimit;
		this.cashWithdrawLimit = cashWithdrawLimit;
		this.limit = limit;
		this.abstractAccount = abstractAccount;
		this.cardStatus = cardStatus;
	}

	public Card(){

	}

	public Integer getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public CardType getCardType() {
		return cardType;
	}

	public LocalDate getValidity() {
		return validity;
	}

	public long getBuyingLimit() {
		return buyingLimit;
	}

	public long getCashWithdrawLimit() {
		return cashWithdrawLimit;
	}

	public long getLimit() {
		return limit;
	}

	public AbstractAccount getAccount() {
		return abstractAccount;
	}

	public CardStatus getCardStatus() {
		return cardStatus;
	}

}
