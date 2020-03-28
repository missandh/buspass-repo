package org.buspass.user;

import java.util.InputMismatchException;

import org.buspass.menu.Menu;

public class UserHandler 
{
	//Scanner input = new Scanner(System.in);
	boolean exit;
	boolean loggedout = false;

	User bususer = new User();
	
	/**
	 * @param userid
	 */
	public UserHandler(int userid) 
	{
		bususer.setUserid(userid);
	}


	public void displayUserMenu() //throws Exception
	{
		displayUserHeader();
		int choice = -1;
		while(!loggedout)
		{
			displayUserOptions();
			choice= getInput(); 	
			executeChoice(choice);
		}
	}
	
	
	private int getInput() 
	{
		int choice = -1;
		
		boolean validchoice = false;
		while (!validchoice) 
		{
    	    try 
    	    {
    			System.out.println("\nEnter your choice:");
    	    	choice = Menu.USERCHOICE.nextInt();
    	        if (choice >= 1 || choice <= 5 || choice == 9)
    	        {
    	        	validchoice = true;  // will not get here if nextInt() throws exception
    	        }
    	        else
    	        	System.out.println("Incorrect Choice");
    	    }
    	    catch (InputMismatchException e) {
    	        Menu.USERCHOICE.nextLine();            // let the scanner skip over the bad input
    	        System.out.println("ERROR!!! \nENTER 1, 2, 3, 4, 5 or 9 as your choice :");
    	    }
		}
		
		return choice;
}

	
	public void displayUserHeader() {
		System.out.println("\n|----------------|");
		System.out.println(" Welcome Bus User ");
		System.out.println("|-----------------|");
	}
	
	public void displayUserOptions() 
	{
		System.out.println("\nPlease select an option to continue");
		
		System.out.println("1. View all the routes, their stops, type of vehicles, no of vehicles on each route");
		System.out.println("2. Request Cancel or Reactivate or Suspend Bus Pass");
		System.out.println("3. Give feedback on the Bus Pass Maintenance System");
		System.out.println("4. View and Update your contact details");
		System.out.println("5. Get Bus Pass Snapshot");
		System.out.println("0. Logout");
	}
	
	private void executeChoice(int choice) {
		
		switch (choice)
		{
		case 1: 
			System.out.println("\n*******View all the routes, their stops, type of vehicles, no of vehicles on each route*******");
			bususer.viewAllDetails();
			break;
			
		case 2:
			System.out.println("\n*******Request Cancel or Reactivate or Suspend Bus Pass*******\n");
			bususer.requestAlterBusPass();
			break;
			
		case 3://
			System.out.println("\n*******Give feedback on the Bus Pass Maintenance System*******\n");
			bususer.provideFeedback();
			break;
			
		case 4:
			System.out.println("\n*******View and Update your contact details******\n");
			bususer.updateUserData();
			break;
		
		case 5: 
			System.out.println("\n*******Get Bus Pass Snapshot*******\n");
			bususer.viewBuspassSnapshot(bususer.getUserid());
			break;
			
		case 0:
			System.out.println("\n*******Logging you out********\n");
			System.out.println("Bye Bye. Thanks for using Amazon Transport System.");
			loggedout= bususer.logout();
			break;
			
		default:
			break;
			
		}
		
	}
	
}
