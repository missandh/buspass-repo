package org.buspass.user;

import org.buspass.menu.Menu;

public class AdminHandler 
{
	//Scanner input = new Scanner(System.in);
	boolean exit;
	boolean loggedout = false;

	Admin admin=new Admin();
	
	/**
	 * @param userid
	 */
	public AdminHandler(int userid) {
		admin.setUserid(userid);
	}


	public void displayAdminMenu() //throws Exception 
	
	{
		int choice = -1;
		displayAdminHeader();
		while (!loggedout)
		{
			displayAdminOptions();
			choice =getInput(); 	
			executeChoice(choice);
		}
	}
	
	
	private int getInput() {
		int choice = 0;
		System.out.println("\nEnter your choice:");
		if(Menu.USERCHOICE.hasNextLine())
			choice=Menu.USERCHOICE.nextInt();			
		return choice;
	}
	
	
	public void displayAdminHeader() {
		System.out.println("\n|------------------------------------------|");
		System.out.println("\t  Welcome Admin ");
		System.out.println("|------------------------------------------|");
	}
	
	private void displayAdminOptions() {
		System.out.println("\nPlease select an option to continue");
		
		System.out.println("1. Approve/Reject bus pass apllication");
		System.out.println("2. Add a new route");
		System.out.println("3. Remove an exsisting route");
		System.out.println("4. Change number of busses on a route");
		System.out.println("5. Change bus type on a route ");
		System.out.println("6. View number of vehicles in each type");
		System.out.println("7. Change route of a bus");
		System.out.println("8. View all the routes, their stops, type of vehicles, no of vehicles on each route");
		System.out.println("0. Logout");
	}
	
	private void executeChoice(int choice) {
		Admin admin=new Admin();
		
		switch (choice)
		{
		case 1: 
			admin.fetchBusPassApplications();
			break;
			
		case 2:
			admin.addNewRoute();
			break;
			
		case 3:
			admin.removeRoute();
			break;
			
		case 4:
			admin.changeNumberofBussesInRoute();
			break;
		
		case 5: 
			admin.changeBusTypeInRoute();
			break;
			
		case 6:
			 admin.typeofVehicles();
			 break;
			 
		case 7: 
			admin.changeRouteOfBus();
			break;
		
		case 8:
			admin.viewAllDetails();
			break;
			
		case 0:
			loggedout= admin.logout();
			break;
			
		default:
			System.out.println("Wrong Input, try again\n");
			break;
		}
	}
	
}
