package org.buspass.menu;
import java.sql.Connection;
import java.sql.ResultSet;
import org.buspass.connection.Connections;
import org.buspass.user.*;

public class Login {
	
	Admin busadmin;
	User bususer;
	Employee aduser;
	
	public Employee askLogin() {
		//Scanner input = new Scanner(System.in))
		System.out.println("Enter your user Id: ");
		int userId=Integer.parseInt(Menu.userChoice.nextLine());
		System.out.println("Enter ur password");
		String password=Menu.userChoice.nextLine();
		Employee objuser = null; 
		
		try 
		{
			objuser = validateLogin(userId,password);
		} 
		catch(NullPointerException ne)
		{
			System.out.println("Employee object not returned");
			ne.printStackTrace();
		}
		catch(NumberFormatException nfe)
		{
			System.out.println("Input fields format is incorrect");
			nfe.printStackTrace();
		}
		catch (Exception e) 
		{
			System.out.println("Something went wrong during login.");
			e.printStackTrace();
		}
			return objuser;

	}
	
	
	private Employee validateLogin(int userId, String password) throws Exception {
		String type="invalid";
		String statement="Select password,isAdmin from user where userid=" + userId;
		
		Connection connection=Connections.makeConnection(); //making connection to db

		ResultSet result=Connections.sendQuery(connection, statement);  //getting the password for admin
		if(result.next()) {
			if(result.getString(1).equals(password))  //checking if password is correct or not
			{
				if (result.getInt(2)==1)              //checking if it is admin or user
				{	
					type = "admin";
					aduser = new Admin(userId, type);
				}
				else if(result.getInt(2)==0)
				{	
					type = "user";
					aduser = new User(userId, type); 
				}
				else
				{
					System.out.println("Invalid usertype!");
				}
			}
			else
				System.out.println("The password is incorrect. Please retry with correct password.");
		}		
		Connections.closeConnection();
		return aduser;
	}
	

}