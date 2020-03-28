package org.buspass.user;

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
		System.out.println("Your application id is "+applicationid);
		System.out.println("Please wait for admin approval");
		route.visitorToUser(applicationid, name, password, address, phonenumber, emailId);
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
		System.out.println("\n Percentage occupancy = "+percent*100);
	}
}