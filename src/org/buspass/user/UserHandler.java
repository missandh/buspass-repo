package org.buspass.user;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserHandler 
{
	//Scanner input = new Scanner(System.in);
	boolean exit;
	User bususer = new User();
	
	public void displayUserMenu() //throws Exception 
	
	{
		displayUserHeader();
		displayUserOptions();
		int choice =getInput(); 	
		executeChoice(choice);
	}
	
	
	private int getInput() 
	{
		Scanner input = new Scanner(System.in);
		int choice = -1;
		
		boolean validchoice = false;
		while (!validchoice) 
		{
    	    try 
    	    {
    			System.out.println("\nEnter your choice:");
    	    	choice = input.nextInt();
    	        if (choice >= 1 || choice <= 5 || choice == 9)
    	        	validchoice = true;  // will not get here if nextInt() throws exception
    	    }
    	    catch (InputMismatchException e) {
    	        input.nextLine();            // let the scanner skip over the bad input
    	        System.out.println("ERROR!!! \nENTER 1, 2, 3, 4, 5 or 9 as your choice :");
    	    }
		}
		
		input.close();
		return choice;
}
	
	
	public void displayUserHeader() {
		System.out.println("\n|----------------|");
		System.out.println(" Welcome Bus User ");
		System.out.println("|-----------------|");
	}
	
	public void displayUserOptions() {
		System.out.println("\nPlease select an option to continue");
		
		System.out.println("1. View all the routes, their stops, type of vehicles, no of vehicles on each route");
		System.out.println("2. Request cancel or Reactivate or Cancel Bus Pass");
		System.out.println("3. Give feedback on the Bus Pass Maintenance System");
		System.out.println("4. View and Update your contact details");
		System.out.println("5. Get Bus Pass Snapshot");

		System.out.println("9. Exit menu");
	}
	
	private void executeChoice(int choice) {
		
		switch (choice)
		{
		case 1: 
			System.out.println("View all the routes, their stops, type of vehicles, no of vehicles on each route");
			bususer.viewAllDetails();
			break;
			
		case 2:
			System.out.println("Request cancel or Reactivate or Cancel Bus Pass");
			bususer.requestAlterBusPass();
			break;
			
		case 3://
			System.out.println("Give feedback on the Bus Pass Maintenance System");
			bususer.provideFeedback();
			break;
			
		case 4:
			System.out.println("View and Update your contact details");
			bususer.updateUserData();
			break;
		
		case 5: 
			System.out.println("Get Bus Pass Snapshot");
			bususer.viewBuspassSnapshot(bususer.getUserid());
			break;
			
		case 9:
			break;
			
		default:
			break;
		}
		
	}
	
}
