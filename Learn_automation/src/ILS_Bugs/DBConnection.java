package ILS_Bugs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public static Connection conn 	= null;
	
	public static Connection establishMySQLConnection(){
		if(conn==null){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://192.168.57.101:3306/bugs","readonly","readonly");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		}
		return conn;
	}
}
