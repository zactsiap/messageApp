package message;

import java.util.Scanner;

import dao.DataBaseJDBC;

/**
 * @author Zacharias Tsiaparas
 *
 */
public class LogIn {

	private String userName;
	private String password, passwordCheck, role;
	private DataBaseJDBC dataBase;
	private Boolean userNameCheck;
	private Scanner scanner;

	public LogIn(String userName, String password) {
		this.userName = userName;
		this.password = password;
		dataBase = new DataBaseJDBC();
		checkAutheticationUserName();
	}// End of constructor

	// ==============================================================================

	private void checkAutheticationUserName() {
		userNameCheck = dataBase.checkUserName(getUserName());
		if (userNameCheck && (dataBase.getExist(getUserName()) == true)) {
			this.passwordCheck = dataBase.checkPassword(getUserName());
			checkAutheticationPassword();
		} else {
			System.out.println("================================");
			System.out.println("|The User Name is not correct. |");
			System.out.println("================================");
			userNameRepeat();
		}
	}// End of checkAutheticationUserName()

	// ==============================================================================

	private void userNameRepeat() {
		scanner = new Scanner(System.in);
		System.out.println("==========================");
		System.out.println("|Give me your User Name: |");
		System.out.println("==========================");
		System.out.print(">> ");
		userName = scanner.nextLine();
		setUserName(userName);
		checkAutheticationUserName();
	}// End of userNameRepeat()

	// ==============================================================================

	private void checkAutheticationPassword() {
		if (this.passwordCheck.equals(this.getPassword())) {
			System.out.println("===========================");
			System.out.println("Hello " + getUserName().toString() + " !");
			System.out.println("===========================");
			goToMenu();
		} else {
			System.out.println("==============================");
			System.out.println("|The password is not correct.|");
			System.out.println("==============================");
			passwordRepeat();
		}
	}// End of checkAutheticationPassword()

	// ==============================================================================

	private void passwordRepeat() {
		scanner = new Scanner(System.in);
		System.out.println("=========================");
		System.out.println("|Give me your Password: |");
		System.out.println("=========================");
		System.out.print(">> ");
		password = scanner.nextLine();
		setPassword(password);
		checkAutheticationPassword();
	}// End of passwordRepeat()

	// ==============================================================================

	private void goToMenu() {
		this.role = dataBase.getUserRole(getUserName());
		if (getUserName().equals("admin")) {
			new Menus(getUserName(), getPassword(), getRole());
		} else {
			new Menus(getUserName(), getPassword(), getRole());
		}
	}// End of goToMenu()

	// ==============================================================================

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	// ==============================================================================

}// End of Class LogIn
