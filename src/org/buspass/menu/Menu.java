package org.buspass.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.buspass.user.UserHandler;
import org.buspass.user.AdminHandler;
import org.buspass.user.Employee;

public class Menu {
	boolean exit;

	public static final Scanner userChoice = new Scanner(System.in);

	public void displayMenu() throws Exception // throws Exception
	{
		printMainMenu();
		int choice = getInput();
		try
		{
			executeChoice(choice);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong. Please recheck the format of the userid and password");
			e.printStackTrace();
			System.out.println("Let's retry that in the correct format!");
			displayMenu();
		}
	}

	/* Private Functions to cut down scope only to Menu class */

	private void printWelcome() {
		System.out.println("+----------------------------------------------------+");
		System.out.println("|         Welcome to Amazon Transport System         |");
		System.out.println("+----------------------------------------------------+");

	}

	private void printMainMenu() {
		System.out.println("\nSelect user type to continue:\n");
		System.out.println("1. Admin");
		System.out.println("2. User");
		System.out.println("3. Visitor");
		System.out.println("9. Exit menu");
	}

	private int getInput() {
		// Scanner input = new Scanner(System.in)

		// Initializing choice with -1 to avoid conflict with menu options
		int choice = -1;
		// while (choice < 0 || choice >2 ) {
		try 
		{
			System.out.print("\nEnter your choice");
			choice = Integer.parseInt(userChoice.nextLine());
		} 
		catch (NumberFormatException nfe) 
		{
			System.out.println("Invalid choice, please try again");
			nfe.printStackTrace();
		}
		catch(InputMismatchException ime)
		{
			System.out.println("ERROR! Please enter only numeric choices as provided in options.");
			ime.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong. Please retry.");
			e.printStackTrace();
		}

		return choice;
	}

	private void executeChoice(int choice) throws Exception {
		Login login = new Login();
		Employee newemp = login.askLogin();
		if (newemp != null)
		{
			switch (choice) {
			case 1:
				// Admin after validation call the User handler
				  if(newemp.usertype.equals("admin")) 	//after validation call the Admin handler
					  new AdminHandler(newemp.userid).displayAdminMenu();
				  else
					  System.out.println("Invalid credentials");		  					  
				  break;
				 
			case 2:
				// User after validation call the User handler
				
				 if(newemp.usertype.equals("user"))
					new UserHandler(newemp.userid).displayUserMenu();
				 else			 
					 System.out.println("Invalid credentials, Please try again"); 
				  break;
	
			case 3:
				// visitor
				System.out.println("welcome to visitor");
				//VisitorHandler visitor = new VisitorHandler();
				//visitor.displayVisitorMenu();
				break;
	
			case 9:
				exit = true;
				System.out.println("Thank you for using Amazon Transport System. Signing you out.");
				break;
			default:
				System.out.println("Unable to make selection, Please try again");
				
			}
		}//end if
		else
			System.out.println("Unable to login");
	}
	
	public static void main(String[] args)
	{
		Menu mymenu = new Menu();
		try 
		{
			mymenu.displayMenu();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			userChoice.close();
		}
	}
}