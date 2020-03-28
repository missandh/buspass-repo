package org.buspass.user;

import org.buspass.menu.Menu;

public class VisitorHandler 
{
	boolean exit=false;
	
	private void displayVisitorHeader() 
	{
		System.out.println("\n|---------------------------|");
		System.out.println(" 	Welcome Visitor");
		System.out.println("|---------------------------|\n");
	}
	
	public void displayVisitorMenu() 
	{
		displayVisitorHeader();
		while(!exit) 
		{
			displayVisitorOptions();
			int choice =getInput(); 	
			executeChoice(choice);
		}
	}
	
	private int getInput() 
	{
		int choice = 0;
		System.out.println("\nEnter your choice:");
		choice=Menu.USERCHOICE.nextInt();
		return choice;
	}
	
    public void displayVisitorOptions() 
    {
    	System.out.println("\nHello Visitor, Please select an option to continue: ");
        System.out.println("\n1. View all exsisting routes"
                           + "\n2. View percentage occupancy in a route:  "
                           + "\n3. Apply for a bus pass"
                           + "\n0. Exit");
    }
    
    private void executeChoice(int choice) 
    {
    	Visitor visitor = new Visitor();
        switch (choice) 
        {
        	case 1:
        		System.out.println("\n********* View all exsisting routes***********\n");
        		visitor.viewAllDetails();
            	break;
        
        	case 2:
        		System.out.println("\n********* View percentage occupancy in a route***********\n");
            	visitor.viewOccupancy();
                break;
            
        	case 3:
        		System.out.println("\n********* Apply for a bus pass***********\n");
                visitor.applyBusPass();
                break;
                
           case 0:
             	 exit=true; //goback to previous menu
             	 System.out.println("Thank you for visiting Amazon Transport System. Have a great time!");
             	 return;              
        }
    }
}
