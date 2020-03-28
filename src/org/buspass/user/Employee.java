/**
 * 
 */
package org.buspass.user;

import org.buspass.route.Route;

/**
 * @author missandh
 * Super class in Bus Pass Maintenance. Extended by User and Admin classes. 
 *
 */
public class Employee 
{
	public int userid;
	public String usertype;
	Route currentroute = new Route();
	
	/**
	 * @param userid
	 * @param usertype
	 */
	public Employee(int userid, String usertype) 
	{
		this.userid = userid;
		this.usertype = usertype;
	}
	
	public Employee()
	{
		//System.out.println("New Employee");
	}
	
    public void viewAllDetails()
    {
    	System.out.println("Here are all the routes that currently are served: ");
    	currentroute.viewAllRoutesWithStops();
    	currentroute.viewTypeOfVehiclesPerRoute();
    }

	
}
