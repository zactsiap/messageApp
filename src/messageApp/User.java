package message;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * @author Zacharias Tsiaparas
 *
 */
public class User extends DataBaseJDBC {

	private String userName;
	private String password;
	private String date, sender, receiver, messageData, userId;
	private String line = null;
	private FileWriter fileWriter;
	private LocalDate now = LocalDate.now();
	private LocalTime time = LocalTime.now();
	private String fileName;
	private File f;
	private final int messageLength = 250;
	private int i = 0;
	Scanner scanner;

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
		fileName = ".\\messages" + "\\message_" + getUserName() + "_" + now + ".txt";
		f = new File(fileName);
		scanner = new Scanner(System.in);
	}// End of constructor

	// ==============================================================================

	protected void typeMessage() {
		String input;
		System.out.println("==========================================");
		System.out.println("|Who user do you want to send a message ?|");
		System.out.println("==========================================");
		System.out.print(">> ");
		input = scanner.nextLine();
		this.receiver = input;
		if (checkUserName(receiver)) {
			writeMessage();
		} else {
			System.out.println("============================================================");
			System.out.println("|The user who you want to send the message does not exist! |");
			System.out.println("============================================================");
		}
	}// End of typeMessage()

	// ==============================================================================

	protected void writeMessage() {
		String input;
		System.out.println("=================================================");
		System.out.println("|Please enter your message.                     |");
		System.out.println("|Attention! Must be less than to 250 characters.|");
		System.out.println("=================================================");
		System.out.print(">> ");
		do {
			input = scanner.nextLine();
			messageData = input;
			if (messageData.length() <= messageLength) {
				break;
			}
		} while (true);
		date = now.toString() + " " + time.toString();
		sender = getUserName();
		writeToDatabase();
		writeMessageTofile("newMessage");
	}// End of writeMessage()

	// ==============================================================================

	protected void writeToDatabase() {
		insertTableMessages(this.date, this.sender, this.receiver, this.messageData);
	}// End of writeToDatabase()

	// ==============================================================================

	protected void writeMessageTofile(String message) {
		try {
			fileWriter = new FileWriter(f);
			if (message.equals("newMessage")) {
				fileWriter.append("Date: " + date + "\n");
				fileWriter.append("Sender: " + sender + "\n");
				fileWriter.append("Receiver: " + receiver + "\n");
				fileWriter.append("Message: " + messageData + "\n");
			} else {
				fileWriter.append(message);
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// End of writeMessageTofile()

	// ==============================================================================

	protected void readFromDataBase() {
		date = now.toString() + " " + time.toString();
		String message = readTableMessages(getUserName());
		writeMessageTofile(message);
		readMessage();
	}// End of readFromDataBase()

	// ==============================================================================

	protected void readMessage() {
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}// End of readMessage()

	// ==============================================================================

	protected void editMessage() {
		readFromDataBase();
		System.out.println("==========================================================");
		System.out.println("|Please give me the ID of message which you like to edit.|");
		System.out.println("==========================================================");
		System.out.print(">> ");
		String id = scanner.nextLine();
		System.out.println("==============================");
		System.out.println("|Please type the new message.|");
		System.out.println("==============================");
		System.out.print(">> ");
		String newMessage = scanner.nextLine();
		date = now.toString() + " " + time.toString();
		editDataBase(id, date, newMessage);
	}// End of editMessage()

	// ==============================================================================

	protected void deleteMessage() {
		String input;
		readFromDataBase();
		System.out.println("============================================================");
		System.out.println("|Please give me the ID of message which you like to delete.|");
		System.out.println("============================================================");
		System.out.print(">> ");
		String id = scanner.nextLine();
		System.out.println("=====================================================");
		System.out.println("|Are you sure about deleting? There is no comeback. |");
		System.out.println("|If Yes type:     y                                 |");
		System.out.println("|If No type:      n                                 |");
		System.out.println("=====================================================");
		System.out.print(">> ");
		input = scanner.nextLine();
		if (input.equals("y") || input.equals("Y")) {
			deleteMessage(id);
		}
	}// End of deleteMessage()

	// ==============================================================================

	protected void deleteAllMessagesFromDataBase() {
		String input;
		scanner = new Scanner(System.in);
		System.out.println("===================================================");
		System.out.println("|Who user do you want to delete your message from?|");
		System.out.println("===================================================");
		System.out.print(">> ");
		input = scanner.nextLine();
		receiver = input;
		System.out.println("=====================================================");
		System.out.println("|Are you sure about deleting? There is no comeback. |");
		System.out.println("|If Yes type:     y                                 |");
		System.out.println("|If No type:      n                                 |");
		System.out.println("=====================================================");
		System.out.print(">> ");
		input = scanner.nextLine();
		if (input.equals("y") || input.equals("Y")) {
			deleteTableMessages(getUserName(), receiver);
		}
	}// End of deleteAllMessagesFromDataBase()

	// ==============================================================================

	public String getUserName() {
		return this.userName;
	}

	public String getPassword() {
		return this.password;
	}

}// End of Class User
