/**
 * 
 */
package org.buspass.route;

import java.sql.Connection;
import java.sql.ResultSet;
import org.buspass.connection.Connections;

/**
 * @author missandh
 *
 * This class contains methods to add, modify, remove and view the schedules of a bus (in a route)
 * These methods should only be called by the admin.
 * 
 * Methods:
 * 
 * addSchedule(): Add schedule of a bus in a route to the Schedule table
 * 				Parameters: int busid, int routeid, String bustime (format HH:MM:SS)
 * modifySchedule(): Modify scheduled time of a bus in a route in the Schedule table
 * 				Parameters: int scheduleid, String bustime (format HH:MM:SS)
 * removeSchedule(): Remove schedule of a bus in a route from the Schedule table
 * viewSchedule(): View schedule of a bus in a route from the Schedule table
 * viewRouteSchedule(): View Schedule of all the buses in a route - to be decided
 * 
 */

public class Schedule {

	private String table = "schedule";
	/*
	 * Columns:
	 * scheduleid int(11) AI PK 
	 * busid int(11) 
	 * routeid int(11) 
	 * scheduletime time HH:MM:SS format
	 */
	
	public void addSchedule(int busid, int routeid, String bustime)
	{
		String squery = "INSERT INTO " + table + " VALUES ( DEFAULT, " + busid + ", "+ routeid + ", \'" + bustime +"\';" ; 

		if( Connections.sendStatement(squery))
			System.out.println("Successfully added the schedule for the bus " + busid + " into the schedule table");
	}
	
	public void modifySchedule (int scheduleid, String bustime)
	{
		String squery = "UPDATE " + table + " SET scheduletime= \'" + bustime + "\' WHERE scheduleid= " +scheduleid+";";

		if( Connections.sendStatement(squery))
			System.out.println("Successfully modified the schedule for schedule id " + scheduleid + " in the schedule table");

	}
		
	public void removeSchedule(int scheduleid)
	{
			
		String squery = "DELETE from "+ table + " WHERE scheduleid= " + scheduleid+";";
			
		if( Connections.sendStatement(squery))
			System.out.println("Successfully removed the entry for the schedule id " + scheduleid +" from the schedule table");
	}
		
	public void viewSchedule(int busid)
	{
		String squery = "SELECT * FROM "+ table + "WHERE busid= " + busid+";";
		ResultSet rset = null;
		
		//Connect to the database
		Connection dbcon = Connections.makeConnection();

		try 
		{
			//Prepare the query string
			rset = Connections.sendQuery(dbcon, squery);
			while (rset.next())
			{
				System.out.println("Printing result...");
				 
                // Let's fetch the data by column name
                int scheduleid = rset.getInt(1);
                busid = rset.getInt(2);
                int routeid = rset.getInt(3);
                String scheduletime = rset.getString(4);
 
                System.out.println("\tSchedule ID: " + scheduleid + 
                        ", Bus ID: " + busid + 
                        ", Route ID: " + routeid + ", Scheduled Time: " + scheduletime);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void viewRouteSchedule(int routeid)
	{
		String squery = "SELECT * FROM "+ table + " WHERE routeid= " + routeid+";";
		ResultSet rset = null;
		//ArrayList<String> cnames = new ArrayList<String>();
		//ArrayList<String> values = new ArrayList<String>();
		Connection dbcon = Connections.makeConnection();

		try 
		{
			rset = Connections.sendQuery(dbcon, squery);
			while (rset.next())
			{
				System.out.println("Here's the schedule for the route: " + routeid);
				 
                // Let's fetch the data by column name
                int scheduleid = rset.getInt(1);
                int busid = rset.getInt(2);
                routeid = rset.getInt(3);
                String scheduletime = rset.getString(4);
 
                System.out.println("\tSchedule ID: " + scheduleid + 
                        ", Bus ID: " + busid + 
                        ", Route ID: " + routeid + ", Scheduled Time: " + scheduletime);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
}
