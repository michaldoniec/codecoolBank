import controller.MainController;
import model.exception.NoSuchAccountException;

/**
 * Created by michal on 09.06.17.
 */
public class App {

	public static void main(String[] args) throws NoSuchAccountException {
		MainController mainController = new MainController("src/test/resources/testBank.db");
		mainController.getCustomerByLogin("Abc", "Def");
		mainController.viewAllAccounts();
	}
}
