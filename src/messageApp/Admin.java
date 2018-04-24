package message;

import java.util.Scanner;

/**
 * @author Zacharias Tsiaparas
 *
 */
public class Admin extends User {

	Scanner scanner = new Scanner(System.in);
	private String newUserName;
	private String newPassword;
	private String role;

	public Admin(String userName, String password) {
		super(userName, password);
	}// End of constructor

	// ==================================================================

	protected void createUser() {
		System.out.println("============================");
		System.out.println("|How will be the User Name?|");
		System.out.println("============================");
		System.out.print(">> ");
		newUserName = scanner.next();
		if (checkUserName(newUserName)) {
			System.out.println("===============================================");
			System.out.println("|That User Name already exist, choose another.|");
			System.out.println("===============================================");
			System.out.print("\n\n");
			createUser();
		}
		System.out.println("===========================");
		System.out.println("|How will be the Password?|");
		System.out.println("===========================");
		System.out.print(">> ");
		newPassword = scanner.next();
		System.out.println("===================================");
		System.out.println("|What role will have the new user?|");
		System.out.println("===================================");
		System.out.print(">> ");
		printRole();
		role = scanner.next();
		createUserIntoDB(newUserName, newPassword, role);
	}// End of createUser()

	// ==================================================================

	protected void deleteUser() {
		String input;
		scanner = new Scanner(System.in);
		System.out.println("=====================================");
		System.out.println("|Which user do you want to delete ? |");
		System.out.println("=====================================");
		System.out.print(">> ");
		input = scanner.nextLine();
		String user = input;
		System.out.println("=====================================================");
		System.out.println("|Are you sure about deleting? There is no comeback. |");
		System.out.println("|If Yes type:     y                                 |");
		System.out.println("|If No type:      n                                 |");
		System.out.println("=====================================================");
		System.out.print(">> ");
		input = scanner.nextLine();
		if (input.equals("y") || input.equals("Y")) {
			deleteUserFromDB(user);
		}
	}// End of deleteUser()

	// ==================================================================

	protected void updateUser() {
		System.out.println("=======================================");
		System.out.println("|Which user would you like to update? |");
		System.out.println("=======================================");
		System.out.print(">> ");
		String user = scanner.next();
		UpdateUserMenu();
		String choise = scanner.next();
		switch (choise) {
		case "name":
			System.out.println("=================================");
			System.out.println("|What will be the new user Name?|");
			System.out.println("=================================");
			System.out.print(">> ");
			String newName = scanner.next();
			if (checkUserName(newName)) {
				System.out.println("===============================================");
				System.out.println("|That User Name already exist, choose another.|");
				System.out.println("===============================================");
				System.out.print("\n\n");
				createUser();
			} else {
				updateUserFromDB(1, user, newName);
			}
			break;
		case "pass":
			System.out.println("====================================");
			System.out.println("|What will be the new user Passwod?|");
			System.out.println("====================================");
			System.out.print(">> ");
			String newPass = scanner.next();
			updateUserFromDB(2, user, newPass);
			break;
		case "role":
			System.out.println("=================================");
			System.out.println("|What will be the new user Role?|");
			System.out.println("=================================");
			System.out.print(">> ");
			printRole();
			String newRoll = scanner.next();
			updateUserFromDB(3, user, newRoll);
			break;
		case "help":
			UpdateUserMenu();
			break;
		default:
			System.out.println("===============================");
			System.out.println("|Remember For Help type:  help|");
			System.out.println("===============================");
			break;
		}
	}// End of updateUser()

	// ==================================================================

	protected void UpdateUserMenu() {
		System.out.println("=======================================");
		System.out.println("|To change User Name type:        name|");
		System.out.println("|To change User Password type:    pass|");
		System.out.println("|To change User Role type:        role|");
		System.out.println("=======================================");
		System.out.print(">> ");
	}// End of UpdateUserMenu()

	// ==================================================================

	protected void printRole() {
		System.out.println("========================================================");
		System.out.println("|                    Remember !                        |");
		System.out.println("========================================================");
		System.out.println("|To View only the transacted data type:              A |");
		System.out.println("|To View and Edit the transacted data type:          B |");
		System.out.println("|To View, Edit and Delete the transacted data type:  C |");
		System.out.println("========================================================");
	}// End of printRole()

}// End of Class Admin
