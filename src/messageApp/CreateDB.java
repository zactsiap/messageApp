package message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Zacharias Tsiaparas
 *
 */
public class CreateDB {

	private static final String MYSQLURL = "jdbc:mysql://localhost/bootcampdb";
	private static final String USERNAME = "root";
	private static final String PASS = "agent900";

	Connection con = null;
	ResultSet rs = null;
	PreparedStatement stm = null;
	String sqlselect;
	String sqlinsert;
	LocalDate now = LocalDate.now();
	Statement statement = null;
	String messageId;
	String userId;
	String sqlselect1 = "USE Project1";

	/*
	 * ==========================================================================
	 * ========================CREATE A DATABASE=================================
	 * ==========================================================================
	 * 
	 */
	public CreateDB() throws ClassNotFoundException {
		connectDB();
		createDB();
		createTableRegistration();
		createTableMessages();
	}// End of constructor

	// ==============================================================================

	private void connectDB() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			System.out.println("DataBase connect");
		} catch (SQLException ex) {
			Logger.getLogger(DataBaseJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// End of connectDB()

	// ==============================================================================

	private void createDB() {
		try {
			sqlselect = "CREATE DATABASE IF NOT EXISTS Project1 DEFAULT CHARACTER SET utf8";
			statement = con.createStatement();
			statement.executeUpdate(sqlselect);
			System.out.println("DataBase create");
		} catch (SQLException ex) {
			Logger.getLogger(DataBaseJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// End of createDB()

	// ==============================================================================

	private void createTableRegistration() {
		try {			
			String sqlselect2 = "DROP TABLE IF EXISTS REGISTRATION";
			System.out.println("Creating table REGISTRATION in given database...");
			statement = con.createStatement();
			String sql = "CREATE TABLE REGISTRATION " + "(ID_REGIST INTEGER not NULL AUTO_INCREMENT, "
					+ " USERNAME VARCHAR(25), " + " PASSWORD VARCHAR(25), " + " ROLE ENUM('A','B','C'), "
					+ " EXIST ENUM('YES','NO')," + " PRIMARY KEY ( ID_REGIST ))";
			statement.executeUpdate(sqlselect1);
			statement.executeUpdate(sqlselect2);
			statement.executeUpdate(sql);
			System.out.println("Created table in given database...");
			insertTableRegistration();
		} catch (SQLException ex) {
			Logger.getLogger(DataBaseJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// End of createTableRegistration()

	// ==============================================================================

	private void insertTableRegistration() {
		String sql = "INSERT INTO REGISTRATION VALUES (1, 'admin', 'aDmI3$', 'C','YES')";
		try {
			statement.executeUpdate(sqlselect1);
			statement.executeUpdate(sql);
			System.out.println("The admin were created");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// End of insertTableRegistration()

	// ==============================================================================

	private void createTableMessages() {
		try {
			String sqlselect2 = "DROP TABLE IF EXISTS MESSAGES";
			System.out.println("Creating table MESSAGES in given database...");
			statement = con.createStatement();
			String sql = "CREATE TABLE MESSAGES " + "(ID_MESSAGE int not NULL AUTO_INCREMENT, " + " DATE DATETIME, "
					+ " SENDER VARCHAR(20), " + " RECEIVER VARCHAR(20), " + " MESSAGE_DATA VARCHAR(250), "
					+ " USER_ID INT, " + " PRIMARY KEY (ID_MESSAGE)," + "CONSTRAINT FK_USER_ID "
					+ " FOREIGN KEY (USER_ID) REFERENCES REGISTRATION(ID_REGIST)" + "ON DELETE CASCADE)";
			statement.executeUpdate(sqlselect1);
			statement.executeUpdate(sqlselect2);
			statement.executeUpdate(sql);
			System.out.println("Created table in given database...");
			insertTableMessages();
		} catch (SQLException ex) {
			Logger.getLogger(DataBaseJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// End of createTableMessages()

	// ==============================================================================

	private void insertTableMessages() {
		String sql = "INSERT INTO MESSAGES VALUES (1, '2018-04-15 12:00:00', 'admin', 'admin','hello ',1)";
		try {
			statement.executeUpdate(sqlselect1);
			statement.executeUpdate(sql);
			System.out.println("The dummy message of user were created");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// End of insertTableMessages()

	// ==============================================================================

}// End of Class CreateDB
