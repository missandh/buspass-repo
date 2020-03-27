/**
 * 
 */
package org.buspass.user;

/**
 * @author missandh
 * Super class in Bus Pass Maintenance. Extended by User and Admin classes. 
 *
 */
public class Employee 
{
	public int userid;
	public String usertype;
	
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
	
}
