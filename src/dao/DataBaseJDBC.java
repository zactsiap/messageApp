package dao;

/**
 * @author Zacharias Tsiaparas
 *
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import message.Menus;

public class DataBaseJDBC {

	private static final String MYSQLURL = "jdbc:mysql://localhost/bootcampdb?autoReconnect=true&useSSL=false";
	private static final String USERNAME = "root";
	private static final String PASS = "root";

	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement stm = null;
	private Statement statement = null;
	private String userName;
	private String password;
	private String userId;
	private String sqlselect1 = "USE Project1";

	public DataBaseJDBC() {
		try {
			connectDB();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}// End of constructor

	// ==============================================================================

	public DataBaseJDBC(String userName, String password) {
		this.userName = userName;
		this.password = password;
		try {
			connectDB();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}// End of constructor

	// ==============================================================================

	private void connectDB() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			System.out.println("==================");
			System.out.println("|DataBase connect|");
			System.out.println("==================");
		} catch (SQLException ex) {
			Logger.getLogger(DataBaseJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// End of connectDB()

	// ==============================================================================

	public String checkPassword(String userName) {
		try {
			statement = con.createStatement();
			statement.executeUpdate(sqlselect1);
			String sql = "SELECT PASSWORD FROM REGISTRATION WHERE USERNAME='" + userName + "';";
			rs = statement.executeQuery(sql);
			rs.next();
			return rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return "|  The user does not exist.  |";
		}
	}// End of checkPassword()

	// ==============================================================================

	public boolean checkUserName(String userName) {
		ArrayList<String> userNames = new ArrayList<>();
		try {
			statement = con.createStatement();
			statement.executeUpdate(sqlselect1);
			String sql = "SELECT USERNAME FROM REGISTRATION ";
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				userNames.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (String e : userNames) {
			if (userNames.contains(userName)) {
				return true;
			}
		}
		return false;
	}// End of checkUserName()

	// ==============================================================================

	public void insertTableMessages(String date, String sender, String receiver, String messageData) {
		userId = getUserId(receiver);
		if (userId != "null") {
			String sql = "INSERT INTO MESSAGES(DATE,SENDER,RECEIVER,MESSAGE_DATA,USER_ID)VALUES ('" + date + "', '"
					+ sender + "', '" + receiver + "','" + messageData + "'," + userId + ")";
			try {
				statement = con.createStatement();
				statement.executeUpdate(sqlselect1);
				statement.executeUpdate(sql);
				System.out.println("===========================");
				System.out.println("|The message was sended ! |");
				System.out.println("===========================");
				new Menus(getUserName(), getPassword(), getUserRole(getUserName()));
				// menu.userMenu(this.userName, this.password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}// End of insertTableMessages()

	// ==============================================================================

	public String getMessageId() {
		try {
			statement = con.createStatement();
			statement.executeUpdate(sqlselect1);
			String sqlselect = "SELECT COUNT(ID_MESSAGE) FROM MESSAGES";
			rs = statement.executeQuery(sqlselect);
			rs.next();
			String idMessage = rs.getString(1);
			int newId = Integer.parseInt(idMessage);
			newId += 1;
			String id_Message = Integer.toString(newId);
			return id_Message;
		} catch (SQLException e) {
			e.printStackTrace();
			return "1";
		}
	}// End of getMessageId()

	// ==============================================================================

	public String getUserId(String receiver) {
		String sqlselect = "SELECT ID_REGIST FROM REGISTRATION WHERE USERNAME='" + receiver + "'";
		try {
			statement = con.createStatement();
			statement.executeUpdate(sqlselect1);
			rs = statement.executeQuery(sqlselect);
			rs.next();
			String id_receiver = rs.getString(1);
			if (id_receiver != null) {
				return id_receiver;
			} else {
				return "null";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "null";
		}
	}// End of getUserId()

	// ==============================================================================

	public void deleteTableMessages(String sender, String receiver) {
		try {
			String sqlselect2 = "DELETE FROM MESSAGES WHERE SENDER='" + sender + "' AND RECEIVER='" + receiver + "';";
			statement = con.createStatement();
			statement.executeUpdate(sqlselect1);
			statement.executeUpdate(sqlselect2);
			System.out.println("=====================================================");
			System.out.println("|All the messages from " + receiver + " were deleted.|");
			System.out.println("=====================================================");
		} catch (SQLException ex) {
			Logger.getLogger(DataBaseJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// End of deleteTableMessages()

	// ==============================================================================

	public void deleteMessage(String id) {
		try {
			String sqlselect2 = "DELETE FROM MESSAGES WHERE ID_MESSAGE='" + id + "';";
			statement = con.createStatement();
			statement.executeUpdate(sqlselect1);
			statement.executeUpdate(sqlselect2);
			System.out.println("==========================");
			System.out.println("|The message was deleted.|");
			System.out.println("==========================");
		} catch (SQLException ex) {
			Logger.getLogger(DataBaseJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// End of deleteMessage()

	// ==============================================================================

	public String readTableMessages(String userName) {
		String sqlselect = "SELECT * FROM MESSAGES WHERE RECEIVER='" + userName + "'";
		try {
			statement = con.createStatement();
			statement.executeUpdate(sqlselect1);
			rs = statement.executeQuery(sqlselect);
			StringBuilder message = new StringBuilder();
			while (rs.next()) {
				String id = rs.getString(1);
				String date = rs.getString(2);
				String sender = rs.getString(3);
				String receiver = rs.getString(4);
				String messageDb = rs.getString(5);
				message.append("ID: ");
				message.append(id + "\n");
				message.append("Date: ");
				message.append(date + "\n");
				message.append("Sender: ");
				message.append(sender + "\n");
				message.append("Receiver: ");
				message.append(receiver + "\n");
				message.append("Message Data: ");
				message.append(messageDb + "\n\n");
			}
			return message.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return "null";
		}
	}// End of readTableMessages()

	// ==============================================================================

	public void editDataBase(String id, String date, String newMessage) {
		String sqlselect = "UPDATE MESSAGES SET DATE ='" + date + "', MESSAGE_DATA = '" + newMessage
				+ "' WHERE ID_MESSAGE='" + id + "'";
		try {
			statement = con.createStatement();
			statement.executeUpdate(sqlselect1);
			statement.executeUpdate(sqlselect);
			System.out.println("============================");
			System.out.println("|The new message was edited|");
			System.out.println("============================");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// End of editDataBase()

	// ==============================================================================

	public String getUserRole(String user) {
		String sqlselect = "SELECT ROLE FROM REGISTRATION WHERE USERNAME='" + user + "'";
		try {
			statement = con.createStatement();
			statement.executeUpdate(sqlselect1);
			rs = statement.executeQuery(sqlselect);
			rs.next();
			String receiverRole = rs.getString(1);
			if (receiverRole != null) {
				return receiverRole;
			} else {
				return "null";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "null";
		}
	}// End of getUserRole()

	// ==============================================================================

	public void createUserIntoDB(String newUserName, String newPassword, String role) {
		String sql = "INSERT INTO REGISTRATION(USERNAME,PASSWORD,ROLE,EXIST) VALUES ('" + newUserName + "', '"
				+ newPassword + "', '" + role + "','YES')";
		try {
			statement.executeUpdate(sqlselect1);
			statement.executeUpdate(sql);
			System.out.println("The " + newUserName + " were created");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// End of createUserIntoDB()

	// ==============================================================================

	public void deleteUserFromDB(String user) {
		try {
			String sqlselect2 = "UPDATE REGISTRATION SET EXIST = 'NO' WHERE USERNAME='" + user + "';";
			statement = con.createStatement();
			statement.executeUpdate(sqlselect1);
			statement.executeUpdate(sqlselect2);
			System.out.println("================================");
			System.out.println("|The " + user + " was deleted.|");
			System.out.println("================================");
		} catch (SQLException ex) {
			Logger.getLogger(DataBaseJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// End of deleteUserFromDB()

	// ==============================================================================

	public void updateUserFromDB(int i, String user, String change) {
		String sql = null;
		try {
			switch (i) {
			case 1:
				// System.out.println("What is the new User Name?");
				sql = "UPDATE REGISTRATION set USERNAME='" + change + "' WHERE USERNAME='" + user + "'";
				break;
			case 2:
				sql = "UPDATE REGISTRATION set PASSWORD='" + change + "' WHERE USERNAME='" + user + "'";
				break;
			case 3:
				sql = "UPDATE REGISTRATION set ROLE='" + change + "' WHERE USERNAME='" + user + "'";
				break;
			}
			statement.executeUpdate(sqlselect1);
			stm = con.prepareStatement(sql);
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// End of updateUserFromDB()

	// ==============================================================================

	public Boolean getExist(String user) {
		String result = null;
		String sql = null;
		try {
			sql = "SELECT EXIST FROM REGISTRATION WHERE USERNAME='" + user + "';";
			statement = con.createStatement();
			statement.executeUpdate(sqlselect1);
			rs = statement.executeQuery(sql);
			rs.next();
			result = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (result.equals("YES")) {
			return true;
		} else {
			return false;
		}
	}// End of getExist()

	// ==============================================================================

	public void closeDB() {
		try {
			if (rs != null) {
				rs.close();
				System.out.println("ResultSet closed!");
			}
			if (statement != null) {
				statement.close();
				System.out.println("Statement closed!");
			}
			if (con != null) {
				con.close();
				System.out.println("Connection closed!");
			}
			System.out.println("All closed!");
		} catch (Exception e) {
		}
	}// End of closeDB()

	// ==============================================================================

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

}// End of Class DataBaseJDBC
