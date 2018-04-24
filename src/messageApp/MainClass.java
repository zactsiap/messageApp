package message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Zacharias Tsiaparas
 *
 */
public class MainClass {

	private static final String MYSQLURL = "jdbc:mysql://localhost/bootcampdb";
	private static final String USERNAME = "root";
	private static final String PASS = "agent900";
	static Connection con = null;
	static Statement statement = null;
	static ResultSet rs = null;

	public static boolean dbexist() {
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
			System.out.println("DataBase connect");
			// existsDB();
		} catch (SQLException ex) {
			Logger.getLogger(DataBaseJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			statement = con.createStatement();
			String sqlselect1 = "USE Project1";
			statement.executeUpdate(sqlselect1);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}// End of dbexist()

	// ==============================================================================

	public static void closedb() {
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
			System.out.println("All close!");
		} catch (Exception e) {
		}
	}// End of closedb()

	// ==============================================================================

	public static void main(String[] args) {

		while (true) {
			if (dbexist()) {
				closedb();
				new Menus();
			} else {
				try {
					new CreateDB();
					closedb();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}// End of main

}// End of Class MainClass
