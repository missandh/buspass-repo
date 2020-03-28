package org.buspass.user;

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
        selection = Menu.USERCHOICE.nextInt();
        selectApplication(selection);
        }
 
 public void selectApplication(int selection) {
        int routeid = bus.getapplicationdetails(selection);
        bus.validateBusPassApplication(routeid, selection);
 
 }
	
	
	//method to see the type of vehicles available on all routes/particular route 
	//based on user input
	public  void typeofVehicles() {
		System.out.println("\nDo you want to view any specific route: type yes/no ");
		String choice = Menu.USERCHOICE.next();
		if (choice.equals("yes")) {
			System.out.println("\nPlease enter routeid : ");
			int routeid = Menu.USERCHOICE.nextInt();
			bus.viewTypeCount(routeid);
			}
			else {
				bus.viewTypeCount();
			}
		//previousMenu();
}

	/*:-change the route of bus
	 * method to change the route of bus based on routeid of route on which bus is currently running
	 * and routeid of route(to which bus need to be assigned)
	 */	public void changeRouteOfBus() {
			int filledCapacity=0;
			int totalCapacity=0;
			int busCapacity=0;
			int availableCapacity=0; 
			System.out.println("Enter the routeId from which you want to remove the bus");
			int oldRouteId=Menu.USERCHOICE.nextInt();
			route.getBussesInRoute(oldRouteId);
			System.out.println("enter the busId whose route you want to change");
			int busId=Menu.USERCHOICE.nextInt();
			System.out.println("enter the routeId to which you want to assign the bus");
			int newRouteId=Menu.USERCHOICE.nextInt();
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

	
	/*method to add a new route
	 *  admin wants to add new route
	 * 
	 */
	public void addNewRoute() {
		int cost=0;
		Route route=new Route();
		String stop=null;
			System.out.println("Enter the cost for route which you want to create");			
			cost=Menu.USERCHOICE.nextInt();
			System.out.println("Enter all the stops in order present in the route(press comma \",\" after every stop and enter to end) : ");
			Menu.USERCHOICE.nextLine();
			stop=Menu.USERCHOICE.nextLine();
			String[] stopArray=stop.split(",");
			route.addRoute(cost, stopArray);	
	}

	
	/*to remove existing route
	 * @functionality 3
	 */
	public void removeRoute() 
	{
		int routeId=0;
		int filledCapacity=0;
		Route route=new Route();
		System.out.println("Enter the routeid of existing Route that you want to delete");
		routeId=Menu.USERCHOICE.nextInt();
		filledCapacity=route.filledCapacityInRoute(routeId);
			
		if(filledCapacity!=0) {
			System.out.println("Sorry, this route can't be removed as there are active route users");
		}	
		else if(route.checkApplications(routeId)) 
		{
			System.out.println("Sorry we cannot remove this route as there are bus pass applications on this route");
		}
		else 
		{				
			//update bus table make route id null
			Connections.sendStatement("Update bus Set routeid=NULL where routeid="+ routeId +" ;");
			//deleting all schedules from schedule table
			Connections.sendStatement("delete from schedule where routeid="+routeId + ";");
			//delete stop table where routeid matches
			Connections.sendStatement("delete from stop where routeid=" + routeId +" ;");
			Connections.sendStatement("delete from route where routeid=" + routeId +" ;");
			System.out.println("Route has been successfully removed.");
		}
	}

	//method to change type of bus
	public void changeBusTypeInRoute() 
	{
	    System.out.println("\nSelect route id to change bus type ");
	    int routeid = Menu.USERCHOICE.nextInt();
	    route.getBussesInRoute(routeid);
	    System.out.println("\nSelect busid to change type : ");
	    int busid = Menu.USERCHOICE.nextInt();
	    String oldType = bus.getBusType(busid);
	    int oldCapacity = bus.getTypeCapacity(oldType);
	    System.out.println("Previous capacity of bus "+oldCapacity);
	                 
	    System.out.println("\nPlease enter new type : (Van/Mini/Large) ");
	    String newType = Menu.USERCHOICE.next();
	    int newCapacity = bus.getTypeCapacity(newType);
	    System.out.println("\nnew capacity  is "+newCapacity);
	    //if upgrading to larger bus then automatically change

	    if (newCapacity >= oldCapacity) 
	    {
	    	bus.changeType(busid,newType);                 	
	    }
	    else 
	    {
	    	System.out.println("You are trying to reduce capacity of the route, please add a bus before proceeding.");
	    }                                          
	}

	public void changeNumberofBussesInRoute() {
	       
	       System.out.println("\nSelect route id to change number of busses: ");
	              int routeid = Menu.USERCHOICE.nextInt();
	              bus.getBussesInRoute(routeid);
	              System.out.println("\nSelect an option to continue, \nTo add a bus type 'add', \nTo remove type 'remove' and press enter: ");
	              String selection = Menu.USERCHOICE.next();
	              if(selection.equals("add")) {
	                     bus.getAvailableBusses();
	                     System.out.println("\nSelect bus id to add to the route: ");
	                     int busid = Menu.USERCHOICE.nextInt();
	                     route.addBusToRoute(routeid,busid);
	                     bus.getBussesInRoute(routeid);
	                     }
	              else if(selection.equals("remove")){
	                     System.out.println("\nEnter bus id to remove from route : ");
	                     int busid = Menu.USERCHOICE.nextInt();
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