package org.buspass.user;

import java.sql.Connection;
import java.sql.ResultSet;

import org.buspass.connection.Connections;
import org.buspass.menu.Menu;
import org.buspass.route.Route;

public class Visitor extends Employee {

	/*method for applying for bus pass
	 * asks for visitor details
	 * also gives applicationId to user
	 */
	
    /**
     * Constructors
     */
	
	public Visitor(int userid, String usertype) 
	{
		this.userid = userid;
		this.usertype = usertype;
	}

	public Visitor() 
	{
		//Do nothing. Just create the object.
	}
	
	public void applyBusPass() 
	{
		String name,password,address,emailId;
		int phonenumber,routeid,applicationid;
		System.out.println("\nHi, For applying a bus pass, we need you to enter contact details");
		System.out.println("\n Please enter your Name : ");
		Menu.USERCHOICE.nextLine();
		name = Menu.USERCHOICE.nextLine();
		System.out.println("\n Please enter your Password:  ");
		password = Menu.USERCHOICE.next();
		System.out.println("\n Please enter your address: ");
		Menu.USERCHOICE.nextLine();
		address = Menu.USERCHOICE.nextLine();
		System.out.println("\n Please enter your Phone number:  ");
		// Menu.userChoice.nextLine();
		phonenumber =Menu.USERCHOICE.nextInt();
		System.out.println("\n Please enter your Email id:  ");
		emailId = Menu.USERCHOICE.next();
		System.out.println("\n\nYour basic details have been entered, please enter routeid in which you wish to commute: ");
		routeid = Menu.USERCHOICE.nextInt();
		Route route = new Route();
		route.updateApplication(routeid);
		applicationid = route.getApplicationId();
		addVisitor(applicationid, name, password, address, phonenumber, emailId);

		System.out.println("Your application id is "+applicationid);
		System.out.println("Please wait for admin approval");
	}

	public  void viewOccupancy()
	{	  
		int available=0;
		float percent=0.0f;
		System.out.println("\nSelect route id :");
		//Scanner input = new Scanner(System.in);
		int routeId = Menu.USERCHOICE.nextInt();
		//Bus bus = new Bus();
		Route route = new Route();
		route.getBussesInRoute(routeId);
		int total = route.totalRouteCapacity(routeId);
		int filled = route.filledCapacityInRoute(routeId);
		available=total-filled;
		System.out.println("\nAvailable seats: "+available+" ,Occupied Seats: "+filled);
		//System.out.println((float)filled/total);
		percent = (float)filled/total;
		System.out.println("\n Percentage occupancy = "+ Math.round(percent*100) + "%");
	}
	
	public void addVisitor(int applicationid, String name, String password, String address, int phonenumber, String emailid) 
	{    
		String query = "insert into visitor (visitorid,applicationid,name,password,address,phonenumber,emailid) values (default,"+applicationid+",\'"+name+"\',\'"+password+"\',\'"+address+"\',"+phonenumber+",\'"+emailid+"\' ) ;";
	    
		try 
		{
	           Connections.sendStatement(query);
	    }
	    catch(Exception e) 
		{
	           System.out.println("Something went wrong!");
	    }
	}

	public int visitorToUser( int applicationid) {
	    String query = "select name,password,address,phonenumber,emailid from visitor where applicationid = "+applicationid;
	    Connection con = Connections.makeConnection();
	    String name = null,password=null,address=null,emailid=null;
	    int phonenumber=0,userid=0;
	    try 
	    {
	    	ResultSet rs = Connections.sendQuery(con,query);
	        while(rs.next()) {
	        	name = rs.getString("name");
	            password = rs.getString("password");
	            address = rs.getString("address");
	            phonenumber = rs.getInt("phonenumber");
	            emailid = rs.getString("emailid");
	    }
	           String query1 = "insert into user (userid,password,name,address,phonenumber,emailid,isAdmin) values (default,\'"+password+"\',\'"+name+"\',\'"+address+"\',"+phonenumber+",\'"+emailid+"\',false)";
	           Connections.sendStatement(query1);
	           String query2 = "select max(userid) from user;";
	           ResultSet rs1 = Connections.sendQuery(con, query2);
	           while(rs1.next()) {
	                 userid = rs1.getInt("max(userid)"); 
	           }      
	    }
	    catch(Exception e) 
	    {
	           e.printStackTrace();
	    }
	    return userid;
	}
}