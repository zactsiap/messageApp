package message;

import java.util.Scanner;

/**
 * @author Zacharias Tsiaparas
 *
 */
public class Menus {

	private String userName = null;
	private String password = null;
	private String command = null;
	private User user;
	private String role;

	Scanner scanner = new Scanner(System.in);

	public Menus() {
		starterMenu();
	}// End of constructor

	// ==============================================================================

	public Menus(String userName, String password, String role) {
		this.userName = userName;
		this.password = password;
		this.role = role;
		if (getUserName().equals("admin")) {
			adminMenu(getUserName(), getPassword());
		} else {
			user = new User(getUserName(), getPassword());
			switch (getRole()) {
			case "A":
				userMenuA(getUserName(), getPassword());
				break;
			case "B":
				userMenuB(getUserName(), getPassword());
				break;
			case "C":
				userMenuC(getUserName(), getPassword());
				break;
			}
		}
	}// End of constructor

	// ==============================================================================

	private void starterMenu() {
		helpMenuStart();
		do {
			System.out.print(">> ");
			command = scanner.nextLine();
			switch (command) {
			case "login":
				logInMenu();
				break;
			case "exit":
				System.exit(0);
				break;
			case "help":
				helpMenuStart();
				break;
			default:
				System.out.println("================================");
				System.out.println("|Remember For Help type:  help |");
				System.out.println("================================");
				break;
			}
		} while (true);
	}// End of starterMenu()

	// ==============================================================================

	private void helpMenuStart() {
		System.out.println("====================================================");
		System.out.println("|  Hello fellow user what would you like to do?    |");
		System.out.println("====================================================");
		System.out.println("|If you want to login to your account type:   login|");
		System.out.println("|If you want to log out of application type:   exit|");
		System.out.println("|Remember For Help type:                       help|");
		System.out.println("====================================================");
	}// End of helpMenuStart()

	// ==============================================================================

	private void logInMenu() {
		System.out.println("==========================");
		System.out.println("|Give me your User Name: |");
		System.out.println("==========================");
		System.out.print(">> ");
		userName = scanner.nextLine();
		System.out.println("============================");
		System.out.println("|Now give me your Password: |");
		System.out.println("=============================");
		System.out.print(">> ");
		password = scanner.nextLine();
		new LogIn(userName, password);
	}// End of logInMenu()

	// ==============================================================================

	private void userMenuA(String userName, String password) {
		do {
			helpUserMenuA();
			System.out.print(">> ");
			command = scanner.nextLine();
			switch (command) {
			case "read":
				user.readFromDataBase();
				break;
			case "send":
				user.typeMessage();
				break;
			case "logout":
				user.closeDB();
				starterMenu();
				break;
			case "help":
				helpUserMenuA();
				break;
			default:
				System.out.println("================================");
				System.out.println("|Remember For Help type:  help |");
				System.out.println("================================");
				break;
			}
		} while (true);
	}// End of userMenuA()

	// ==============================================================================

	private void helpUserMenuA() {
		System.out.println("=====================================================");
		System.out.println("|            What would you like to do?             |");
		System.out.println("=====================================================");
		System.out.println("|If you want to See your messages type:        read |");
		System.out.println("|If you want to Sent a message type:           send |");
		System.out.println("|If you want to Log Out type:                logout |");
		System.out.println("|Remember For Help type:                       help |");
		System.out.println("=====================================================");
	}// End of helpUserMenuA()

	// ==============================================================================

	private void userMenuB(String userName, String password) {
		do {
			helpUserMenuB();
			System.out.print(">> ");
			command = scanner.nextLine();
			switch (command) {
			case "send":
				user.typeMessage();
				break;
			case "read":
				user.readFromDataBase();
				break;
			case "edit":
				user.editMessage();
				break;
			case "logout":
				user.closeDB();
				starterMenu();
				break;
			case "help":
				helpUserMenuB();
				break;
			default:
				System.out.println("================================");
				System.out.println("|Remember For Help type:  help |");
				System.out.println("================================");
				break;
			}
		} while (true);
	}// End of userMenuB()

	// ==============================================================================

	private void helpUserMenuB() {
		System.out.println("=====================================================");
		System.out.println("|           What would you like to do?              |");
		System.out.println("=====================================================");
		System.out.println("|If you want to Sent a message type:           send |");
		System.out.println("|If you want to See your messages type:        read |");
		System.out.println("|If you want to Edit your messages type:       edit |");
		System.out.println("|If you want to Log Out type:                logout |");
		System.out.println("|Remember For Help type:                       help |");
		System.out.println("=====================================================");
	}// End of helpUserMenuB()

	// ==============================================================================

	private void userMenuC(String userName, String password) {
		do {
			helpUserMenuC();
			System.out.print(">> ");
			command = scanner.nextLine();
			switch (command) {
			case "send":
				user.typeMessage();
				break;
			case "read":
				user.readFromDataBase();
				break;
			case "edit":
				user.editMessage();
				break;
			case "logout":
				user.closeDB();
				starterMenu();
				break;
			case "delete_all":
				user.deleteAllMessagesFromDataBase();
				break;
			case "delete":
				user.deleteMessage();
				break;
			case "help":
				helpUserMenuC();
				break;
			default:
				System.out.println("Remember For Help type: help");
				break;
			}
		} while (true);
	}// End of userMenuC()

	// ==============================================================================

	private void helpUserMenuC() {
		System.out.println("====================================================================");
		System.out.println("|                  What would you like to do?                      |");
		System.out.println("====================================================================");
		System.out.println("|If you want to Sent a message type:                          send |");
		System.out.println("|If you want to See your messages type:                       read |");
		System.out.println("|If you want to Edit your messages type:                      edit |");
		System.out.println("|If you want to Delete all messages from a User type:   delete_all |");
		System.out.println("|If you want to Delete a message type:                      delete |");
		System.out.println("|If you want to Log Out type:                               logout |");
		System.out.println("|Remember For Help type:                                      help |");
		System.out.println("====================================================================");
	}// End of helpUserMenuC()

	// ==============================================================================

	private void adminMenu(String userName, String password) {
		Admin admin = new Admin(userName, password);
		do {
			helpAdminMenu();
			System.out.print(">> ");
			command = scanner.nextLine();
			switch (command) {
			case "send":
				admin.typeMessage();
				break;
			case "read":
				admin.readFromDataBase();
				break;
			case "logout":
				admin.closeDB();
				starterMenu();
				break;
			case "delete_all":
				admin.deleteAllMessagesFromDataBase();
				break;
			case "delete":
				admin.deleteMessage();
				break;
			case "update":
				admin.updateUser();
				break;
			case "del_user":
				admin.deleteUser();
				break;
			case "new_user":
				admin.createUser();
				break;
			case "help":
				helpAdminMenu();
				break;
			default:
				System.out.println("Remember For Help type:  help");
				break;
			}
		} while (true);
	}// End of adminMenu()

	// ==============================================================================

	private void helpAdminMenu() {
		System.out.println("====================================================================");
		System.out.println("|           Hello Admin what would you like to do?                 |");
		System.out.println("====================================================================");
		System.out.println("|If you want to Sent a message type:                          send |");
		System.out.println("|If you want to See your messages type:                       read |");
		System.out.println("|If you want to Delete all messages from a User type:   delete_all |");
		System.out.println("|If you want to Delete a message type:                      delete |");
		System.out.println("|If you want to Update a user type:                         update |");
		System.out.println("|If you want to Delete a user type:                       del_user |");
		System.out.println("|If you want to Create a new user type:                   new_user |");
		System.out.println("|If you want to Log Out type:                               logout |");
		System.out.println("|Remember For Help type:                                      help |");
		System.out.println("====================================================================");
	}// End of helpAdminMenu()

	// ==============================================================================

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

}// End of Class Menus
