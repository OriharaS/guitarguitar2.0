package DB;

import java.sql.*;


public class DBUtil {
	static Connection conn=null;
	public static Connection getConnection(){
		try{
			Class.forName("org.sqlite.JDBC");	
			conn = DriverManager.getConnection("jdbc:sqlite:guitar.db");
		}catch(ClassNotFoundException e){
			
		}		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}

