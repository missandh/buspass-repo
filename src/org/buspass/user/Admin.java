package org.buspass.user;

import java.util.Scanner;

import org.buspass.connection.Connections;
import org.buspass.menu.Menu;
import org.buspass.route.Bus;
import org.buspass.route.Route;

public class Admin extends Employee{
	Route route=new Route();
	Bus bus = new Bus();
	public int userid;
	String usertype;

	/**
	 * @param userid
	 * @param usertype
	 */
	public Admin(int userid, String usertype) {
		super(userid, usertype);
		this.userid = userid;
		this.usertype = usertype;
	}

	public Admin() {
		super();
	}

	public void fetchBusPassApplications() {
        bus.newRequests();
        int selection=0;
        System.out.println("\n\nSelect application id : ");
        //Was selection = Integer.parseInt(Menu.userChoice.nextLine());
        selection = Menu.userChoice.nextInt();
        selectApplication(selection);
        }
 
 public void selectApplication(int selection) {
        int routeid = bus.getapplicationdetails(selection);
        bus.validateBusPassApplication(routeid, selection);
 
 }
	
	
	//method to see the type of vehicles available on all routes/particular route 
	//based on user input
	public  void typeofvehicles() {
		Bus bus = new Bus();
		try (Scanner input = new Scanner(System.in)) {
			System.out.println("\nDo you want to view any specific route: type yes/no ");
			String choice = input.nextLine();
			if (choice.equals("yes")) {
				System.out.println("\nPlease enter routeid : ");
				int routeid = Integer.parseInt(input.nextLine());
				bus.viewTypeCount(routeid);
			}
			else {
				bus.viewTypeCount();
			}
		}
}
	
	//incomplete
	//method to change the route of bus based on busid
	//and routeId of the new route(to which bus need to be assigned)
	public void changeRouteOfBus1() {
		try (Scanner input = new Scanner(System.in)) {
			int filledCapacity=0;
			int totalCapacity=0;
			int busCapacity=0;
			int availableCapacity=0;
			System.out.println("Enter the busId whose route you want to change: ");
			int busId=Integer.parseInt(input.nextLine());
			System.out.println("Enter the Route ID to which you want to assign the bus " + busId);
			int routeId=Integer.parseInt(input.nextLine());
			filledCapacity=route.filledCapacityInRoute(routeId);
			totalCapacity=route.totalRouteCapacity(routeId);
			
			availableCapacity=totalCapacity-filledCapacity;
			if(availableCapacity >= busCapacity)
				System.out.println("Update the Bus table");//need to write update statement to bus table
			else
				System.out.println("Sorry route of bus can't be changed as there are users on this route who will be affected.");
			
			
		}
		
	}

	
	/*functionalty @2 admin wants to add new route
	 * 
	 */
	public void addNewRoute() {
		int cost=0;
		Route route=new Route();
		String stop=null;
		//ArrayList<String> list=new ArrayList<String>();
			System.out.println("Enter the cost for route which you want to create");			
			cost=Menu.userChoice.nextInt();
			System.out.println("Enter all the stops in order present in the route(press space after every stop) : ");
			stop=(Menu.userChoice.nextLine());
			String[] stopArray=stop.split(" ");
			route.addRoute(cost, stopArray);
			/*
			 * for(int i=0;i<stopArray.length;i++) { System.out.println(stopArray[i]); }
			 */		
	}

	
	/*to remove existing route
	 * @functionality 3
	 * #incomplete
	 */
	public void removeRoute() {
		int routeId=0;
		int filledCapacity=0;
		Route route=new Route();
		try (Scanner input = new Scanner(System.in)) {
			System.out.println("Enter the routeid of existing Route that you want to delete");
			routeId=Integer.parseInt(input.nextLine());
			
			//need a function to check that route exist or not
			
			filledCapacity=route.filledCapacityInRoute(routeId);
			if(filledCapacity!=0) {
				System.out.println("Sorry, this route can't be removed");
			}
			else {
				//update bus table make route id null
				Connections.sendStatement("Update bus Set routeid=NULL where routeid="+ routeId +" ;");
				//delete stop table where routeid matches
				Connections.sendStatement("delete from stop where routeid=" + routeId +" ;");
				Connections.sendStatement("delete from route where routeid=" + routeId +" ;");
				System.out.println("Route has been successfully removed.");
			}
			
		}
		
		
	}

	public void changeRouteOfBus() {
		int filledCapacity=0;
		int totalCapacity=0;
		int busCapacity=0;
		int availableCapacity=0; 
		System.out.println("Enter the routeId from which you want to remove the bus");
		int oldRouteId=Menu.userChoice.nextInt();
		route.getBussesInRoute(oldRouteId);
		System.out.println("enter the busId whose route you want to change");
		int busId=Menu.userChoice.nextInt();
		System.out.println("enter the routeId to which you want to assign the bus");
		int newRouteId=Menu.userChoice.nextInt();
		filledCapacity=route.filledCapacityInRoute(newRouteId);
		totalCapacity=route.totalRouteCapacity(newRouteId);
		
		availableCapacity=totalCapacity-filledCapacity;
		if(availableCapacity >= busCapacity)
			if(Connections.sendStatement("Update bus SET routeid="+ newRouteId +" where busid="+ busId + " ;"))
				System.out.println("Route of bus is changed");
			else
				System.out.println("sorry route of bus can't be changed");
		else
			System.out.println("sorry route of bus can't be changed");
		
}
	//method to change type of bus
	public void changeBusTypeInRoute() {
	    
		int busid =0;
		String newType="", oldType = "";
	    System.out.println("\nSelect route id to change bus type ");
	    int routeid = Menu.userChoice.nextInt();
	    route.getBussesInRoute(routeid);
	    System.out.println("\nSelect busid to change type : ");
	    busid = Menu.userChoice.nextInt();
	    oldType = bus.getBusType(busid);
	    int oldCapacity = bus.getTypeCapacity(oldType);
	    System.out.println("Previous capacity of bus "+ oldCapacity);
	                 
	    System.out.println("\nPlease enter new type : (Van(7)/Mini(15)/Large(30)) ");
        newType = Menu.userChoice.nextLine();
	    //System.out.println("Newtype"+newType);
	    int newCapacity = bus.getTypeCapacity(newType);
	    //if (newCapacity == 0)
	    System.out.println("New capacity is unchanged");
	    System.out.println("\nnew capacity  is "+newCapacity);
	    //if upgrading to larger bus then automatically change
	    if (newCapacity >= oldCapacity)
	    	bus.changeType(busid,newType);
	    else
	        System.out.println("You are trying to reduce capacity of the route, please add a bus before proceeding.");	                        
	    }

	public void changeNumberofBussesInRoute() {
	       
	       System.out.println("\nSelect route id to change number of busses: ");
	              int routeid = Menu.userChoice.nextInt();
	              bus.getBussesInRoute(routeid);
	              System.out.println("\nSelect an option to continue, \nTo add a bus type 'add', \nTo remove type 'remove' and press enter: ");
	              String selection = Menu.userChoice.nextLine();
	              if(selection.equals("add")) {
	                     bus.getAvailableBusses();
	                     System.out.println("\nSelect bus id to add to the route: ");
	                     int busid = Menu.userChoice.nextInt();
	                     route.addBusToRoute(routeid,busid);
	                     bus.getBussesInRoute(routeid);
	                     }
	              else if(selection.equals("remove")){
	                     System.out.println("\nEnter bus id to remove from route : ");
	                     int busid = Menu.userChoice.nextInt();
	                     route.removeBusFromRoute(routeid,busid);
	                     route.getBussesInRoute(routeid);
	              }
	       
}

	public boolean logout()
	{
		System.out.println("Thank you Admin for using Amazon Transport System. Signing you out...");
		return true;
	}

	/**
	 * @param userid
	 */
	public void setUserid(int userid) {
		this.userid = userid;		
	}
	
}