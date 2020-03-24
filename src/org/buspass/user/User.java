/**
 * 
 */
package org.buspass.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.buspass.connection.Connections;

/**
 * @author missandh
 *
 *user table columns:
 *		userid int(11) AI PK 
 *		password varchar(20) 
 *		name char(20) 
 *		address varchar(30) 
 *		phonenumber int(20) 
 *		emailid varchar(30) 
 *		isAdmin tinyint(1)
 */
public class User {
	
//	int userid;
//	String password;
//	String name;
//	String address;
//	int phonenumber;
//	String emailid;
//	int isAdmin;
//	
	public void resetPassword (int userid, String passwd)
	{
		//Reset password for user
		String uppass = "update user set password= \'" + passwd + "\' where userid = " + userid +";";
		if(Connections.sendStatement(uppass))
			System.out.println("Successfully reset the password for user: " + userid);
	}
	
	public void updateContactDetails (int userid, String address, int phonenumber, String emailid ) 
	{
		//Update contact details for user
		String updetails = "update user set address = \'" + address + "\', phonenumber = " + phonenumber + ", emailid=\'" + emailid +"\' where userid = " + userid +";";
		if(Connections.sendStatement(updetails))
			System.out.println("Successfully updated the contact details for the user: " + userid);
	}
	
	public boolean secondaryAuthentication(int userid, int phonenumber, String address)
	{
		boolean authpass = false;
		//compare the entered phone number and address with the userid from user table
		//reset password to password of user choice in the handler after successful authentication
		return authpass;
	}
	
	public void viewBuspassSnapshot(int userid)
	{
		
		/**
		 * Table: buspassmaster
		 	* Columns: buspassid int(11) AI PK,  userid int(11), routeid int(11),  
		 	* stopid int(11), scheduleid int(11), buspassstatus char(30), monthlyamount int(20)
		 	* 
		 	* Fields fetched in the snapshot userid, buspassid, routeid, buspassstatus, monthlyamount, from buspassmaster
		 	*  name from user, stopname from stop, scheduletime from schedule
		 */
		
		String table = "buspassmaster";
		ResultSet rset = null;

		String squery = "select b.userid, buspassid, b.routeid, buspassstatus, monthlyamount, u.name, s.stopname, sc.scheduletime from "+ 
						table +" as b, user as u, stop as s, schedule as sc " + 
				" where b.userid = u.userid AND b.stopid = s.stopid and b.scheduleid = sc.scheduleid and b.userid = " + userid + ";";
		
		//Connect to the database
		Connection dbcon = Connections.makeConnection();

		try 
		{
			rset = Connections.sendQuery(dbcon, squery);
			System.out.println("\n\nHere is the bus pass snapshot for user:  ");
			
			while (rset.next())
			{	 
                // Let's fetch the data by column numbers
				
				System.out.println("\nUser ID: " + rset.getInt(1));
				System.out.println("\n***************************************");
				System.out.println("\n\tBus Pass ID :"+rset.getInt(2)+ 
									"\n\tName: " + rset.getString(6)+
									"\n\tBus Pass Status: " + rset.getString(4)+ 
									"\n\tRoute ID: " + rset.getInt(3) + 
									"\n\tStop Name: " + rset.getString(7)+
									"\n\tSelected Time Slot: " + rset.getString(8)+
									"\n\tMonthly Amount: " + rset.getInt(5));
				System.out.println("\n***************************************");

			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		Connections.closeConnection();
	}
	
    public static void main(String args[]) {
    	User bususer = new User();
    	bususer.viewBuspassSnapshot(100);
    	bususer.resetPassword(100, "lokesh123");
    	bususer.updateContactDetails(100, "Gachibowli", 123454310, "lokesh@amazon.com");
    }

    
/**
 * User Module
1.  View all the routes, their stops, type of vehicles, no of vehicles on each route
						   display all routes (shows routes and stops), QUERY (type and no. of vehicles in each route)						 
    public void viewAllDetails()

2. Request cancel /suspend bus pass / (reactivate) : directly modify buspass master. (Auto approved)
requestCancelBusPass()
requestSuspendBusPass()
requestReactivateBusPass()
based on the user handler it will take parameter requesttype

requestAlterBusPass(String reqtype, int userid, int routeid, int scheduleid)

4. feedback 
giveFeedback()

5. view and update user details.
updateContactDetails()
					 
6. get snapshot of details. get all user related details.		
viewBuspassSnapshot()

(Tentative)3. Request to change to another route -> check if new route has capacity, and change, else prompt change is not possible. (auto approved)
 */
    
    public void viewAllDetails()
    {
    	
    }
	
}
