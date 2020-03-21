package org.buspass.connection;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
/* * 
 * * To connect to MySQL database running on aws cloud and 
 * * sending a query or statement to the connection and teardown connection. 
 * * @author missandh 
 * */
import java.util.ArrayList; 

public class Connections 
{ 
	// JDBC URL, username and password of MySQL server hosted on aws
	private static final String url = "jdbc:mysql://db-miniproject-buspass.cifdz4wrihfw.us-east-1.rds.amazonaws.com:3306/BusPass?characterEncoding=utf-8"; 
	private static final String user = "admin"; 
	private static final String password = "admin123"; 
	
	// Methods for opening and closing connection with DataBase
	private static Connection con; 

	public static Connection makeConnection() 
	{ 
		try 
		{ 
			// opening database connection to MySQL server 
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password); 
		} 
		catch (SQLException sqlEx) 
		{ 
			sqlEx.printStackTrace(); 
		} catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
			return con;
	}
			
	public static void closeConnection() 
	{		
			//close connection with mysql database here 
			try 
			{ 
				con.close(); 
			} 
			
			catch(SQLException sqlEx) 
			{ 
				sqlEx.printStackTrace(); 					
			} 
		 }
	
	// for executing data retrieval queries
	public static ResultSet sendQuery ( Connection dbcon, String query) 
	{	
		Statement stmt;
		ResultSet rs = null;
		try 
		{ 
			stmt = dbcon.createStatement();
	        rs = stmt.executeQuery(query);
		}
		catch(SQLException sqlEx) 
		{ 
			sqlEx.printStackTrace(); 					
		} 
        return rs;

	}
	
	//* * @author oohprava
	// for executing data manipulation queries
	
	public static boolean sendStatement (String query) 
	{	
		Statement statement;
		boolean flag = false;
		
		con=makeConnection();    //creating database connection
		
		try 
		{ 
			statement = con.createStatement();
	        int result=statement.executeUpdate(query);
	        statement.close();
	        
	        closeConnection();    	//closing database connection
	        
	        if(result>=1)
	        	flag = true;
		}	
		catch(SQLException sqlEx) 
		{ 
			sqlEx.printStackTrace(); 					
		} 
       
		return flag;
	}
	
	
	/* * @author missandh
	 * 
	 * For batch processing of update/insert/delete queries
	 * Parameters: Array of query strings
	 * Returns array list of integer values
	 * 
 
	
	public static ArrayList<Integer> processBatchUpdates ( Connection dbcon, String [] query) 
	{	
		Statement statement;
		ArrayList<Integer> batchresult = new ArrayList<Integer>();
		
		con=makeConnection();    //creating database connection
		
		try 
		{ 
			statement = con.createStatement();
			for(String stupdate : query)
			{
				statement.addBatch(stupdate);
			}
	        int resultarray[] = statement.executeBatch();
	        for (int each : resultarray)
	        {
	        	batchresult.add(each);
	        }
	        statement.close();
	        
	        closeConnection();    	//closing database connection
		}	
		catch(SQLException sqlEx) 
		{ 
			sqlEx.printStackTrace(); 					
		} 
       
		return batchresult;
	}
		* */
}