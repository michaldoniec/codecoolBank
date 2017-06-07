package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TestCard {
	private SavingAccount savingAccount;
	private Card card;
	private CardStatus cardStatus;
	private CardType cardType;
	private LocalDate validity;


	@BeforeEach
	public void createCard() {
		savingAccount =  mock(SavingAccount.class);
		cardStatus = new CardStatus(1,"Activated", "Activated description");
		cardType = new CardType(1, "Debit card", "Debit card description");
		validity = LocalDate.of(2017,1,1);
		card = new Card("1234", cardType, validity, 3000, 1000,
		 4000,
		 savingAccount, cardStatus);
	}

	@Test
	public void testIfCardConstructorWithoutIdCreateCorrectCard() {
		long limit = 4000;
		long buyingLimit = 3000;
		long cashWithdrawLimit = 1000;
		assertAll("Card constructor",
			 () -> assertEquals("1234", card.getNumber()),
			 () -> assertEquals(validity, card.getValidity()),
			 () -> assertEquals(limit, card.getLimit()),
			 () -> assertEquals(buyingLimit, card.getBuyingLimit()),
			 () -> assertEquals(cashWithdrawLimit, card.getCashWithdrawLimit()),
			 () -> assertEquals(savingAccount, card.getAccount()),
			 () -> assertEquals(cardStatus, card.getCardStatus()),
			 () -> assertEquals(cardType, card.getCardType())
		);
	}

	@Test
	public void testIfCardConstructorWithIdCreateCorrectCard() {
		long limit = 4000;
		long buyingLimit = 3000;
		long cashWithdrawLimit = 1000;
		Integer cardId = 1;
		card = new Card(1,"1234", cardType, validity, 3000, 1000,
		 4000,
		 savingAccount, cardStatus);
		assertAll("Card constructor",
		 () -> assertEquals(cardId, card.getId()),
		 () -> assertEquals("1234", card.getNumber()),
		 () -> assertEquals(validity, card.getValidity()),
		 () -> assertEquals(limit, card.getLimit()),
		 () -> assertEquals(buyingLimit, card.getBuyingLimit()),
		 () -> assertEquals(cashWithdrawLimit, card.getCashWithdrawLimit()),
		 () -> assertEquals(savingAccount, card.getAccount()),
		 () -> assertEquals(cardStatus, card.getCardStatus()),
		 () -> assertEquals(cardType, card.getCardType())
		);
	}

}
